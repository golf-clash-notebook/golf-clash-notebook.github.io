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
import scala.scalajs.js.timers._

import org.scalajs.jquery._
import org.scalajs.dom.Element

object balls {

  case class RankingWeights(windResistance: Double, sideSpin: Double, power: Double)

  val init = () => {

    List(sideSpinSlider, windResistanceSlider, powerSlider).foreach { slider =>
      slider.on("change", () => showRankings(rank(ballList, generateRankingWeights())))
    }

    showRankings(rank(ballList, generateRankingWeights()))
  }

  def rank(balls: List[Ball], weights: RankingWeights): List[Ball] = {
    balls.sortBy { ball =>
      (ball.windResistance * weights.windResistance) +
        (ball.sideSpin * weights.sideSpin) +
        (ball.power * weights.power)
    }.reverse
  }

  def generateRankingWeights(): RankingWeights = {
    // Slight modifications here so that balls with same stats are ranked identically
    // and ball with different stats (but result in same rank score) are always slighly different
    val powerWeight          = getSliderValue("power-slider") - 0.001
    val windResistanceWeight = getSliderValue("wind-resistance-slider") - 0.0001
    val sideSpinWeight       = getSliderValue("side-spin-slider") - 0.00001

    RankingWeights(windResistanceWeight, sideSpinWeight, powerWeight)
  }

  def getSliderValue(sliderId: String): Double = {
    getSlider(sliderId).slider("getValue").asInstanceOf[Double]
  }

  def windResistanceSlider = getSlider("wind-resistance-slider")
  def sideSpinSlider       = getSlider("side-spin-slider")
  def powerSlider          = getSlider("power-slider")

  def getSlider(sliderId: String) = {
    jQuery(s"#${sliderId}").asInstanceOf[js.Dynamic].slider()
  }

  lazy val ballList: List[Ball] = {
    jQuery("tr.ball-table-row")
      .map { e: Element =>
        val name           = jQuery(e).data("name").asInstanceOf[String]
        val windResistance = jQuery(e).data("wind-resistance").asInstanceOf[Int]
        val sideSpin       = jQuery(e).data("side-spin").asInstanceOf[Int]
        val power          = jQuery(e).data("power").asInstanceOf[Int]

        Ball(name, windResistance, sideSpin, power)
      }
      .toArray
      .map(_.asInstanceOf[Ball])
      .toList
      .sortBy(_.name)
  }

  def showRankings(rankedBalls: List[Ball]): Unit = {
    updateRankChart(rankedBalls)
    updateBallDetails(rankedBalls)

    // Since we're moving around the detail cards, call match height again
    setTimeout(300) {
      jQuery.fn.asInstanceOf[js.Dynamic].matchHeight._update()
      ()
    }

    ()
  }

  def updateRankChart(rankedBalls: List[Ball]): Unit = {
    updateBallElements(
      rankedBalls,
      ball => s"#${ball.path}-ranking-row",
      ix => s".ball-table-row:eq($ix)"
    )
  }

  def updateBallDetails(rankedBalls: List[Ball]): Unit = {
    updateBallElements(
      rankedBalls,
      ball => s"#${ball.path}-details-card",
      ix => s".ball-details-card:eq($ix)"
    )
  }

  def updateBallElements(rankedBalls: List[Ball],
                         elementId: Ball => String,
                         replacementSelector: Int => String): Unit = {
    val ballElements = rankedBalls.map { ball =>
      jQuery(elementId(ball)).clone()
    }

    ballElements.zipWithIndex.foreach {
      case (ballCard, ix) =>
        jQuery(replacementSelector(ix)).replaceWith(ballCard)
    }
  }

}
