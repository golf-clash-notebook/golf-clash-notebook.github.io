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
import scala.util.Try

import org.scalajs.jquery.jQuery

import cats.implicits._
import jspdf._
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import scalatags.JsDom.all._

object windchartcreator {

  case class ClubLevel(level: Int, club: Club)

  val init = () => {

    if (util.isFacebookBrowser()) {
      jQuery("#facebook-browser-warning").removeClass("hidden")
    }

    Club.All.map { allClubsList =>
      Club.Category.All.foreach { clubCategory =>
        val categoryClubs = allClubsList.filter(_.clubCategory == Some(clubCategory))
        val optGroup =
          jQuery("<optgroup>", js.Dynamic.literal("label" -> clubCategory.printableName))
        categoryClubs.foreach { club =>
          optGroup.append(
            jQuery("<option>", js.Dynamic.literal("value" -> club.id, "text" -> club.name))
          )
        }

        jQuery("#club-select").append(optGroup)
      }

      jQuery("#club-select").change { () =>
        val clubId = jQuery("#club-select").`val`.asInstanceOf[String]
        allClubsList.find(_.id == clubId) match {
          case Some(club) => clubSelected(club)
          case None       => println("oops...")
        }
      }

      allClubsList.headOption.map { firstClub =>
        jQuery("#club-select").`val`(firstClub.id).change()
      }

      jQuery("#club-add-btn").click { () =>
        currentClubLevel().map { maybeClubLevel =>
          maybeClubLevel.map(addClubLevel)
        }.runAsync
      }

      jQuery("#create-wind-chart-btn").click { () =>
        currentClubs.map(generateWindChart).runAsync
      }
    }.runAsync

  }

  def clubSelected(club: Club) = {
    jQuery("#club-level-select").empty()
    (1 to club.maxLevel).foreach { level =>
      jQuery("#club-level-select").append(
        jQuery(
          "<option>",
          js.Dynamic.literal("value" -> level.toString, "text" -> level.toString)
        )
      )
    }
    jQuery("#club-level-select").prop("disabled", false)
  }

  def currentClubLevel(): Task[Option[ClubLevel]] = {
    currentClub().zip(currentLevel()).map {
      case (clubOpt, levelOpt) =>
        for {
          club  <- clubOpt
          level <- levelOpt
        } yield (ClubLevel(level, club))
    }
  }

  def currentClub(): Task[Option[Club]] = {
    Club.All.map { allClubsList =>
      val clubId = jQuery("#club-select").`val`.asInstanceOf[String]
      allClubsList.find(_.id == clubId)
    }
  }

  def currentLevel(): Task[Option[Int]] = {
    Task(Try(jQuery("#club-level-select").`val`.asInstanceOf[String].toInt).toOption)
  }

  def addClubLevel(clubLevel: ClubLevel): Unit = {

    val sanitizedName =
      clubLevel.club.name.replaceAll("The", "").replaceAll(" ", "").replaceAll("'", "")
    val clubImagePath = s"""/img/golfclash/clubs/$sanitizedName-64x64.png"""

    val removeClubF = { () =>
      jQuery(s"""div[data-club-id="${clubLevel.club.id}"]""").first().remove()
      updateActionsVisibility().runAsync
    }

    jQuery("#current-club-list").append(
      div(
        cls := "wind-chart-club-card",
        data("club-id") := clubLevel.club.id,
        data("club-level") := clubLevel.level
      )(
        img(cls := "img-inline", src := clubImagePath),
        div(cls := "club-info margin-left-24")(
          h4(
            span(s"${clubLevel.club.name} ${clubLevel.level}")
          )
        ),
        button(cls := "btn btn-danger remove-club-btn pull-right", onclick := removeClubF)(
          i(cls := "fas fa-times")
        )
      ).render
    )

    updateActionsVisibility().runAsync

    ()
  }

  def currentClubs(): Task[List[ClubLevel]] = {
    Club.All.map { allClubsList =>
      jQuery(".wind-chart-club-card[data-club-id]")
        .map { element =>
          val clubId    = jQuery(element).data("club-id").asInstanceOf[String]
          val clubLevel = jQuery(element).data("club-level").asInstanceOf[Int]
          allClubsList.find(_.id == clubId).map(ClubLevel(clubLevel, _))
        }
        .toArray
        .map(_.asInstanceOf[Option[ClubLevel]])
        .toList
        .flatten
    }
  }

  def updateActionsVisibility(): Task[Unit] = {
    currentClubs().map { clubs =>
      clubs match {
        case Nil => {
          jQuery("#create-wind-chart-btn").addClass("hidden")
          jQuery("#club-add-btn").removeClass("hidden")
        }
        case _ => {
          jQuery("#create-wind-chart-btn").removeClass("hidden")
          jQuery("#club-add-btn").removeClass("hidden")
        }
      }

      ()
    }
  }

  def generateWindChart(allClubs: List[ClubLevel]): Unit = {

    val pdf = JSPdf("portrait", "in")

    val pageXMargin = 0.75
    val pageYMargin = 0.75

    val clubRowHeight   = 1.35
    val clubColumnWidth = (8.5 - (pageXMargin * 2)) / 4

    allClubs
      .sliding(7, 7)
      .zipWithIndex
      .map {
        case (pageClubs, pageNum) =>
          if (pageNum > 0) pdf.addPage()

          pdf.setTextColor(100d)
          pdf.setFontSize(12d)

          pdf.text(s"Max", 3.375, 1.0, "center")
          pdf.text(s"Mid", 5.125, 1.0, "center")
          pdf.text(s"Min", 6.875, 1.0, "center")

          pdf.setTextColor(150d)
          pdf.setFontSize(9d)

          pdf.text(
            "Driver, Wood, Long Iron, Short Iron [ Max | Mid | Min ] @ (100%, 87.5%, 75%).",
            8.5 / 2,
            10.68,
            "center"
          )
          pdf.text(
            "Wedge, Rough Iron, Sand Wedge [ Max | Mid | Min ] @ (100%, 50%, 25%).",
            8.5 / 2,
            10.85,
            "center"
          )
          pdf.text(
            "Wedge, Rough Iron, Sand Wedge mileage will vary. They are SWAGs!",
            8.5 / 2,
            11.02,
            "center"
          )

          pdf.setFontSize(10d)
          pdf.setLineWidth(0.0025)

          pageClubs.toList.zipWithIndex
            .map {
              case (clubLevel, clubRow) =>
                ClubImage(clubLevel.club).map { base64Image =>
                  val rowTopY            = pageYMargin + (clubRow * clubRowHeight)
                  val firstColumnCenterX = pageXMargin + (clubColumnWidth / 2)

                  pdf.setPage(pageNum + 1)

                  pdf.setTextColor(150d)

                  pdf.addImage(base64Image, "png", pageXMargin + 0.4, rowTopY + 0.2, 1.0, 1.0)

                  pdf.text(
                    s"""${clubLevel.club.name.replaceAll("The", "").trim} ${clubLevel.level}""",
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
                      val columnCenterX      = pageXMargin + ((clubColumn + 1) * clubColumnWidth) + (clubColumnWidth / 2)
                      val chartTitleY        = rowTopY + 0.25
                      val chartY             = chartTitleY + 0.1
                      val ringRowHeight      = 0.23
                      val ringRowTextYOffset = 0.17

                      val windPerRing = wind.windPerRing(clubLevel.club, clubLevel.level)

                      // Ring background colors
                      (RingColors).zipWithIndex.foreach {
                        case ((ringColorR, ringColorG, ringColorB), ringNum) =>
                          pdf.setFillColor(ringColorR, ringColorG, ringColorB)
                          pdf.rect(
                            columnCenterX - (clubColumnWidth / 3),
                            chartY + (ringNum * ringRowHeight),
                            (2 * clubColumnWidth / 3),
                            ringRowHeight,
                            "F"
                          )
                      }

                      // Ring wind values
                      (0 until (RingColors.size * 2)).foreach { ringNum =>
                        val subColumn        = ringNum / RingColors.size
                        val subColumnCenterX = columnCenterX - (clubColumnWidth / 6) + (subColumn * clubColumnWidth / 3)

                        pdf.text(
                          f"${windPerRing * (ringNum + 1) / clubPowerRatio}%.2f",
                          subColumnCenterX,
                          chartY + (ringNum * ringRowHeight) + ringRowTextYOffset - (subColumn * (ringRowHeight * RingColors.size)),
                          "center"
                        )
                      }

                      pdf.setDrawColor(150.0, 150.0, 150.0)

                      // Middle line seperator
                      pdf.line(
                        columnCenterX,
                        chartY,
                        columnCenterX,
                        chartY + (ringRowHeight * 5)
                      )

                      // Outside border
                      pdf.rect(
                        columnCenterX - (clubColumnWidth / 3),
                        chartY,
                        (2 * clubColumnWidth / 3),
                        ringRowHeight * 5,
                        "D"
                      )
                  }
                }
            }
      }
      .toList
      .flatten
      .sequence
      .map(_ => pdf.save("WindChart.pdf"))
      .runAsync

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
