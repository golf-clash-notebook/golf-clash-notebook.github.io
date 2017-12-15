/*
 * MIT License
 *
 * Copyright (c) 2017 golf-clash-notebook
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

import scala.scalajs.js
import org.scalajs.dom.Element
import org.scalajs.jquery.jQuery

import monix.execution.Scheduler.Implicits.global

///////////////////////////////////
// TODO: Make this less terrible //
///////////////////////////////////
object clubs {

  val init = () => initWindCharts()

  def initWindCharts() = {

    Club.All.map { allClubs =>
      // Popover for larger screens

      jQuery("""[data-toggle="popover"]""").each { e: Element =>
        val clubName = jQuery(e).data("club-name").asInstanceOf[String]

        val maybeClub = allClubs.find(_.name.toLowerCase.equals(clubName.toLowerCase))

        maybeClub match {
          case Some(club) => {
            jQuery(e)
              .asInstanceOf[js.Dynamic]
              .popover(
                js.Dynamic.literal(
                  "html"      -> true,
                  "content"   -> (() => createWindChart(club).render),
                  "container" -> "body"
                )
              )

            jQuery(e).removeClass("hidden")
            ()
          }
          case None => {
            println(s"Oops couldn't find club for name: $clubName")
          }
        }
      }

      // Modal for mobile

      jQuery(""".btn-wind-chart-modal""").each { e: Element =>
        val clubName  = jQuery(e).data("club-name").asInstanceOf[String]
        val maybeClub = allClubs.find(_.name.toLowerCase.equals(clubName.toLowerCase))

        maybeClub match {
          case Some(club) => {
            jQuery(e).click { () =>
              jQuery("#modal-wind-chart").remove()
              jQuery("body").append(createWindChartModal("modal-wind-chart", club).render);
              jQuery("#modal-wind-chart").asInstanceOf[js.Dynamic].modal("show")
            }

            jQuery(e).removeClass("hidden")
          }
          case None => {
            println(s"Oops couldn't find club for name: $clubName")
          }
        }

      }

    }.runAsync
  }

  def createWindChartModal(chartId: String, club: Club) = {

    import scalatags.JsDom.all._

    div(id := chartId, cls := "modal fade", tabindex := "-1", role := "dialog")(
      div(cls := "modal-dialog modal-sm", role := "document")(
        div(cls := "modal-content")(
          div(cls := "modal-header pad-8")(
            button(`type` := "button", cls := "close", data("dismiss") := "modal")(
              span(cls := "text-extra-large")("\u00D7")
            ),
            h4(cls := "modal-title text-center")(club.name)
          ),
          div(cls := "modal-body")(
            createWindChart(club)
          )
        )
      )
    )
  }

  def createWindChart(club: Club) = {

    import scalatags.JsDom.all._
    import scalatags.JsDom.svgAttrs.{ fontFamily => _, fontSize => _, id => _, tag => _, _ }
    import scalatags.JsDom.svgTags.{ tag => _, _ }

    val windPerRing = 1 + ((100 - club.accuracy(0)) * 0.02)

    div(cls := "wind-chart")(
      svg(viewBox := "0 0 100 100", preserveAspectRatio := "xMidYMid meet")(
        circle(
          cx := "50%",
          cy := "50%",
          r := "50",
          strokeWidth := "0.1",
          stroke := "#000",
          fill := "#ffffff"
        ),
        circle(
          cx := "50%",
          cy := "50%",
          r := "40",
          strokeWidth := "0.1",
          stroke := "#000",
          fill := "#000000",
          fillOpacity := "0.1"
        ),
        circle(
          cx := "50%",
          cy := "50%",
          r := "30",
          strokeWidth := "0.1",
          stroke := "#000",
          fill := "#70c5fe"
        ),
        circle(
          cx := "50%",
          cy := "50%",
          r := "20",
          strokeWidth := "0.1",
          stroke := "#000",
          fill := "#ffc350"
        ),
        circle(
          cx := "50%",
          cy := "50%",
          r := "10",
          strokeWidth := "0.1",
          stroke := "#000",
          fill := "#fbff65"
        ),
        circle(
          cx := "50%",
          cy := "50%",
          r := "0.5",
          strokeWidth := "0.1",
          stroke := "#000",
          fill := "#000000"
        ),
        text(
          id := s"${club.##}-ring-5",
          x := "50%",
          y := "5",
          dy := "0.1em",
          textAnchor := "middle",
          alignmentBaseline := "middle",
          fontFamily := "Lato",
          fontSize := "5"
        )(f"${windPerRing * 5}%.1f"),
        text(
          id := s"${club.##}-ring-4",
          x := "50%",
          y := "15",
          dy := "0.1em",
          textAnchor := "middle",
          alignmentBaseline := "middle",
          fontFamily := "Lato",
          fontSize := "5"
        )(f"${windPerRing * 4}%.1f"),
        text(
          id := s"${club.##}-ring-3",
          x := "50%",
          y := "25",
          dy := "0.1em",
          textAnchor := "middle",
          alignmentBaseline := "middle",
          fontFamily := "Lato",
          fontSize := "5"
        )(f"${windPerRing * 3}%.1f"),
        text(
          id := s"${club.##}-ring-2",
          x := "50%",
          y := "35",
          dy := "0.1em",
          textAnchor := "middle",
          alignmentBaseline := "middle",
          fontFamily := "Lato",
          fontSize := "5"
        )(f"${windPerRing * 2}%.1f"),
        text(
          id := s"${club.##}-ring-1",
          x := "50%",
          y := "45",
          dy := "0.1em",
          textAnchor := "middle",
          alignmentBaseline := "middle",
          fontFamily := "Lato",
          fontSize := "5"
        )(f"${windPerRing * 1}%.1f")
      ),
      tag("nav")(cls := "text-center")(
        ul(
          cls := s"club-pagination club-pagination-${club.##} pagination pagination-sm justify-content-center"
        )(
          ((1 to 10).map { level =>
            val disabled = level > club.maxLevel || club.accuracy(level - 1) < 0
            val classes  = if (disabled) "disabled" else if (level == 1) "active" else ""
            val onClickF =
              if (disabled) { () =>
                ()
              } else { () =>
                updateRings(club, level)
              }
            li(cls := "page-item " ++ classes)(a(cls := "page-link", onclick := onClickF)(level))
          }): _*
        )
      )
    )

  }

  def updateRings(club: Club, level: Int) = {

    val acc         = club.accuracy(level - 1)
    val windPerRing = 1 + ((100 - acc) * 0.02)

    jQuery(s".club-pagination-${club.##} > li").each { e =>
      jQuery(e).removeClass("active")
    }

    jQuery(s".club-pagination-${club.##} > li:eq(${level - 1})").addClass("active")

    (1 to 5).foreach { ringNum =>
      jQuery(s"#${club.##}-ring-${ringNum}").text(f"${windPerRing * ringNum}%.1f")
    }
  }

}
