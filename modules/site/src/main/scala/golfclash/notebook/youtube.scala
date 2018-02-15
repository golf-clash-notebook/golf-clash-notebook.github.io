/*
 * MIT License
 *
 * Copyright (c) 2018 golf-clash-notebook
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package golfclash.notebook

import org.threeten.bp._

import org.scalajs.dom.ext.Ajax

import cats.implicits._
import io.circe.generic.auto._
import io.circe.parser._
import monix.eval._
import monix.execution.Scheduler.Implicits.global

object youtube {

  case class Channel(name: String, id: String)
  case class YouTubeItemId(kind: String, videoId: String)
  case class YouTubeSearchResultItem(id: YouTubeItemId)
  case class YouTubeSnippet(title: Option[String], description: Option[String])
  case class YouTubeLiveStreamingDetails(scheduledStartTime: Option[String],
                                         scheduledEndTime: Option[String]) {
    def startTime = scheduledStartTime.map(parseStreamTime)
    def endTime   = scheduledEndTime.map(parseStreamTime)
  }

  case class UpcomingStreamInfo(snippet: YouTubeSnippet, details: YouTubeLiveStreamingDetails)

  sealed trait YouTubeStream extends Product with Serializable

  case class LiveStream(id: YouTubeItemId, snippet: YouTubeSnippet) extends YouTubeStream {
    def title: String = snippet.title.getOrElse("Untitled")
    def url: String   = s"https://www.youtube.com/watch?v=${id.videoId}"
  }

  case class UpcomingStream(id: YouTubeItemId, info: UpcomingStreamInfo) extends YouTubeStream {
    def title: String = info.snippet.title.getOrElse("Untitled")
    def url: String   = s"https://www.youtube.com/watch?v=${id.videoId}"
  }

  val StreamKeywordsFilter = List(
    "clash",
    "chris taylor",
    "elite",
    "expert",
    "friendly",
    "friendlies",
    "golf",
    "master",
    "opening",
    "practice",
    "pro",
    "qualifying",
    "rookie",
    "round",
    "tour",
    "tournament",
    "weekend"
  )

  def apiKey(): String = {
    GolfClashNotebookApp.currentMode() match {
      case GolfClashNotebookApp.Prod => "AIzaSyCn1DjohiZO2wYqyxqRyUpW-EKc9G0wzDw"
      case GolfClashNotebookApp.Dev  => "AIzaSyDA9KbcKjaMOFZpGJLtlE-6pBdBhUzQ6kM"
    }
  }

  def channels(): Task[List[Channel]] = {
    urlRequest("/data/channels.json").map { responseText =>
      (for {
        json     <- parse(responseText)
        channels <- json.as[List[Channel]]
      } yield {
        channels
      }).getOrElse(Nil)
    }
  }

  def channelStreams(channelId: String): Task[List[YouTubeStream]] =
    for {
      liveStreams     <- channelLiveStreams(channelId)
      upcomingStreams <- channelUpcomingStreams(channelId)
    } yield {
      (liveStreams ::: upcomingStreams).filter { stream =>
        stream match {
          case LiveStream(_, YouTubeSnippet(Some(title), _)) =>
            StreamKeywordsFilter.exists(title.toLowerCase.contains)
          case UpcomingStream(_, UpcomingStreamInfo(YouTubeSnippet(Some(title), _), _)) =>
            StreamKeywordsFilter.exists(title.toLowerCase.contains)
          case _ => false
        }
      }
    }

  private[this] def channelLiveStreams(channelId: String): Task[List[LiveStream]] = {
    urlRequest(streamChannelQueryURL(channelId, "live")).map { responseText =>
      parse(responseText) match {
        case Left(_) => Nil
        case Right(json) => {
          json.hcursor.downField("items").as[List[LiveStream]] match {
            case Left(_) => Nil
            case Right(streamList) => {
              streamList
            }
          }
        }
      }
    }
  }

  private[this] def channelUpcomingStreams(channelId: String): Task[List[UpcomingStream]] = {
    urlRequest(streamChannelQueryURL(channelId, "upcoming")).flatMap { responseText =>
      parse(responseText) match {
        case Left(_) => Task.now(Nil)
        case Right(json) => {
          json.hcursor.downField("items").as[List[YouTubeSearchResultItem]] match {
            case Left(_) => Task.now(Nil)
            case Right(searchResults) => {
              searchResults
                .map { searchResult =>
                  getUpcomingStreamInfo(searchResult.id.videoId).map { info =>
                    info.map { i =>
                      UpcomingStream(searchResult.id, i)
                    }
                  }
                }
                .sequence
                .map { optionList =>
                  optionList.flatten
                    .sortBy(
                      _.info.details.startTime.map(_.toEpochSecond).getOrElse(Long.MaxValue)
                    )
                    .filter {
                      _.info.details.startTime match {
                        case Some(startTime) =>
                          startTime.isAfter(ZonedDateTime.now.minusMinutes(5)) && startTime
                            .isBefore(ZonedDateTime.now.plusDays(7))
                        case None => false
                      }
                    }
                }
            }
          }
        }
      }
    }
  }

  private[this] def getUpcomingStreamInfo(videoId: String): Task[Option[UpcomingStreamInfo]] = {
    urlRequest(
      s"https://www.googleapis.com/youtube/v3/videos?part=snippet,liveStreamingDetails&id=${videoId}&key=${apiKey()}"
    ).map { responseText =>
      parse(responseText) match {
        case Left(_) => None
        case Right(json) => {
          val streamSnippet =
            json.hcursor
              .downField("items")
              .downArray
              .downField("snippet")
              .as[YouTubeSnippet]
              .toOption

          val liveStreamDetails =
            json.hcursor
              .downField("items")
              .downArray
              .downField("liveStreamingDetails")
              .as[YouTubeLiveStreamingDetails]
              .toOption

          for {
            snippet <- streamSnippet
            details <- liveStreamDetails
          } yield {
            UpcomingStreamInfo(snippet, details)
          }
        }
      }
    }
  }

  private[this] def streamChannelQueryURL(channelId: String, streamType: String) = {
    s"https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=${channelId}&eventType=${streamType}&order=date&type=video&key=${apiKey()}"
  }

  private[this] def urlRequest(url: String): Task[String] = {
    Task.fromFuture(Ajax.get(url).map(_.responseText))
  }

  private[this] def parseStreamTime(timeString: String): ZonedDateTime = {
    OffsetDateTime
      .parse(timeString, format.DateTimeFormatter.ISO_DATE_TIME)
      .toZonedDateTime
      .withZoneSameInstant(ZoneId.systemDefault)
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////
  // LIVE RESPONSE
  //
  // GET https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UC0aJpP4DgTL96qnQE5BA1Sw&eventType=live&maxResults=1&order=date&type=video&key={YOUR_API_KEY}
  //
  // URL to stream is: https://www.youtube.com/watch?v= + videoID
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////

  // {
  //  "kind": "youtube#searchListResponse",
  //  "etag": "\"Wu2llbfqCdxIVjGbVPm2DslKPCA/7t2sW9MH-WwyJXOPpsLNdYqqZ9I\"",
  //  "nextPageToken": "CAEQAA",
  //  "regionCode": "US",
  //  "pageInfo": {
  //   "totalResults": 2,
  //   "resultsPerPage": 1
  //  },
  //  "items": [
  //   {
  //    "kind": "youtube#searchResult",
  //    "etag": "\"Wu2llbfqCdxIVjGbVPm2DslKPCA/DuTFRqMHpWLb0zJRz7mJDgWxJek\"",
  //    "id": {
  //     "kind": "youtube#video",
  //     "videoId": "rhy_EjvT1S0"
  //    },
  //    "snippet": {
  //     "publishedAt": "2018-01-22T14:05:12.000Z",
  //     "channelId": "UC0aJpP4DgTL96qnQE5BA1Sw",
  //     "title": "Southwest Florida Eagle Cam - 360",
  //     "description": "Southwest Florida Eagle Cam - 360.",
  //     "thumbnails": {
  //      "default": {
  //       "url": "https://i.ytimg.com/vi/rhy_EjvT1S0/default_live.jpg",
  //       "width": 120,
  //       "height": 90
  //      },
  //      "medium": {
  //       "url": "https://i.ytimg.com/vi/rhy_EjvT1S0/mqdefault_live.jpg",
  //       "width": 320,
  //       "height": 180
  //      },
  //      "high": {
  //       "url": "https://i.ytimg.com/vi/rhy_EjvT1S0/hqdefault_live.jpg",
  //       "width": 480,
  //       "height": 360
  //      }
  //     },
  //     "channelTitle": "Southwest Florida Eagle Cam",
  //     "liveBroadcastContent": "live"
  //    }
  //   }
  //  ]
  // }

  //////////////////////////////////////////////////////////////////////////////////////////////////
  // UPCOMING RESPONSE
  //
  // GET https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCvqRdlKsE5Q8mf8YXbdIJLw&eventType=upcoming&maxResults=1&order=date&type=video&key={YOUR_API_KEY}
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////

  // {
  //  "kind": "youtube#searchListResponse",
  //  "etag": "\"Wu2llbfqCdxIVjGbVPm2DslKPCA/8JWJkZ-N0Pb5b6hvMC3EbAAO8vw\"",
  //  "nextPageToken": "CAEQAA",
  //  "regionCode": "US",
  //  "pageInfo": {
  //   "totalResults": 4,
  //   "resultsPerPage": 1
  //  },
  //  "items": [
  //   {
  //    "kind": "youtube#searchResult",
  //    "etag": "\"Wu2llbfqCdxIVjGbVPm2DslKPCA/cK6sX7WoUDN2WcvZcLbVPprJw78\"",
  //    "id": {
  //     "kind": "youtube#video",
  //     "videoId": "6kJPU5t1qCE"
  //    },
  //    "snippet": {
  //     "publishedAt": "2018-02-04T19:21:57.000Z",
  //     "channelId": "UCvqRdlKsE5Q8mf8YXbdIJLw",
  //     "title": "ROX vs. KSV - KDM vs. AFS | Week 4 Day 1 | LCK Spring (2018)",
  //     "description": "LCK Spring Split Week 4 2018 #LCK ROX Tigers vs. KSV KONGDOO MONSTER vs. Afreeca Freecs Watch all matches of the split here from all of our leagues: NA LCS, EU LCS, LCK, LPL. FULL VOD PLAYLIST...",
  //     "thumbnails": {
  //      "default": {
  //       "url": "https://i.ytimg.com/vi/6kJPU5t1qCE/default_live.jpg",
  //       "width": 120,
  //       "height": 90
  //      },
  //      "medium": {
  //       "url": "https://i.ytimg.com/vi/6kJPU5t1qCE/mqdefault_live.jpg",
  //       "width": 320,
  //       "height": 180
  //      },
  //      "high": {
  //       "url": "https://i.ytimg.com/vi/6kJPU5t1qCE/hqdefault_live.jpg",
  //       "width": 480,
  //       "height": 360
  //      }
  //     },
  //     "channelTitle": "LoL Esports",
  //     "liveBroadcastContent": "upcoming"
  //    }
  //   }
  //  ]
  // }

  //////////////////////////////////////////////////////////////////////////////////////////////////
  // UPCOMING FOLLOW UP TO GET START TIME
  //
  // GET https://www.googleapis.com/youtube/v3/videos?part=liveStreamingDetails&id=6kJPU5t1qCE&maxResults=1&key={YOUR_API_KEY}
  //////////////////////////////////////////////////////////////////////////////////////////////////

  // {
  //  "kind": "youtube#videoListResponse",
  //  "etag": "\"Wu2llbfqCdxIVjGbVPm2DslKPCA/Z7QrDD5f8vvgRVmz2H_d7gOQzPw\"",
  //  "pageInfo": {
  //   "totalResults": 1,
  //   "resultsPerPage": 1
  //  },
  //  "items": [
  //   {
  //    "kind": "youtube#video",
  //    "etag": "\"Wu2llbfqCdxIVjGbVPm2DslKPCA/SIXLtrd2XL61-RwpaaVux-drSPQ\"",
  //    "id": "6kJPU5t1qCE",
  //    "liveStreamingDetails": {
  //     "scheduledStartTime": "2018-02-06T08:00:00.000Z",
  //     "activeLiveChatId": "Cg0KCzZrSlBVNXQxcUNF"
  //    }
  //   }
  //  ]
  // }

}
