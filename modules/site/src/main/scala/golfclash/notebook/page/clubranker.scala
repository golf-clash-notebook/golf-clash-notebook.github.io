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
import org.scalajs.jquery.jQuery

import scalatags.JsDom.all._

import cats.implicits._
import monix.execution.Scheduler.Implicits.global

object clubranker {

  case class RankedClub(clubLevel: ClubLevel, score: Double, tier: Int = 1)

  case class ClubLevel(
    name: String,
    category: String,
    level: Int,
    tour: Int,
    power: Int,
    accuracy: Int,
    topspin: Int,
    backspin: Int,
    curl: Int,
    ballguide: Double
  )

  case class ClubRankWeights(
    rawPower: Double,
    rawAccuracy: Double,
    rawTopspin: Double,
    rawBackspin: Double,
    rawCurl: Double,
    rawBallguide: Double
  ) {

    val total = (rawPower + rawAccuracy + rawTopspin + rawBackspin + rawCurl + rawBallguide).max(1)

    val power     = rawPower / total
    val accuracy  = rawAccuracy / total
    val topspin   = rawTopspin / total
    val backspin  = rawBackspin / total
    val curl      = rawCurl / total
    val ballguide = rawBallguide / total

  }

  val init = () => {

    jQuery("#club-ranker-category-select").change { () =>
      applyCategoryPreset()
      updateRankings()
    }

    List(powerSlider, accuracySlider, topSpinSlider, backSpinSlider, curlSlider, ballGuideSlider)
      .foreach { slider =>
        slider.on("change", { () =>
          updateRankings()
        })
      }

    applyCategoryPreset(CategoryWeightPresets(Club.Category.Drivers))
    updateRankings()
  }

  def updateRankings(): Unit = {
    for {
      clubCategory <- selectedClubCategory
      clubLevelsT  <- ClubLevels.get(clubCategory)
    } {
      clubLevelsT
        .map { clubLevels =>
          showRankings(rank(clubLevels, currentWeights()))
        }
        .runAsync
        .onComplete {
          case scala.util.Success(_) => ()
          case scala.util.Failure(ex) => {
            println("Failed to initialize club ranker: " + ex)
            ex.printStackTrace()
          }
        }
    }
  }

  def applyCategoryPreset(): Unit = {
    for {
      clubCategory <- selectedClubCategory
      preset       <- CategoryWeightPresets.get(clubCategory)
    } {
      applyCategoryPreset(preset)
    }
  }

  def applyCategoryPreset(preset: ClubRankWeights): Unit = {
    setSliderValue("power-slider", preset.rawPower)
    setSliderValue("accuracy-slider", preset.rawAccuracy)
    setSliderValue("top-spin-slider", preset.rawTopspin)
    setSliderValue("back-spin-slider", preset.rawBackspin)
    setSliderValue("curl-slider", preset.rawCurl)
    setSliderValue("ball-guide-slider", preset.rawBallguide)
  }

  def powerSlider     = getSlider("power-slider")
  def accuracySlider  = getSlider("accuracy-slider")
  def topSpinSlider   = getSlider("top-spin-slider")
  def backSpinSlider  = getSlider("back-spin-slider")
  def curlSlider      = getSlider("curl-slider")
  def ballGuideSlider = getSlider("ball-guide-slider")

  val ClubLevels = Map(
    Club.Category.Drivers    -> Club.Drivers.map(createClubLevels),
    Club.Category.Woods      -> Club.Woods.map(createClubLevels),
    Club.Category.LongIrons  -> Club.LongIrons.map(createClubLevels),
    Club.Category.ShortIrons -> Club.ShortIrons.map(createClubLevels),
    Club.Category.Wedges     -> Club.Wedges.map(createClubLevels),
    Club.Category.RoughIrons -> Club.RoughIrons.map(createClubLevels),
    Club.Category.SandWedges -> Club.SandWedges.map(createClubLevels)
  )

  val CategoryWeightPresets = Map(
    Club.Category.Drivers    -> ClubRankWeights(40, 25, 25, 0, 10, 0),
    Club.Category.Woods      -> ClubRankWeights(20, 30, 0, 20, 15, 15),
    Club.Category.LongIrons  -> ClubRankWeights(25, 30, 0, 25, 0, 20),
    Club.Category.ShortIrons -> ClubRankWeights(15, 30, 0, 20, 0, 35),
    Club.Category.Wedges     -> ClubRankWeights(10, 35, 20, 0, 0, 35),
    Club.Category.RoughIrons -> ClubRankWeights(40, 25, 5, 15, 5, 10),
    Club.Category.SandWedges -> ClubRankWeights(50, 25, 0, 5, 0, 20)
  )

  def rank(clubLevels: List[ClubLevel], weights: ClubRankWeights): List[RankedClub] = {

    val maxPower     = clubLevels.map(_.power.toDouble).max
    val maxAccuracy  = clubLevels.map(_.accuracy.toDouble).max
    val maxTopSpin   = clubLevels.map(_.topspin.toDouble).max
    val maxBackSpin  = clubLevels.map(_.backspin.toDouble).max
    val maxCurl      = clubLevels.map(_.curl.toDouble).max
    val maxBallGuide = clubLevels.map(_.ballguide.toDouble).max

    val minPower     = clubLevels.map(_.power.toDouble).min
    val minAccuracy  = clubLevels.map(_.accuracy.toDouble).min
    val minTopSpin   = clubLevels.map(_.topspin.toDouble).min
    val minBackSpin  = clubLevels.map(_.backspin.toDouble).min
    val minCurl      = clubLevels.map(_.curl.toDouble).min
    val minBallGuide = clubLevels.map(_.ballguide.toDouble).min

    assignTiers(
      clubLevels
        .map { clubLevel =>
          val powerRating     = (clubLevel.power - minPower) / (maxPower - minPower)
          val accuracyRating  = (clubLevel.accuracy - minAccuracy) / (maxAccuracy - minAccuracy)
          val topspinRating   = (clubLevel.topspin - minTopSpin) / (maxTopSpin - minTopSpin)
          val backspinRating  = (clubLevel.backspin - minBackSpin) / (maxBackSpin - minBackSpin)
          val curlRating      = (clubLevel.curl - minCurl) / (maxCurl - minCurl)
          val ballguideRating = (clubLevel.ballguide - minBallGuide) / (maxBallGuide - minBallGuide)

          val score =
            powerRating * weights.power +
              accuracyRating * weights.accuracy +
              topspinRating * weights.topspin +
              backspinRating * weights.backspin +
              curlRating * weights.curl +
              ballguideRating * weights.ballguide

          RankedClub(clubLevel, score)

        }
        .sortBy(-_.score)
    )
  }

  def assignTiers(rankedClubs: List[RankedClub]): List[RankedClub] = {
    rankedClubs
      .sortBy(-_.score)
      .scanLeft(none[RankedClub]) {
        case (lastClubOpt, currentClub) =>
          lastClubOpt match {
            case None => currentClub.copy(tier = 1).some
            case Some(lastClub) => {
              val scoreRatio  = 1 - ((lastClub.score - currentClub.score) / currentClub.score)
              val currentTier = if (scoreRatio < 0.97) lastClub.tier + 1 else lastClub.tier
              currentClub.copy(tier = currentTier.min(10)).some
            }
          }
      }
      .flatten
  }

  def createClubLevels(clubs: List[Club]) = {
    clubs.flatMap { club =>
      (0 until club.maxLevel).map { level =>
        ClubLevel(
          club.name,
          club.category,
          level + 1,
          club.tour,
          club.power(level),
          club.accuracy(level),
          club.topspin(level),
          club.backspin(level),
          club.curl(level),
          club.ballguide(level)
        )
      }
    }
  }

  def selectedClubCategory(): Option[Club.Category] = {
    Club.Category.fromString(jQuery("#club-ranker-category-select").`val`().asInstanceOf[String])
  }

  def currentWeights(): ClubRankWeights = {
    val powerWeight     = getSliderValue("power-slider")
    val accuracyWeight  = getSliderValue("accuracy-slider")
    val topSpinWeight   = getSliderValue("top-spin-slider")
    val backSpinWeight  = getSliderValue("back-spin-slider")
    val curlWeight      = getSliderValue("curl-slider")
    val ballGuideWeight = getSliderValue("ball-guide-slider")

    ClubRankWeights(
      powerWeight,
      accuracyWeight,
      topSpinWeight,
      backSpinWeight,
      curlWeight,
      ballGuideWeight
    )
  }

  def getSliderValue(sliderId: String): Double = {
    getSlider(sliderId).slider("getValue").asInstanceOf[Double]
  }

  def setSliderValue(sliderId: String, value: Double): Unit = {
    getSlider(sliderId).slider("setValue", value.toInt)
    ()
  }

  def getSlider(sliderId: String) = {
    jQuery(s"#${sliderId}").asInstanceOf[js.Dynamic].slider()
  }

  def showRankings(rankedClubs: List[RankedClub]): Unit = {
    val rankingTableBody = jQuery("#club-rankings-container table tbody")

    rankingTableBody.empty()

    rankedClubs.foreach { rankedClub =>
      rankingTableBody.append(rankedClubCard(rankedClub).render)
    }

  }

  def rankedClubCard(rankedClub: RankedClub) = {

    val sanitizedName =
      rankedClub.clubLevel.name.replaceAll("The", "").replaceAll(" ", "").replaceAll("'", "")
    val clubImagePath = s"""/img/golfclash/clubs/$sanitizedName-64x64.png"""

    tr(cls := s"club-ranking-row tier-${rankedClub.tier}-club")(
      td(span(cls := "text-tiny")(f"${rankedClub.score * 100}%.2f")),
      td(
        img(
          cls := "club-ranking-row-image img-responsive",
          src := clubImagePath,
          title := rankedClub.clubLevel.name
        )
      ),
      td(span(rankedClub.clubLevel.level)),
      td(span(rankedClub.clubLevel.power)),
      td(span(rankedClub.clubLevel.accuracy)),
      td(span(rankedClub.clubLevel.topspin)),
      td(span(rankedClub.clubLevel.backspin)),
      td(span(rankedClub.clubLevel.curl)),
      td(span(f"${rankedClub.clubLevel.ballguide}%.1f"))
    )
  }

}
