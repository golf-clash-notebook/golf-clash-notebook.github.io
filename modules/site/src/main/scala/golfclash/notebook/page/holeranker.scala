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

import scala.util.Random

import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import org.scalajs.jquery.jQuery
import scalatags.JsDom.all._

object holeranker {

  private val rnd              = new Random()
  private val EloK             = 5d
  private val EloInitialRating = 1500d

  case class Scenario(optionA: Hole, optionB: Hole)

  val init = () => {

    jQuery("#skip-scenario-btn").click { () =>
      generateNewScenario.runAsync
    }

    generateNewScenario().runAsync

    // printCurrentHoleRatings()
    // printRatingsJsonFromStore()
  }

  def generateNewScenario(): Task[Unit] = {
    randomScenario.map { scenario =>
      jQuery("#scenario-content").replaceWith(renderScenario(scenario).render)
      ()
    }
  }

  def renderScenario(scenario: Scenario) = {
    div(id := "scenario-content", cls := "row hole-ranker-scenario")(
      div(cls := "col-md-2 hidden-xs")(),
      renderHoleOption(scenario, scenario.optionA),
      renderHoleOption(scenario, scenario.optionB)
    )
  }

  def renderHoleOption(scenario: Scenario, hole: Hole) = {

    val imgPath = s"/img/golfclash/courses/${hole.course.replaceAll(" ", "")}/${hole.number}.png"

    div(cls := "col-md-4 col-xs-6 scenario-option text-center", data("mh") := "hole-option")(
      div(cls := "text-large text-semi-muted")(s"${hole.course}"),
      div(cls := "text-small text-semi-muted")(s"Hole ${hole.number} - Par ${hole.par}"),
      img(src := imgPath, onclick := { () =>
        scenarioDecided(scenario, hole)
      })
    )
  }

  def scenarioDecided(scenario: Scenario, harderHole: Hole) = {

    jQuery("#scenario-content, #skip-scenario-btn").css("visibility", "hidden")
    jQuery("#busy-spinner").removeClass("hidden")

    val (winner, loser) =
      if (scenario.optionA == harderHole) (scenario.optionA, scenario.optionB)
      else (scenario.optionB, scenario.optionA)

    (for {
      currentWinnerRating <- store.holeranker
        .ratingForHole(winner.id)
        .map(_.getOrElse(EloInitialRating))
      currentLoserRating <- store.holeranker
        .ratingForHole(loser.id)
        .map(_.getOrElse(EloInitialRating))
      newRatings                        = elo.calculateRatings(EloK)(currentWinnerRating, currentLoserRating)
      (winnerNewRating, loserNewRating) = newRatings
      _ <- store.holeranker.storeHoleRating(winner.id, winnerNewRating)
      _ <- store.holeranker.storeHoleRating(loser.id, loserNewRating)
      _ <- generateNewScenario()
    } yield {
      jQuery("#busy-spinner").addClass("hidden")
      jQuery("#skip-scenario-btn").css("visibility", "visible")
    }).runAsync
  }

  def randomScenario: Task[Scenario] = {
    for {
      holeA <- randomHole
      holeB <- randomHole.restartUntil(_ != holeA)
    } yield {
      Scenario(holeA, holeB)
    }
  }

  def randomHole: Task[Hole] = {
    Hole.Unskinned.map { allHoles =>
      allHoles(rnd.nextInt(allHoles.size))
    }
  }

  def currentHoleRating(hole: Hole): Task[Double] = {
    store.holeranker.ratingForHole(hole.id).map(_.getOrElse(elo.InitialRating))
  }

}
