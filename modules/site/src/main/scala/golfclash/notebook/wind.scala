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

/**
  * The meat of the wind calculation. If you're here to understand how this calculation is done
  * check out the [[windPerRing]] function. The last parameter, 'power' is typically the output of
  * either {min|mid|max}Power functions.
  */
object wind {

  /** 'Power' modes match the power attribute of a given ball. */
  sealed abstract class WindMode(val name: String, val powerCoefficient: Double)
      extends Product
      with Serializable

  case object Power0 extends WindMode("Power 0", 1.00)
  case object Power1 extends WindMode("Power 1", 1.03)
  case object Power2 extends WindMode("Power 2", 1.05)
  case object Power3 extends WindMode("Power 3", 1.07)
  case object Power4 extends WindMode("Power 4", 1.10)
  case object Power5 extends WindMode("Power 5", 1.13)

  object WindMode {
    def All = List(Power0, Power1, Power2, Power3, Power4, Power5)
  }

  def windPerRing(club: Club, level: Int, power: Double): Double = {
    (1d + ((100d - club.accuracy(level - 1)) * 0.02)) * windCategoryMultiplier(club.clubCategory) / power * ruleBasedCorrection(
      club,
      level
    )
  }

  // Loosely related to ball trajectory, lie properties...a.k.a SWAG
  def windCategoryMultiplier(clubCategory: Option[Club.Category]): Double =
    clubCategory match {
      case Some(Club.Category.RoughIrons) => 1.8
      case Some(Club.Category.SandWedges) => 1.6
      case _                              => 1.0
    }

  def maxPower(club: Club, level: Int, mode: WindMode): Double = {
    club.clubCategory
      .map(category => (club.power(level - 1) / category.maxDistance.toDouble))
      .getOrElse(1d) * mode.powerCoefficient
  }

  def midPower(club: Club, level: Int, mode: WindMode): Double = {
    def averagePower(club: Club, level: Int, mode: WindMode): Double =
      (minPower(club, level, mode) + maxPower(club, level, mode)) / 2

    club.clubCategory match {
      case Some(Club.Category.Drivers)    => averagePower(club, level, mode)
      case Some(Club.Category.Woods)      => averagePower(club, level, mode)
      case Some(Club.Category.LongIrons)  => averagePower(club, level, mode)
      case Some(Club.Category.ShortIrons) => averagePower(club, level, mode)
      case Some(Club.Category.Wedges)     => maxPower(club, level, mode) / 2
      case Some(Club.Category.RoughIrons) => maxPower(club, level, mode) / 2
      case Some(Club.Category.SandWedges) => maxPower(club, level, mode) / 2
      case None                           => averagePower(club, level, mode)
    }
  }

  def minPower(club: Club, level: Int, mode: WindMode): Double = {
    club.clubCategory match {
      case Some(Club.Category.Drivers)    => 0.75 * mode.powerCoefficient
      case Some(Club.Category.Woods)      => 0.75 * mode.powerCoefficient
      case Some(Club.Category.LongIrons)  => 0.66 * mode.powerCoefficient
      case Some(Club.Category.ShortIrons) => 0.50 * mode.powerCoefficient
      case Some(Club.Category.Wedges)     => maxPower(club, level, mode) / 4
      case Some(Club.Category.RoughIrons) => maxPower(club, level, mode) / 4
      case Some(Club.Category.SandWedges) => maxPower(club, level, mode) / 4
      case None                           => 0.75 * mode.powerCoefficient
    }
  }

  def ruleBasedCorrection(club: Club, level: Int): Double = {
    if (club.name.toLowerCase().contains("b52") && level >= 5) {
      0.9
    } else {
      1.0
    }
  }
}
