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

object wind {

  def windPerRing(club: Club, level: Int): Double =
    windPerRing(club, level, 1.0)

  def windPerRing(club: Club, level: Int, power: Double): Double = {

    // Loosely related to ball trajectory, lie properties...a.k.a SWAG
    val windCategoryMultiplier = {
      club.clubCategory match {
        case Some(Club.Category.RoughIrons) => 1.8
        case Some(Club.Category.SandWedges) => 1.8
        case _                              => 1
      }
    }

    ((1 + ((100 - club.accuracy(level - 1)) * 0.02)) * windCategoryMultiplier) / power
  }

  def clubPowersFor(club: Club, level: Int): List[Double] = {

    // TODO: Assuming every club max power is 100% even though it's really not...
    // val maxDistancePower = club.clubCategory
    //   .map(category => (club.power(level - 1) / category.maxDistance.toDouble))
    //   .getOrElse(1d)

    val maxDistancePower = 1d

    val minDistancePower =
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

    List(
      maxDistancePower,
      minDistancePower + ((maxDistancePower - minDistancePower) / 2),
      minDistancePower
    )

  }

}
