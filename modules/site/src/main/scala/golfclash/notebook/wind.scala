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

  /**
    * 'Power' modes match the power attribute of a given ball. 'Simple' mode assumes all clubs have
    * the same maximum distance (i.e. Driver = 240, Wood = 180, etc.), which is not a valid
    * assumption but exists for legacy reasons.
    */
  sealed abstract class WindMode(
    val name: String,
    val maxPowerAdjustment: Double => Double,
    val minPowerAdjustment: Double => Double
  ) extends Product
      with Serializable

  case object Power0 extends WindMode("Power 0", _ * 1.00, _ * 1.00)
  case object Power1 extends WindMode("Power 1", _ * 1.03, _ * 1.03)
  case object Power2 extends WindMode("Power 2", _ * 1.05, _ * 1.05)
  case object Power3 extends WindMode("Power 3", _ * 1.07, _ * 1.07)
  case object Power4 extends WindMode("Power 4", _ * 1.10, _ * 1.10)
  case object Power5 extends WindMode("Power 5", _ * 1.13, _ * 1.13)
  case object Simple extends WindMode("Simple", _ => 1.00, _ * 1.00)

  object WindMode {
    def All = List(Power0, Power1, Power2, Power3, Power4, Power5, Simple)
  }

  def windPerRing(club: Club, level: Int, power: Double): Double = {
    (1d + ((100d - club.accuracy(level - 1)) * 0.02)) * windCategoryMultiplier(club.clubCategory) / power
  }

  // Loosely related to ball trajectory, lie properties...a.k.a SWAG
  def windCategoryMultiplier(clubCategory: Option[Club.Category]): Double =
    clubCategory match {
      case Some(Club.Category.RoughIrons) => 1.8
      case Some(Club.Category.SandWedges) => 1.6
      case _                              => 1
    }

  def maxPower(club: Club, level: Int, mode: WindMode): Double = {
    mode match {
      case Simple => 1.0
      case mode => {
        mode.maxPowerAdjustment(
          club.clubCategory
            .map(
              category => (club.power(level - 1) / category.maxDistance.toDouble)
            )
            .getOrElse(1d)
        )
      }
    }
  }

  def midPower(club: Club, level: Int, mode: WindMode): Double = {
    mode match {
      case Simple => {
        club.clubCategory match {
          case Some(Club.Category.Drivers)    => 0.875
          case Some(Club.Category.Woods)      => 0.875
          case Some(Club.Category.LongIrons)  => 0.833
          case Some(Club.Category.ShortIrons) => 0.750
          case Some(Club.Category.Wedges)     => 0.500
          case Some(Club.Category.RoughIrons) => 0.500
          case Some(Club.Category.SandWedges) => 0.500
          case None                           => 0.875
        }
      }
      case mode => {

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
    }
  }

  def minPower(club: Club, level: Int, mode: WindMode): Double = {
    mode match {
      case Simple => {
        club.clubCategory match {
          case Some(Club.Category.Drivers)    => 0.75
          case Some(Club.Category.Woods)      => 0.75
          case Some(Club.Category.LongIrons)  => 0.66
          case Some(Club.Category.ShortIrons) => 0.50
          case Some(Club.Category.Wedges)     => 0.25
          case Some(Club.Category.RoughIrons) => 0.25
          case Some(Club.Category.SandWedges) => 0.25
          case None                           => 0.75
        }
      }
      case mode => {
        club.clubCategory match {
          case Some(Club.Category.Drivers)    => mode.minPowerAdjustment(0.75)
          case Some(Club.Category.Woods)      => mode.minPowerAdjustment(0.75)
          case Some(Club.Category.LongIrons)  => mode.minPowerAdjustment(0.66)
          case Some(Club.Category.ShortIrons) => mode.minPowerAdjustment(0.50)
          case Some(Club.Category.Wedges) =>
            mode.minPowerAdjustment(maxPower(club, level, mode) / 4)
          case Some(Club.Category.RoughIrons) =>
            mode.minPowerAdjustment(maxPower(club, level, mode) / 4)
          case Some(Club.Category.SandWedges) =>
            mode.minPowerAdjustment(maxPower(club, level, mode) / 4)
          case None => mode.minPowerAdjustment(0.75)
        }
      }
    }
  }
}
