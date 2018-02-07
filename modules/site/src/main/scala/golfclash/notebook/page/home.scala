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

import scala.scalajs.js.timers._

import org.scalajs.jquery._
import org.scalajs.dom.Element
import org.threeten.bp._

import monix.execution.Scheduler.Implicits.global
import scalatags.JsDom.all._
import youtube._

object home {

  private val dateTimeFormatter = format.DateTimeFormatter.ofPattern("d MMM @ h:mm a")

  val init = () => {
    refreshStreamSchedule(true)
    setInterval(store.scheduling.ExpiredThreshold / 3) { refreshStreamSchedule(); () }
  }

  def refreshStreamSchedule(initial: Boolean = false) = {

    if (initial) {
      jQuery(".stream-updating-spinner").removeClass("hidden")
    }

    store.scheduling
      .current()
      .map { maybeSchedule =>
        maybeSchedule match {
          case Some(schedule) => {
            schedule.channelStreams.foreach {
              case (channelId, streamList) =>
                jQuery(s""".stream-item[data-channel-id="${channelId}"]""").map { e: Element =>
                  val streamItem = jQuery(e)

                  if (streamList.nonEmpty) {
                    streamItem.find(".stream-schedule").empty()
                    streamList
                      .sortWith {
                        case (_: LiveStream, _) => false
                        case (_, _: LiveStream) => true
                        case _                  => false
                      }
                      .take(3)
                      .foreach { stream =>
                        streamItem
                          .find(".stream-schedule")
                          .append(createStreamItem(stream).render)
                      }
                  } else {
                    streamItem
                      .find(".stream-schedule")
                      .empty()
                      .append(
                        div(cls := "stream-schedule-item")(
                          div(cls := "stream-schedule-item-title")(""),
                          div(cls := "stream-schedule-item-time")("Offline")
                        ).render
                      )
                  }

                  streamItem.find(".stream-updating-spinner").addClass("hidden")
                }
            }
          }
          case None => ()
        }
      }
      .runAsync

  }

  def createStreamItem(stream: YouTubeStream) = {
    stream match {
      case live: LiveStream => {
        a(
          cls := "stream-schedule-item live-stream-link",
          href := live.url,
          target := "_blank",
          rel := "noopener"
        )(
          div(cls := "stream-schedule-item-title")(live.title),
          div(cls := "stream-schedule-item-time")(span(cls := "live-label")("LIVE"))
        )
      }
      case upcoming: UpcomingStream => {

        val startTimeString =
          upcoming.info.details.startTime.map(_.format(dateTimeFormatter).toUpperCase).getOrElse("")

        a(
          cls := "stream-schedule-item",
          href := upcoming.url,
          target := "_blank",
          rel := "noopener"
        )(
          div(cls := "stream-schedule-item-title")(upcoming.title),
          div(cls := "stream-schedule-item-time")(startTimeString)
        )
      }
    }
  }

}
