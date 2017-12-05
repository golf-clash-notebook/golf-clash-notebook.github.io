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

object courses {

  val segmentDuration = 1.1

  val init = () => {
    jQuery("img.course-hole-map")
      .one("load", initGuides())
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

  def initGuides() = {

    val numSegments =
      jQuery(".guide-path")
        .map(e => jQuery(e).data("guide-segment"))
        .toArray
        .map(_.asInstanceOf[Int])
        .sorted
        .lastOption
        .map(_ + 1)
        .getOrElse(0)

    jQuery(".guide-path").each(element => initGuidePath(element))
    jQuery(".guide-point").each(element => initGuidePoint(element))
    jQuery(".guide-image-annotation").each(
      (ix, element) =>
        initGuideImageAnnotation(element, numSegments * segmentDuration + (ix * 0.25))
    )

    showLevel("Rookie")

  }

  def initGuidePath(path: Element) = {

    val jqPath      = jQuery(path)
    val pathDynamic = path.asInstanceOf[js.Dynamic]

    val pathLength  = pathDynamic.getTotalLength()
    val pathSegment = jqPath.data("guide-segment").asInstanceOf[Int]

    jqPath
      .stop()
      .show()
      .css("stroke-dasharray", pathLength)
      .css("stroke-dashoffset", pathLength)
      .css("animation", s"guidePathDashOffset ${segmentDuration}s linear forwards")
      .css("animation-delay", s"${pathSegment * segmentDuration}s")

  }

  def initGuidePoint(point: Element) = {

    val jqPoint = jQuery(point)

    val pointSegment = jqPoint.data("guide-segment").asInstanceOf[Int]

    jqPoint
      .stop()
      .show()
      .css("animation", s"guidePointScale 0.5s ease-out forwards")
      .css("animation-delay", s"${(pointSegment * segmentDuration).max(0)}s")

  }

  def initGuideImageAnnotation(annotation: Element, delay: Double) = {

    val annotationImageAnimationDuration  = 0.6
    val annotationLineAnimationDuration   = 0.5
    val annotationCircleAnimationDuration = 0.5

    val annotationImageAnimationDelay  = delay
    val annotationLineAnimationDelay   = annotationImageAnimationDelay + annotationImageAnimationDuration
    val annotationCircleAnimationDelay = annotationLineAnimationDelay + annotationLineAnimationDuration

    val jqAnnotation = jQuery(annotation)

    jqAnnotation
      .find("image")
      .stop()
      .show()
      .css("animation", s"guidePointScale ${annotationImageAnimationDuration}s ease-out forwards")
      .css("animation-delay", s"${annotationImageAnimationDelay}s")

    jqAnnotation
      .find("path")
      .stop()
      .each { path: Element =>
        val pathDynamic = path.asInstanceOf[js.Dynamic]

        val pathLength = pathDynamic.getTotalLength()

        jQuery(path)
          .show()
          .css("stroke-dasharray", pathLength)
          .css("stroke-dashoffset", pathLength)
          .css(
            "animation",
            s"guidePathDashOffset ${annotationLineAnimationDuration}s linear forwards"
          )
          .css("animation-delay", s"${annotationLineAnimationDelay}s")
      }

    jqAnnotation
      .find("circle")
      .stop()
      .each { path: Element =>
        jQuery(path)
          .show()
          .css(
            "animation",
            s"guidePointScale ${annotationCircleAnimationDuration}s ease-out forwards"
          )
          .css("animation-delay", s"${annotationCircleAnimationDelay}s")
      }

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

}
