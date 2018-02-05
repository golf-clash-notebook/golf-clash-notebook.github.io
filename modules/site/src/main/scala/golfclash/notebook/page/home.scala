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
package page

import org.scalajs.jquery._
import org.threeten.bp._

import cats.implicits._
import monix.execution.Scheduler.Implicits.global
import scalatags.JsDom.all._

object home {

  private val dateTimeFormatter = format.DateTimeFormatter.ofPattern("d MMM @ hh:mm a")

  val init = () => {

    youtube
      .channels()
      .flatMap { channels =>
        channels
          .map { channel =>
            for {
              liveStream     <- youtube.getChannelLiveStream(youtube.apiKey(), channel.id)
              upcomingStream <- youtube.getChannelUpcomingStream(youtube.apiKey(), channel.id)
            } yield {

              val (itemClasses, scheduleText) =
                (liveStream, upcomingStream) match {
                  case (Some(live), _) =>
                    (
                      "live",
                      a(
                        href := live.url,
                        cls := "live-stream-link",
                        target := "_blank",
                        rel := "noopener"
                      )("LIVE")
                    )
                  case (None, Some(upcoming)) =>
                    (
                      "",
                      upcoming.dateTime
                        .map(dateTime => span(dateTime.format(dateTimeFormatter)))
                        .getOrElse(span("Nothing Scheduled"))
                    )
                  case _ => ("", span("Nothing Scheduled"))
                }

              jQuery("#stream-schedule > .list-group").append(
                div(cls := s"list-group-item stream-item $itemClasses")(
                  a(href := s"https://www.youtube.com/channel/${channel.id}")(
                    h5(cls := "list-group-item-heading stream-title")(channel.name)
                  ),
                  p(cls := "list-group-item-text stream-schedule-time text-small")(scheduleText)
                ).render
              )
            }
          }
          .sequence
          .map(_ => jQuery("#stream-schedule").removeClass("hidden"))
      }
      .runAsync

  }

}
