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
    import scalatags.JsDom.svgAttrs.{
      fontFamily => _,
      fontSize => _,
      id => _,
      style => _,
      tag => _,
      _
    }
    import scalatags.JsDom.svgTags.{ tag => _, _ }

    val clubPower   = wind.maxPower(club, 1, wind.Power0)
    val windPerRing = wind.windPerRing(club, 1, clubPower)

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
        )(windRingText(5, windPerRing)),
        text(
          id := s"${club.##}-ring-4",
          x := "50%",
          y := "15",
          dy := "0.1em",
          textAnchor := "middle",
          alignmentBaseline := "middle",
          fontFamily := "Lato",
          fontSize := "5"
        )(windRingText(4, windPerRing)),
        text(
          id := s"${club.##}-ring-3",
          x := "50%",
          y := "25",
          dy := "0.1em",
          textAnchor := "middle",
          alignmentBaseline := "middle",
          fontFamily := "Lato",
          fontSize := "5"
        )(windRingText(3, windPerRing)),
        text(
          id := s"${club.##}-ring-2",
          x := "50%",
          y := "35",
          dy := "0.1em",
          textAnchor := "middle",
          alignmentBaseline := "middle",
          fontFamily := "Lato",
          fontSize := "5"
        )(windRingText(2, windPerRing)),
        text(
          id := s"${club.##}-ring-1",
          x := "50%",
          y := "45",
          dy := "0.1em",
          textAnchor := "middle",
          alignmentBaseline := "middle",
          fontFamily := "Lato",
          fontSize := "5"
        )(windRingText(1, windPerRing))
      ),
      div(cls := "text-tiny text-semi-muted text-center", style := "margin-top: 10px;")(
        "Club Level"
      ),
      tag("nav")(cls := "text-center")(
        ul(
          id := s"club-level-${club.##}",
          cls := s"club-level-pagination pagination pagination-sm justify-content-center"
        )(
          ((1 to 10).map { level =>
            val disabled = level > club.maxLevel || club.accuracy(level - 1) < 0
            val classes  = if (disabled) "disabled" else if (level == 1) "active" else ""
            li(cls := "page-item " ++ classes)(
              a(cls := "page-link", onclick := clubLevelClicked(club)(level - 1))(level)
            )
          }): _*
        )
      ),
      div(cls := "text-tiny text-semi-muted text-center")("Club Power"),
      tag("nav")(cls := "text-center")(
        ul(
          id := s"club-power-${club.##}",
          cls := s"club-power-pagination pagination pagination-sm justify-content-center"
        )(
          li(cls := "page-item active")(
            a(cls := "page-link", onclick := clubPowerClicked(club)(0))("Max")
          ),
          li(cls := "page-item")(
            a(cls := "page-link", onclick := clubPowerClicked(club)(1))("Mid")
          ),
          li(cls := "page-item")(
            a(cls := "page-link", onclick := clubPowerClicked(club)(2))("Min")
          )
        )
      ),
      div(cls := "text-tiny text-semi-muted text-center")("Ball Power"),
      tag("nav")(cls := "text-center")(
        ul(
          id := s"ball-power-${club.##}",
          cls := s"ball-power-pagination pagination pagination-sm justify-content-center"
        )(
          li(cls := "page-item active")(
            a(cls := "page-link", onclick := ballPowerClicked(club)(0))("0")
          ),
          li(cls := "page-item")(a(cls := "page-link", onclick := ballPowerClicked(club)(1))("1")),
          li(cls := "page-item")(a(cls := "page-link", onclick := ballPowerClicked(club)(2))("2")),
          li(cls := "page-item")(a(cls := "page-link", onclick := ballPowerClicked(club)(3))("3")),
          li(cls := "page-item")(a(cls := "page-link", onclick := ballPowerClicked(club)(4))("4")),
          li(cls := "page-item")(a(cls := "page-link", onclick := ballPowerClicked(club)(5))("5"))
        )
      )
    )
  }

  def clubLevelClicked(club: Club)(ix: Int) = () => {
    jQuery(s"#club-level-${club.##} > li").removeClass("active")
    jQuery(s"#club-level-${club.##} > li:eq(${ix})").addClass("active")
    updateRings(club)
  }

  def clubPowerClicked(club: Club)(ix: Int) = () => {
    jQuery(s"#club-power-${club.##} > li").removeClass("active")
    jQuery(s"#club-power-${club.##} > li:eq(${ix})").addClass("active")
    updateRings(club)
  }

  def ballPowerClicked(club: Club)(ix: Int) = () => {
    jQuery(s"#ball-power-${club.##} > li").removeClass("active")
    jQuery(s"#ball-power-${club.##} > li:eq(${ix})").addClass("active")
    updateRings(club)
  }

  def updateRings(club: Club) = {

    val currentLevelIx     = jQuery(s"#club-level-${club.##} > li.active").index()
    val currentClubPowerIx = jQuery(s"#club-power-${club.##} > li.active").index()
    val currentBallPowerIx = jQuery(s"#ball-power-${club.##} > li.active").index()

    val ballPower = Option(currentBallPowerIx)
      .collect {
        case 0 => wind.Power0
        case 1 => wind.Power1
        case 2 => wind.Power2
        case 3 => wind.Power3
        case 4 => wind.Power4
        case 5 => wind.Power5
      }
      .getOrElse(wind.Power0)

    val clubPower = Option(currentClubPowerIx)
      .collect {
        case 0 => wind.maxPower(club, currentLevelIx + 1, ballPower)
        case 1 => wind.midPower(club, currentLevelIx + 1, ballPower)
        case 2 => wind.minPower(club, currentLevelIx + 1, ballPower)
      }
      .getOrElse(wind.maxPower(club, currentLevelIx + 1, ballPower))

    val windPerRing = wind.windPerRing(club, currentLevelIx + 1, clubPower)

    (1 to 5).foreach { ringNum =>
      jQuery(s"#${club.##}-ring-${ringNum}").text(formatWindRingText(windPerRing * ringNum))
    }
  }

  def windRingText(ring: Int, windPerRing: Double): String = {
    formatWindRingText(ring * windPerRing)
  }

  def formatWindRingText(wind: Double): String = {
    f"$wind%.1f"
  }

}
