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

import core._
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

object crowdcaddy {

  type EloRating = Double
  case class ClubLevel(club: Club, level: Int)
  case class ClubRating(clubLevel: ClubLevel, rating: EloRating)
  case class Scenario(hole: Hole, optionA: ClubLevel, optionB: ClubLevel) {
    def iansWinner = {

      val options = List(optionA, optionB)
      val shit    = rnd.nextDouble > 0.2d

      options
        .find(_.club.name.toLowerCase.contains("apoc") && shit)
        .orElse(options.find(_.club.name.toLowerCase.contains("hammer") && shit))
        .orElse(options.find(_.club.name.toLowerCase.contains("mile") && shit))
        .orElse(options.find(_.club.name.toLowerCase.contains("quarterback") && shit))
        .orElse(options.find(_.club.name.toLowerCase.equals("the rock") && shit))
        .orElse(options.find(_.club.name.toLowerCase.contains("rocket") && shit))
        .orElse(options.find(_.club.name.toLowerCase.contains("topper") && shit))
        .getOrElse(optionA)

    }
  }
  case class ScenarioOutcome(
    scenario: Scenario,
    winner: ClubLevel,
    optionA: ClubRating,
    optionB: ClubRating
  )

  private val rnd = new Random()

  val init = () => {

    var x = 10

    (for {
      scenario <- randomScenario
      outcome  <- playScenario(scenario)
    } yield {
      ()
    }).restartUntil { _ =>
      x -= 1
      x <= 0
    }.runAsync

  }

  def randomScenario: Task[Scenario] = {
    for {
      hole <- randomHole
      clubCategory = randomClubCategory
      clubs <- randomClubLevels(clubCategory)
      (club0, club1) = clubs
    } yield {
      Scenario(hole, club0, club1)
    }
  }

  def playScenario(scenario: Scenario): Task[Unit] = {

    (for {
      initialHoleRatings <- currentHoleRatings(scenario.hole)
    } yield {

      val scenarioWinner = promptWinner(scenario)
      val scenarioLoser = scenarioWinner match {
        case scenario.optionA => scenario.optionB
        case _                => scenario.optionA
      }

      val winnerLevel = scenarioWinner.level
      val loserLevel  = scenarioLoser.level

      val combinations: List[(Int, Int)] =
        (for {
          wl <- winnerLevel to scenarioWinner.club.maxLevel
          ll <- loserLevel to 1 by -1
        } yield {
          (wl, ll)
        }).toList

      val updatedHoleRatings: HoleClubRatings =
        combinations.foldLeft(initialHoleRatings) {
          case (holeRatings, (winnerLevel, loserLevel)) =>
            val winner = scenarioWinner.copy(level = winnerLevel)
            val loser  = scenarioLoser.copy(level = loserLevel)

            val currentWinnerRating = currentClubRating(holeRatings, winner)
            val currentLoserRating  = currentClubRating(holeRatings, loser)

            val (winnerNewRating, loserNewRating) =
              elo.calculateRatings(elo.K)(currentWinnerRating.rating, currentLoserRating.rating)

            holeRatings
              .update(winner.club, winnerLevel, winnerNewRating)
              .update(loser.club, loserLevel, loserNewRating)
        }

      store.crowdcaddy.storeHoleRatings(scenario.hole.id, updatedHoleRatings)

    }).flatten
  }

  def promptWinner(scenario: Scenario): ClubLevel = {

    val promptMessage =
      s"Is ${scenario.optionA.club.name} [${scenario.optionA.level}] better than ${scenario.optionB.club.name} [${scenario.optionB.level}]"

    if (org.scalajs.dom.window.confirm(promptMessage)) {
      scenario.optionA
    } else {
      scenario.optionB
    }
  }

  def currentHoleRatings(hole: Hole): Task[HoleClubRatings] = {
    store.crowdcaddy.ratingsForHole(hole.id).map(_.getOrElse(HoleClubRatings.empty(hole.id)))
  }

  def currentClubRating(holeRatings: HoleClubRatings, clubLevel: ClubLevel): ClubRating = {
    holeRatings.ratings
      .get(clubLevel.club.id)
      .map { ratings =>
        ClubRating(clubLevel, ratings(clubLevel.level - 1))
      }
      .getOrElse {
        ClubRating(clubLevel, elo.InitialRating)
      }
  }

  def randomHole: Task[Hole] = {
    Hole.All.map { allHoles =>
      val ix = rnd.nextInt(allHoles.size)
      allHoles(ix)
    }
  }

  def randomClubCategory: Club.Category = {
    val ix = rnd.nextInt(Club.Category.All.size)
    Club.Category.All(ix)
  }

  def randomClubLevels(category: Club.Category): Task[(ClubLevel, ClubLevel)] = {
    for {
      club0 <- randomClubLevel(category)
      club1 <- randomClubLevel(category).restartUntil(_.club != club0.club)
    } yield (club0, club1)
  }

  def randomClubLevel(category: Club.Category): Task[ClubLevel] = {
    clubsOfCategory(category).map(_.filterNot(_.name.contains("Golden"))).map { categoryClubs =>
      val ix    = rnd.nextInt(categoryClubs.size)
      val club  = categoryClubs(ix)
      val level = rnd.nextInt(club.maxLevel) + 1
      ClubLevel(club, level)
    }
  }

  private[this] def clubsOfCategory(category: Club.Category): Task[List[Club]] = {
    category match {
      case Club.Category.Drivers    => Club.Drivers
      case Club.Category.Woods      => Club.Woods
      case Club.Category.LongIrons  => Club.LongIrons
      case Club.Category.ShortIrons => Club.ShortIrons
      case Club.Category.Wedges     => Club.Wedges
      case Club.Category.RoughIrons => Club.RoughIrons
      case Club.Category.SandWedges => Club.SandWedges
    }
  }

}
