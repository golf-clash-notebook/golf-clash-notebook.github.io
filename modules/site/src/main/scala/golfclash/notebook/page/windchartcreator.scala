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

import scala.collection.mutable.ListBuffer

import org.scalajs.jquery.jQuery

import jspdf._
import monix.execution.Scheduler.Implicits.global

object windchartcreator {

  case class ClubLevel(level: Int, club: Club)

  private[this] val clubs = new ListBuffer[ClubLevel]()

  val init = () => {

    Club.All.map { allClubsList =>
      for {
        ExtraMile <- allClubsList.find(_.name.contains("Extra Mile"))
        Guardian  <- allClubsList.find(_.name.contains("Guardian"))
        Backbone  <- allClubsList.find(_.name.contains("Backbone"))
        Hornet    <- allClubsList.find(_.name.contains("Hornet"))
        Rapier    <- allClubsList.find(_.name.contains("Rapier"))
        Nirvana   <- allClubsList.find(_.name.contains("Nirvana"))
        Malibu    <- allClubsList.find(_.name.contains("Malibu"))
      } yield {
        clubs += ClubLevel(7, ExtraMile)
        clubs += ClubLevel(7, Guardian)
        clubs += ClubLevel(10, Backbone)
        clubs += ClubLevel(7, Hornet)
        clubs += ClubLevel(6, Rapier)
        clubs += ClubLevel(6, Nirvana)
        clubs += ClubLevel(7, Malibu)
      }
    }.runAsync

    jQuery("#create-wind-chart-btn").click { () =>
      generateWindChart()
    }

  }

  def generateWindChart(): Unit = {

    val pdf = JSPdf("portrait", "in")

    val pageXMargin = 0.75
    val pageYMargin = 0.65

    val clubRowHeight   = 1.45
    val clubColumnWidth = (8.5 - (pageXMargin * 2)) / 4

    pdf.setTextColor(100d)
    pdf.setFontSize(12d)
    pdf.setFontStyle("bold")

    pdf.text(s"Max", 3.375, 0.85, "center")
    pdf.text(s"Mid", 5.125, 0.85, "center")
    pdf.text(s"Min", 6.875, 0.85, "center")
    pdf.setFontStyle("normal")

    pdf.setTextColor(150d)
    pdf.setFontSize(9d)

    pdf.text(
      "Wedge, Rough Iron, Sand Wedge [ Max | Mid | Min ] @ (100%, 50%, 25%).",
      8.5 / 2,
      11.1,
      "center"
    )
    pdf.text(
      "Wedge, Rough Iron, Sand Wedge mileage will vary. They are SWAGs!",
      8.5 / 2,
      11.27,
      "center"
    )

    pdf.setFontSize(10d)
    pdf.setLineWidth(0.0025)

    clubs.toList.zipWithIndex.foreach {
      case (clubLevel, clubRow) =>
        val rowTopY            = pageYMargin + (clubRow * clubRowHeight)
        val firstColumnCenterX = pageXMargin + (clubColumnWidth / 2)

        pdf.setTextColor(150d)

        ClubImage(clubLevel.club).map { base64Image =>
          pdf.addImage(base64Image, "png", pageXMargin + 0.4, rowTopY + 0.2, 1.0, 1.0)
        }

        pdf.text(
          s"Level ${clubLevel.level}",
          firstColumnCenterX,
          rowTopY + 1.27,
          "center"
        )
        pdf.text(
          s"Accuracy: ${clubLevel.club.accuracy(clubLevel.level - 1)}",
          firstColumnCenterX,
          rowTopY + 1.45,
          "center"
        )

        pdf.setTextColor(0d)

        val ClubPowers = clubPowersFor(clubLevel.club)
        val RingColors = List(
          (251.0, 255.0, 101.0),
          (255.0, 195.0, 80.0),
          (112.0, 197.0, 254.0),
          (225.0, 225.0, 225.0),
          (255.0, 255.0, 255.0)
        )

        ClubPowers.zipWithIndex.foreach {
          case (clubPowerRatio, clubColumn) =>
            val chartCenterX       = pageXMargin + ((clubColumn + 1) * clubColumnWidth) + (clubColumnWidth / 2)
            val chartTitleY        = rowTopY + 0.25
            val chartY             = chartTitleY + 0.1
            val ringRowHeight      = 0.23
            val ringRowTextYOffset = 0.17

            val windPerRing = wind.windPerRing(clubLevel.club, clubLevel.level)

            RingColors.zipWithIndex.foreach {
              case ((ringColorR, ringColorG, ringColorB), ringNum) =>
                pdf.setFillColor(ringColorR, ringColorG, ringColorB)
                pdf.rect(
                  chartCenterX - (clubColumnWidth / 3),
                  chartY + (ringNum * ringRowHeight),
                  (2 * clubColumnWidth / 3),
                  ringRowHeight,
                  "F"
                )
                pdf.text(
                  f"${windPerRing * (ringNum + 1) / clubPowerRatio}%.2f",
                  chartCenterX,
                  chartY + (ringNum * ringRowHeight) + ringRowTextYOffset,
                  "center"
                )
            }

            // Outside border

            pdf.setDrawColor(150.0, 150.0, 150.0)
            pdf.rect(
              chartCenterX - (clubColumnWidth / 3),
              chartY + (0 * ringRowHeight),
              (2 * clubColumnWidth / 3),
              ringRowHeight * 5,
              "D"
            )
        }
    }

    pdf.save("WindChart.pdf")

    ()

  }

  private[this] def clubPowersFor(club: Club): List[Double] = {
    club.clubCategory
      .map { clubCategory =>
        clubCategory match {
          case Club.Category.RoughIrons => List(1.0, 0.5, 0.25)
          case Club.Category.SandWedges => List(1.0, 0.5, 0.25)
          case Club.Category.Wedges     => List(1.0, 0.5, 0.25)
          case _                        => List(1.0, 0.875, 0.75)
        }
      }
      .getOrElse(List(1.0, 0.875, 0.75))
  }

}
