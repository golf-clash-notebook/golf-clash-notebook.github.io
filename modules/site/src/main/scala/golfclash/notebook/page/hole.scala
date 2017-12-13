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
import org.scalajs.jquery._

object hole {

  val init = () => {

    jQuery("img.course-hole-map")
      .one("load", () => { initGuides(); showLevel("Rookie") })
      .each({ (img: Element) =>
        try {
          if (img.asInstanceOf[js.Dynamic].complete.asInstanceOf[Boolean])
            jQuery(img).trigger("load")
        } catch {
          case t: Throwable =>
            println(s"Error triggering course hole map load: ${t.printStackTrace()}")
        }
      })

    initLevelButtonToggles()
  }

  def initLevelButtonToggles() = {
    jQuery(".guide-button-toggles").find("input").each { input: Element =>
      jQuery(input).change { evt: JQueryEventObject =>
        val level = jQuery(evt.target).attr("id").asInstanceOf[String]
        showLevel(level)
      }
    }
  }

  def showLevel(level: String) = {

    // Text descriptions
    jQuery(s"[class*=guide-text]:not([class*=$level])").addClass("hidden")
    jQuery(s".${level}-guide-text").removeClass("hidden")

    // Club recommendations
    jQuery(s"[class*=club-recommendations]:not([class*=$level])").addClass("hidden")
    jQuery(s".${level}-club-recommendations").removeClass("hidden")

    // Overlays
    jQuery(s"[class*=guide-overlay]:not([class*=$level])").children().hide()
    jQuery(s".${level}-guide-overlay").children().show()
  }

  def initGuides() = {
    jQuery(s"[class*=-guide-overlay]").map { element =>
      for {
        classAttr <- jQuery(element).attr("class").toOption
        classes = classAttr.split(" ")
        levelClass <- classes.find(_.contains("-guide-overlay"))
        level = levelClass.replaceAll("-guide-overlay", "")
      } yield {

        val lengths =
          (0 until numGuides(level)).map { guideNum =>
            guideLength(level, guideNum)
          }

        (0 until numGuides(level)).map { guideNum =>
          initGuidePaths(level, guideNum)
          initGuidePoints(level, guideNum)
        }

        val maxLength       = lengths.foldLeft(0)(_.max(_))
        val annotationDelay = pathLengthToDuration(maxLength)

        jQuery(".guide-image-annotation").each(
          (ix, element) => initGuideImageAnnotation(element, annotationDelay + (ix * 0.25))
        )

      }
    }
  }

  def numGuides(level: String): Int = {
    jQuery(s".$level-guide-overlay").length
  }

  def guideLength(level: String, guideNum: Int): Int = {
    jQuery(s".$level-guide-overlay:eq($guideNum) > .guide-path")
      .map(_.asInstanceOf[js.Dynamic].getTotalLength())
      .toArray
      .map(_.asInstanceOf[Int])
      .foldLeft(0)(_ + _)
  }

  def segmentLength(level: String, guideNum: Int, segmentNum: Int): Int = {
    jQuery(s".$level-guide-overlay:eq($guideNum) > .guide-path[data-guide-segment='$segmentNum']")
      .map(_.asInstanceOf[js.Dynamic].getTotalLength())
      .toArray
      .map(_.asInstanceOf[Int])
      .headOption
      .getOrElse(0)
  }

  def lengthBeforeSegment(level: String, guideNum: Int, segmentNum: Int): Int = {
    (0 to (segmentNum - 1)).map(segmentLength(level, guideNum, _)).foldLeft(0)(_ + _)
  }

  def pathLengthToDuration(pathLength: Int): Double = {
    pathLength / 250d
  }

  def initGuidePaths(level: String, guideNum: Int) = {
    jQuery(s".$level-guide-overlay:eq($guideNum) > .guide-path").each { element =>
      val segmentNum   = jQuery(element).data("guide-segment").asInstanceOf[Int]
      val lengthBefore = lengthBeforeSegment(level, guideNum, segmentNum)
      val length       = segmentLength(level, guideNum, segmentNum)

      val pathElement = jQuery(element).stop().show()

      if (!util.isIE()) {
        pathElement
          .css("stroke-dasharray", length)
          .css("stroke-dashoffset", length)
          .css("animation", s"guidePathDashOffset ${pathLengthToDuration(length)}s linear forwards")
          .css("animation-delay", s"${pathLengthToDuration(lengthBefore)}s")
      }
    }
  }

  def initGuidePoints(level: String, guideNum: Int) = {
    jQuery(s".$level-guide-overlay:eq($guideNum) > .guide-point").each { element =>
      val segmentNum   = jQuery(element).data("guide-segment").asInstanceOf[Int]
      val lengthBefore = lengthBeforeSegment(level, guideNum, segmentNum)

      val pointElement = jQuery(element).stop().show()

      if (!util.isIE()) {
        pointElement
          .css("animation", s"guidePointScale 0.5s ease-out forwards")
          .css("animation-delay", s"${pathLengthToDuration(lengthBefore)}s")
      }

    }
  }

  def initGuideImageAnnotation(annotation: Element, delay: Double) = {

    val annotationImageAnimationDuration  = 0.6
    val annotationLineAnimationDuration   = 0.5
    val annotationCircleAnimationDuration = 0.5

    val annotationImageAnimationDelay  = delay
    val annotationLineAnimationDelay   = annotationImageAnimationDelay + annotationImageAnimationDuration
    val annotationCircleAnimationDelay = annotationLineAnimationDelay + annotationLineAnimationDuration

    val jqAnnotation = jQuery(annotation)

    val imageElement      = jqAnnotation.find("image").stop().show()
    val connectorElements = jqAnnotation.find("path").stop().show()
    val pointElements     = jqAnnotation.find("circle").stop().show()

    if (!util.isIE()) {
      imageElement
        .css("animation", s"guidePointScale ${annotationImageAnimationDuration}s ease-out forwards")
        .css("animation-delay", s"${annotationImageAnimationDelay}s")

      connectorElements.each { path: Element =>
        val pathDynamic = path.asInstanceOf[js.Dynamic]

        val pathLength = pathDynamic.getTotalLength()

        jQuery(path)
          .css("stroke-dasharray", pathLength)
          .css("stroke-dashoffset", pathLength)
          .css(
            "animation",
            s"guidePathDashOffset ${annotationLineAnimationDuration}s linear forwards"
          )
          .css("animation-delay", s"${annotationLineAnimationDelay}s")
      }

      pointElements.each { path: Element =>
        jQuery(path)
          .css(
            "animation",
            s"guidePointScale ${annotationCircleAnimationDuration}s ease-out forwards"
          )
          .css("animation-delay", s"${annotationCircleAnimationDelay}s")
      }
    }

  }

}
