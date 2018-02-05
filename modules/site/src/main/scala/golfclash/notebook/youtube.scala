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

import io.circe.generic.auto._
import io.circe.parser._
import monix.eval._
import monix.execution.Scheduler.Implicits.global

object youtube {

  case class Channel(name: String, id: String)
  case class YouTubeItemId(kind: String, videoId: String)
  case class YouTubeLiveStreamingDetails(scheduledStartTime: String)

  sealed trait YouTubeStream extends Product with Serializable

  case class LiveStream(id: YouTubeItemId) extends YouTubeStream {
    def url: String = s"https://www.youtube.com/watch?v=${id.videoId}"
  }

  case class UpcomingStream(id: YouTubeItemId, dateTimeString: Option[String])
      extends YouTubeStream {
    def dateTime = {
      dateTimeString
        .map { string =>
          OffsetDateTime
            .parse(string, format.DateTimeFormatter.ISO_DATE_TIME)
            .toZonedDateTime
            .withZoneSameInstant(ZoneId.systemDefault)
        }
        .filter(_.isAfter(ZonedDateTime.now))
    }
  }

  def apiKey(): String = {
    GolfClashNotebookApp.currentMode() match {
      case GolfClashNotebookApp.Prod => "AIzaSyCn1DjohiZO2wYqyxqRyUpW-EKc9G0wzDw"
      case GolfClashNotebookApp.Dev  => "AIzaSyDA9KbcKjaMOFZpGJLtlE-6pBdBhUzQ6kM"
    }
  }

  def channels(): Task[List[Channel]] = {
    urlRequest("/data/channels.json").map { text =>
      (for {
        json     <- parse(text)
        channels <- json.as[List[Channel]]
      } yield {
        channels
      }).getOrElse(Nil)
    }
  }

  def getChannelLiveStream(apiKey: String, channelId: String): Task[Option[LiveStream]] = {
    urlRequest(streamChannelQueryURL(apiKey, channelId, "live")).map { responseText =>
      parse(responseText) match {
        case Left(_) => None
        case Right(json) => {
          json.hcursor.downField("items").as[List[LiveStream]] match {
            case Left(_)           => None
            case Right(streamList) => streamList.headOption
          }
        }
      }
    }
  }

  def getChannelUpcomingStream(apiKey: String, channelId: String): Task[Option[UpcomingStream]] = {
    urlRequest(streamChannelQueryURL(apiKey, channelId, "upcoming")).flatMap { responseText =>
      parse(responseText) match {
        case Left(_) => Task.now(None)
        case Right(json) => {
          json.hcursor.downField("items").as[List[UpcomingStream]] match {
            case Left(_) => Task.now(None)
            case Right(streamList) => {
              streamList.headOption
                .map { upcomingStream =>
                  getUpcomingStreamInfo(apiKey, upcomingStream.id.videoId).map { info =>
                    info.map(i => UpcomingStream(upcomingStream.id, Some(i.scheduledStartTime)))
                  }
                }
                .getOrElse(Task.now(None))
            }
          }
        }
      }
    }
  }

  def getUpcomingStreamInfo(apiKey: String,
                            videoId: String): Task[Option[YouTubeLiveStreamingDetails]] = {
    urlRequest(
      s"https://www.googleapis.com/youtube/v3/videos?part=liveStreamingDetails&id=${videoId}&key=${apiKey}"
    ).map { responseText =>
      parse(responseText) match {
        case Left(_) => None
        case Right(json) => {
          json.hcursor
            .downField("items")
            .downArray
            .downField("liveStreamingDetails")
            .as[YouTubeLiveStreamingDetails]
            .toOption
        }
      }
    }
  }

  private[this] def streamChannelQueryURL(apiKey: String, channelId: String, streamType: String) = {
    s"https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=${channelId}&eventType=${streamType}&order=date&type=video&key=${apiKey}"
  }

  private[this] def urlRequest(url: String): Task[String] = {
    Task.fromFuture(Ajax.get(url).map(_.responseText))
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
