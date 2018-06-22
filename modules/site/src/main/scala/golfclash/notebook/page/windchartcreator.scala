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
import golfclash.notebook.wind._
import jspdf._
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import scalatags.JsDom.all._

object windchartcreator {

  sealed abstract class ChartVariant(val name: String) extends Product with Serializable
  case object WindPerRing                              extends ChartVariant("Wind Per Ring")
  case object RingsPerWind                             extends ChartVariant("Rings Per Wind")

  object ChartVariant {
    val All = List(WindPerRing, RingsPerWind)
  }

  case class ClubLevel(level: Int, club: Club)

  val RingColors = List(
    (251.0, 255.0, 101.0),
    (255.0, 195.0, 80.0),
    (112.0, 197.0, 254.0),
    (225.0, 225.0, 225.0),
    (255.0, 255.0, 255.0)
  )

  val init = () => {

    if (util.isFacebookBrowser()) {
      jQuery("#facebook-browser-warning").removeClass("hidden")
    }

    WindMode.All.foreach { mode =>
      val option = jQuery("<option>", js.Dynamic.literal("value" -> mode.name, "text" -> mode.name))
      jQuery("#mode-select").append(option)
    }

    ChartVariant.All.foreach { variant =>
      val option =
        jQuery("<option>", js.Dynamic.literal("value" -> variant.name, "text" -> variant.name))
      jQuery("#variant-select").append(option)
    }

    Club.All.map { allClubsList =>
      Club.Category.All.foreach { clubCategory =>
        val categoryClubs = allClubsList.filter(_.clubCategory == Some(clubCategory))
        val optGroup =
          jQuery("<optgroup>", js.Dynamic.literal("label" -> clubCategory.printableName))
        categoryClubs.foreach { club =>
          if (GolfClashNotebookApp.currentMode() == GolfClashNotebookApp.Dev) {
            // TODO: Development purposes...
            if (club.name.toLowerCase.contains("thor's")) {
              addClubLevel(ClubLevel(6, club))
            }
            if (club.name.toLowerCase.contains("sniper")) {
              addClubLevel(ClubLevel(10, club))
            }
            if (club.name.toLowerCase.contains("backbone")) {
              addClubLevel(ClubLevel(10, club))
            }
            if (club.name.toLowerCase.contains("hornet")) {
              addClubLevel(ClubLevel(8, club))
            }
            if (club.name.toLowerCase.contains("rapier")) {
              addClubLevel(ClubLevel(8, club))
            }
            if (club.name.toLowerCase.contains("nirvana")) {
              addClubLevel(ClubLevel(8, club))
            }
            if (club.name.toLowerCase.contains("spitfire")) {
              addClubLevel(ClubLevel(5, club))
            }
          }

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
        (for {
          variant <- currentChartVariant
          mode    <- currentWindMode
          title   <- currentChartTitle
          clubs   <- currentClubs
        } yield {
          generateChart(variant.getOrElse(WindPerRing), mode.getOrElse(Power0), title, clubs)
        }).runAsync
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

  def currentChartTitle(): Task[String] = {
    Task {
      Try(jQuery("#title-input").`val`.asInstanceOf[String]).toOption.getOrElse("")
    }
  }

  def currentWindMode(): Task[Option[WindMode]] = {
    Task {
      Try(jQuery("#mode-select").`val`.asInstanceOf[String]).toOption
        .flatMap(value => WindMode.All.find(_.name == value))
    }
  }

  def currentChartVariant(): Task[Option[ChartVariant]] = {
    Task {
      Try(jQuery("#variant-select").`val`.asInstanceOf[String]).toOption
        .flatMap(value => ChartVariant.All.find(_.name == value))
    }
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
          h5(
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
          jQuery("#create-chart-controls").addClass("hidden")
          jQuery("#club-add-btn").removeClass("hidden")
        }
        case _ => {
          jQuery("#create-chart-controls").removeClass("hidden")
          jQuery("#club-add-btn").removeClass("hidden")
        }
      }

      ()
    }
  }

  def generateChart(
    variant: ChartVariant,
    mode: WindMode,
    title: String,
    allClubs: List[ClubLevel]
  ): Unit = {
    variant match {
      case WindPerRing  => generateWindPerRingChart(mode, title, allClubs)
      case RingsPerWind => generateRingsPerWindChart(mode, title, allClubs)
    }
  }

  def generateWindPerRingChart(mode: WindMode, title: String, allClubs: List[ClubLevel]): Unit = {

    val pdf = JSPdf("portrait", "in")

    val pageXMargin = 0.75
    val pageYMargin = 0.85

    val clubRowHeight   = 1.35
    val clubColumnWidth = (8.5 - (pageXMargin * 2)) / 4

    allClubs
      .sliding(7, 7)
      .zipWithIndex
      .map {
        case (pageClubs, pageNum) =>
          if (pageNum > 0) pdf.addPage()

          if (title.trim.nonEmpty) {
            pdf.setTextColor(0d)
            pdf.setFontSize(16d)
            pdf.text(title, 4.25, pageYMargin, "center")
          }

          pdf.setFontSize(10d)

          pdf.text(s"Max", 3.375, 1.10, "center")
          pdf.text(s"Mid", 5.125, 1.10, "center")
          pdf.text(s"Min", 6.875, 1.10, "center")

          pdf.setTextColor(150d)
          pdf.setFontSize(9d)

          val captions = List(
            s"Generated using ${mode.name} adjustments.",
            "Rough Iron & Sand Wedge mileage will vary. They are SWAGs!"
          )

          val captionLineHeight = 0.17
          val captionFirstLine  = 11.25 - (captions.size * captionLineHeight)

          captions.zipWithIndex.foreach {
            case (caption, lineNum) =>
              pdf.text(caption, 8.5 / 2, captionFirstLine + (captionLineHeight * lineNum), "center")
          }

          pdf.setFontSize(10d)
          pdf.setLineWidth(0.0025)

          pageClubs.toList.zipWithIndex
            .map {
              case (clubLevel, clubRow) =>
                ClubImage(clubLevel.club).map { base64Image =>
                  val rowTopY            = pageYMargin + (clubRow * clubRowHeight)
                  val firstColumnCenterX = pageXMargin + (clubColumnWidth / 2)

                  pdf.setPage(pageNum + 1)

                  pdf.setTextColor(50d)

                  pdf.addImage(base64Image, "png", pageXMargin + 0.4, rowTopY + 0.2, 1.0, 1.0)

                  pdf.text(
                    s"""${clubLevel.club.name.replaceAll("The", "").trim} ${clubLevel.level}""",
                    firstColumnCenterX,
                    rowTopY + 1.25,
                    "center"
                  )
                  pdf.text(
                    s"Accuracy: ${clubLevel.club.accuracy(clubLevel.level - 1)}",
                    firstColumnCenterX,
                    rowTopY + 1.41,
                    "center"
                  )

                  pdf.setTextColor(0d)

                  List(
                    maxPower(clubLevel.club, clubLevel.level, mode),
                    midPower(clubLevel.club, clubLevel.level, mode),
                    minPower(clubLevel.club, clubLevel.level, mode)
                  ).zipWithIndex.foreach {
                    case (clubPower, clubColumn) =>
                      val columnCenterX      = pageXMargin + ((clubColumn + 1) * clubColumnWidth) + (clubColumnWidth / 2)
                      val chartTitleY        = rowTopY + 0.25
                      val chartY             = chartTitleY + 0.1
                      val ringRowHeight      = 0.23
                      val ringRowTextYOffset = 0.17

                      val windPerRingValue = windPerRing(clubLevel.club, clubLevel.level, clubPower)

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
                          f"${windPerRingValue * (ringNum + 1)}%.2f",
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
      .map { _ =>
        val filename =
          if (title.nonEmpty) s"WindChart-${title}.pdf"
          else "WindChart.pdf"
        pdf.save(filename)
      }
      .runAsync

    ()

  }

  def generateRingsPerWindChart(mode: WindMode, title: String, allClubs: List[ClubLevel]): Unit = {

    val pdf = JSPdf("portrait", "in")

    val pageXMargin = 0.75
    val pageYMargin = 0.85

    allClubs
      .sliding(7, 7)
      .zipWithIndex
      .map {
        case (pageClubs, pageNum) =>
          if (pageNum > 0) pdf.addPage()

          if (title.trim.nonEmpty) {
            pdf.setTextColor(0d)
            pdf.setFontSize(16d)
            pdf.text(title, 4.25, pageYMargin, "center")
          }

          pdf.setFontSize(10d)
          pdf.setTextColor(150d)
          pdf.setFontSize(9d)

          val captions = List(
            s"Generated using ${mode.name} adjustments.",
            "Rough Iron & Sand Wedge mileage will vary. They are SWAGs!"
          )

          val captionLineHeight = 0.17
          val captionFirstLine  = 11.3 - (captions.size * captionLineHeight)

          captions.zipWithIndex.foreach {
            case (caption, lineNum) =>
              pdf.text(caption, 8.5 / 2, captionFirstLine + (captionLineHeight * lineNum), "center")
          }

          val clubColumnWidth = 1.0

          pdf.setTextColor(30d)
          pdf.setFontSize(8d)
          pdf.setLineWidth(0.0005)

          val windRowStartY = pageYMargin + 0.6
          val windRowHeight = 0.122

          val windRange = (BigDecimal(1.0) to BigDecimal(16.0) by BigDecimal(0.2))

          pageClubs.toList.zipWithIndex
            .map {
              case (clubLevel, clubColumn) =>
                ClubImage(clubLevel.club).map { base64Image =>
                  val columnCenterX = pageXMargin + (clubColumnWidth * clubColumn) + (clubColumnWidth / 2)

                  val imageX = columnCenterX - 0.25
                  val imageY = pageYMargin

                  pdf.setPage(pageNum + 1)

                  pdf.addImage(base64Image, "png", imageX, imageY, 0.5, 0.5)

                  pdf.setTextColor(80d)
                  pdf.text(
                    s"""${clubLevel.club.name.replaceAll("The", "").trim} ${clubLevel.level}""",
                    columnCenterX,
                    imageY + 0.55,
                    "center"
                  )
                  pdf.setTextColor(30d)

                  windRange.zipWithIndex.foreach {
                    case (wind, windRow) =>
                      val windPerRingMax = windPerRing(
                        clubLevel.club,
                        clubLevel.level,
                        maxPower(clubLevel.club, clubLevel.level, mode)
                      )
                      val windPerRingMid = windPerRing(
                        clubLevel.club,
                        clubLevel.level,
                        midPower(clubLevel.club, clubLevel.level, mode)
                      )
                      val windPerRingMin = windPerRing(
                        clubLevel.club,
                        clubLevel.level,
                        minPower(clubLevel.club, clubLevel.level, mode)
                      )

                      val maxRings = wind.doubleValue / windPerRingMax
                      val midRings = wind.doubleValue / windPerRingMid
                      val minRings = wind.doubleValue / windPerRingMin

                      val textY    = windRowStartY + (windRow * windRowHeight) + windRowHeight - 0.02
                      val maxTextX = columnCenterX - (3 * clubColumnWidth / 11)
                      val midTextX = columnCenterX
                      val minTextX = columnCenterX + (3 * clubColumnWidth / 11)

                      val (maxRingColorR, maxRingColorG, maxRingColorB) =
                        RingColors((maxRings - 0.1).max(0).floor.toInt % RingColors.size)
                      val (midRingColorR, midRingColorG, midRingColorB) =
                        RingColors((midRings - 0.1).max(0).floor.toInt % RingColors.size)
                      val (minRingColorR, minRingColorG, minRingColorB) =
                        RingColors((minRings - 0.1).max(0).floor.toInt % RingColors.size)

                      pdf.setFontSize(6d)
                      pdf.setTextColor(120d)
                      pdf.text(
                        f"${wind}%.1f",
                        (columnCenterX - (clubColumnWidth / 2)),
                        textY - 0.01,
                        "center"
                      )
                      pdf.setTextColor(30d)
                      pdf.setFontSize(8d)

                      pdf.setFillColor(maxRingColorR, maxRingColorG, maxRingColorB)
                      pdf.rect(
                        columnCenterX - (2 * clubColumnWidth / 5),
                        windRowStartY + (windRow * windRowHeight),
                        (4 * clubColumnWidth / 15) + 0.0075,
                        windRowHeight,
                        "F"
                      )

                      pdf.setFillColor(midRingColorR, midRingColorG, midRingColorB)
                      pdf.rect(
                        columnCenterX - (clubColumnWidth / 8),
                        windRowStartY + (windRow * windRowHeight),
                        (4 * clubColumnWidth / 15) + 0.0075,
                        windRowHeight,
                        "F"
                      )

                      pdf.setFillColor(minRingColorR, minRingColorG, minRingColorB)
                      pdf.rect(
                        columnCenterX + (clubColumnWidth / 8),
                        windRowStartY + (windRow * windRowHeight),
                        (4 * clubColumnWidth / 15) + 0.0075,
                        windRowHeight,
                        "F"
                      )

                      pdf.setTextColor(30d)
                      pdf.text(f"${(maxRings * 10).floor / 10}%.1f", maxTextX, textY, "center")
                      pdf.text(f"${(midRings * 10).floor / 10}%.1f", midTextX, textY, "center")
                      pdf.text(f"${(minRings * 10).floor / 10}%.1f", minTextX, textY, "center")
                  }

                  // Table and cell borders
                  pdf.setDrawColor(0)
                  pdf.setFillColor(255)

                  pdf.rect(
                    columnCenterX - (2 * clubColumnWidth / 5),
                    windRowStartY,
                    (4 * clubColumnWidth / 5),
                    windRowHeight * windRange.size,
                    "D"
                  )

                  pdf.line(
                    columnCenterX - (clubColumnWidth / 8),
                    windRowStartY,
                    columnCenterX - (clubColumnWidth / 8),
                    windRowStartY + (windRowHeight * windRange.size)
                  )

                  pdf.line(
                    columnCenterX + (clubColumnWidth / 8),
                    windRowStartY,
                    columnCenterX + (clubColumnWidth / 8),
                    windRowStartY + (windRowHeight * windRange.size)
                  )

                  (1 to windRange.size - 1).foreach { windRow =>
                    pdf.line(
                      columnCenterX - (2 * clubColumnWidth / 5),
                      windRowStartY + (windRow * windRowHeight),
                      columnCenterX + (2 * clubColumnWidth / 5),
                      windRowStartY + (windRow * windRowHeight)
                    )
                  }
                }

            }
      }
      .toList
      .flatten
      .sequence
      .map { _ =>
        val filename =
          if (title.nonEmpty) s"WindChart-${title}.pdf"
          else "WindChart.pdf"
        pdf.save(filename)
      }
      .runAsync

    ()
  }

}
