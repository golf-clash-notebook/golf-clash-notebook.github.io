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

import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import org.scalajs.dom
import org.scalajs.dom.raw.{ SVGPoint, SVGSVGElement }
import org.scalajs.dom.{ MouseEvent, TouchEvent }
import org.scalajs.jquery.jQuery

object shotoverpower {

  var svgPlot: SVGSVGElement = null
  var svgPointTx: SVGPoint   = null

  case class ClubLevel(level: Int, club: Club)
  case class DragState(reticleNum: Int, lastPoint: SVGPoint)
  var dragState: Option[DragState] = None

  val init = () => {

    svgPlot = org.scalajs.dom.document
      .querySelector("#club-op-plot")
      .asInstanceOf[SVGSVGElement]

    svgPointTx = svgPlot.createSVGPoint()

    initCategorySelectors()
    initReticleImage(0)
    initReticleImage(1)
  }

  def initCategorySelectors(): Unit = {

    jQuery("#op-club-category-select").change { () =>
      Club.Category
        .fromString(jQuery(s"#op-club-category-select").`val`.asInstanceOf[String])
        .foreach(categorySelected)
    }

    categorySelected(Club.Category.Drivers)
  }

  def initClubControls(clubNum: Int, clubs: List[Club]): Unit = {

    jQuery(s"#club${clubNum}-select").empty()

    clubs.foreach { club =>
      jQuery(s"#club${clubNum}-select").append(
        jQuery("<option>", js.Dynamic.literal("value" -> club.id, "text" -> club.name))
      )
    }

    jQuery(s"#club${clubNum}-select").change { () =>
      val clubId = jQuery(s"#club${clubNum}-select").`val`.asInstanceOf[String]
      clubs.find(_.id == clubId) match {
        case Some(club) => clubSelected(clubNum, club)
        case None       => println("oops...")
      }
    }

    jQuery(s"#club${clubNum}-level-select").change { () =>
      resetReticle(clubNum)
    }

    jQuery(s"#club${clubNum}-ball-power-select").change { () =>
      resetReticle(clubNum)
    }

    clubs.headOption.foreach(clubSelected(clubNum, _))
  }

  def categorySelected(category: Club.Category): Unit = {
    Club.All.map { allClubsList =>
      val categoryClubs = allClubsList.filter(_.clubCategory == Some(category))
      initClubControls(0, categoryClubs)
      initClubControls(1, categoryClubs)
      resetReticles()
    }.runAsync
    ()
  }

  def clubSelected(clubNum: Int, club: Club) = {
    jQuery(s"#club${clubNum}-level-select").empty()
    (1 to club.maxLevel).foreach { level =>
      jQuery(s"#club${clubNum}-level-select").append(
        jQuery(
          "<option>",
          js.Dynamic.literal("value" -> level.toString, "text" -> s"Level ${level.toString}")
        )
      )
    }
    jQuery(s"#club${clubNum}-level-select").prop("disabled", false)
    resetReticle(clubNum)
  }

  def initReticleImage(reticleNum: Int): Unit = {
    dom.document
      .getElementById(s"club${reticleNum}-reticle-image")
      .addEventListener("mousedown", reticleMouseDragStarted(reticleNum), false)
    dom.document
      .getElementById(s"club${reticleNum}-reticle-image")
      .addEventListener("mousemove", reticleMouseDragged(reticleNum), false)
    dom.document
      .getElementById(s"club${reticleNum}-reticle-image")
      .addEventListener("mouseup", reticleMouseDragEnded(reticleNum), false)
    dom.document
      .getElementById("club-op-plot")
      .addEventListener("mouseleave", reticleMouseDragEnded(reticleNum), false)

    // TODO...
    dom.document
      .getElementById(s"club${reticleNum}-reticle-image")
      .addEventListener("touchstart", reticleTouchStarted(reticleNum), false)
    dom.document
      .getElementById(s"club${reticleNum}-reticle-image")
      .addEventListener("touchmove", reticleTouchMoved(reticleNum), false)
    dom.document
      .getElementById(s"club${reticleNum}-reticle-image")
      .addEventListener("touchend", reticleTouchEnded(reticleNum), false)
    dom.document
      .getElementById("club-op-plot")
      .addEventListener("touchleave", reticleTouchEnded(reticleNum), false)
    dom.document
      .getElementById(s"club${reticleNum}-reticle-image")
      .addEventListener("touchcancel", reticleTouchEnded(reticleNum), false)
  }

  def reticleMouseDragStarted(reticleNum: Int)(mouseEvent: MouseEvent): Unit = {
    mouseEvent.preventDefault()
    dragState = Some(DragState(reticleNum, positionForMouseEvent(mouseEvent)))
  }

  def reticleMouseDragEnded(reticleNum: Int)(mouseEvent: MouseEvent): Unit = {
    mouseEvent.preventDefault()
    dragState = None
  }

  def reticleMouseDragged(reticleNum: Int)(mouseEvent: MouseEvent): Unit = {
    mouseEvent.preventDefault()
    // mouseEvent.stopPropagation()
    reticleDragged(reticleNum)(positionForMouseEvent(mouseEvent))
  }

  def reticleTouchStarted(reticleNum: Int)(touchEvent: TouchEvent): Unit = {
    touchEvent.preventDefault()
    dragState = Some(DragState(reticleNum, positionForTouchEvent(touchEvent)))
  }

  def reticleTouchEnded(reticleNum: Int)(touchEvent: TouchEvent): Unit = {
    touchEvent.preventDefault()
    dragState = None
  }

  def reticleTouchMoved(reticleNum: Int)(touchEvent: TouchEvent): Unit = {
    touchEvent.preventDefault()
    reticleDragged(reticleNum)(positionForTouchEvent(touchEvent))
  }

  def reticleDragStarted(reticleNum: Int)(currentPoint: SVGPoint): Unit = {
    dragState = Some(DragState(reticleNum, currentPoint))
  }

  def reticleDragEnded(reticleNum: Int)(currentPoint: SVGPoint): Unit = {
    dragState = None
  }

  def reticleDragged(reticleNum: Int)(currentPoint: SVGPoint): Unit = {
    dragState.foreach { previousState =>
      val dy = (currentPoint.y - previousState.lastPoint.y).toInt.min(3).max(-3)

      val currentReticleFrame = reticleFrameNum(reticleNum)
      val newReticleFrame     = (currentReticleFrame + dy.toInt).max(0).min(ReticleImages.size - 1)

      setReticleFrameNum(reticleNum, newReticleFrame)

      dragState = Some(DragState(previousState.reticleNum, currentPoint))

      updatePlot()
    }
  }

  def resetReticles(): Unit = {
    resetReticle(0)
    resetReticle(1)
  }

  def resetReticle(reticleNum: Int): Unit = {
    setReticleFrameNum(reticleNum, 0)
  }

  def setReticleFrameNum(reticleNum: Int, frame: Int): Unit = {
    jQuery(s"#${reticleImageId(reticleNum)}")
      .attr("data-reticle-frame-number", frame.toString)

    jQuery(s"#${reticleImageId(reticleNum)}")
      .attr(
        "xlink:href",
        ReticleImages(frame)
      )

    updatePlot(reticleNum)
  }

  def reticleFrameNum(reticleNum: Int): Int =
    jQuery(s"#${reticleImageId(reticleNum)}")
      .attr("data-reticle-frame-number")
      .toOption
      .getOrElse("0")
      .toInt

  def reticleImageId(reticleNum: Int): String =
    s"club${reticleNum}-reticle-image"

  def positionForMouseEvent(event: MouseEvent): SVGPoint = {
    svgPointTx.x = event.clientX
    svgPointTx.y = event.clientY
    svgPointTx.matrixTransform(svgPlot.getScreenCTM().inverse())
  }

  def positionForTouchEvent(event: TouchEvent): SVGPoint = {
    svgPointTx.x = event.touches(0).clientX
    svgPointTx.y = event.touches(0).clientY
    svgPointTx.matrixTransform(svgPlot.getScreenCTM().inverse())
  }

  //////////// Meat and potatoes /////////////////////////

  def currentClub(clubNum: Int): Task[Option[Club]] = {
    Club.All.map { allClubsList =>
      val clubId = jQuery(s"#club${clubNum}-select").`val`.asInstanceOf[String]
      allClubsList.find(_.id == clubId)
    }
  }

  def currentClubLevel(clubNum: Int): Task[Option[Int]] =
    Task(Try(jQuery(s"#club${clubNum}-level-select").`val`.asInstanceOf[String].toInt).toOption)

  def currentBallPower(clubNum: Int): Task[Option[wind.WindMode]] =
    Task(
      Try(jQuery(s"#club${clubNum}-ball-power-select").`val`.asInstanceOf[String].toInt).toOption
        .map {
          case 0 => wind.Power0
          case 1 => wind.Power1
          case 2 => wind.Power2
          case 3 => wind.Power3
          case 4 => wind.Power4
          case 5 => wind.Power5
        }
    )

  def currentOpPercentage(clubNum: Int): Double =
    reticleFrameNum(clubNum) / (ReticleImages.size - 1).toDouble

  def updatePlot(): Unit = {
    updatePlot(0)
    updatePlot(1)
  }

  def updatePlot(clubNum: Int): Unit = {

    (for {
      clubOpt          <- currentClub(clubNum)
      clubLevelOpt     <- currentClubLevel(clubNum)
      clubBallPowerOpt <- currentBallPower(clubNum)
    } yield {

      for {
        club          <- clubOpt
        clubLevel     <- clubLevelOpt
        clubBallPower <- clubBallPowerOpt
      } yield {

        val opPercentage   = currentOpPercentage(clubNum)
        val clubMaxOpRings = overpower.rings(club, clubLevel, clubBallPower.powerCoefficient)
        val clubMaxOpYards = overpower.yards(club, clubLevel, clubBallPower.powerCoefficient)

        val shotOpRings  = clubMaxOpRings * opPercentage
        val shotOpYards  = clubMaxOpYards * opPercentage
        val shotDistance = (club.power(clubLevel - 1) * clubBallPower.powerCoefficient) + shotOpYards

        jQuery(s"#club${clubNum}-distance").text(f"$shotDistance%.1f yards")

        val axisLabelY = s"${3 + (opPercentage * 90)}%"
        jQuery(s"#club${clubNum}-extra-yards")
          .attr("y", axisLabelY)
          .text(f"+${shotOpYards}%.1f")
        jQuery(s"#club${clubNum}-extra-rings")
          .attr("y", axisLabelY)
          .text(f"+${shotOpRings}%.1f")

      }

    }).runAsync

    ()
  }

}
