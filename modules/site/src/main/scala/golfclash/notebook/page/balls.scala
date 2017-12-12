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

  case class RankingWeights(windResistance: Int, sideSpin: Int, power: Int)

  val init = () => {

    showRankings(rank(ballList, generateRankingWeights()))

    jQuery("#perform-ranking-btn").click { () =>
      showRankings(rank(ballList, generateRankingWeights()))
    }
  }

  def rank(balls: List[Ball], weights: RankingWeights): List[Ball] = {
    balls.sortBy { ball =>
      (ball.windResistance * weights.windResistance) +
        (ball.sideSpin * weights.sideSpin) +
        (ball.power * weights.power)
    }.reverse
  }

  def generateRankingWeights(): RankingWeights = {
    val windResistanceWeight = getSliderValue("wind-resistance-slider") - 2
    val sideSpinWeight       = getSliderValue("side-spin-slider") - 2
    val powerWeight          = getSliderValue("power-slider") - 2

    RankingWeights(windResistanceWeight, sideSpinWeight, powerWeight)
  }

  def getSliderValue(sliderId: String): Int = {
    val slider = jQuery(s"#${sliderId}").asInstanceOf[js.Dynamic].slider()
    slider.slider("getValue").asInstanceOf[Int]
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
