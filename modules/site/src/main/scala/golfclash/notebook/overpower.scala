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

object overpower {

  val ReleventCategories = List(
    Club.Category.Drivers,
    Club.Category.Woods
    // Club.Category.RoughIrons,
    // Club.Category.SandWedges
  )

  def rings(club: Club, level: Int, ballPower: Double): Double = {
    yards(club, level, ballPower) / yardsPerRing(club, level, ballPower)
  }

  def yards(club: Club, level: Int, ballPower: Double): Double = {

    val clubDistance = club.power(level - 1) * ballPower

    club.clubCategory match {
      case Some(Club.Category.Drivers) =>
        14 + ((clubDistance - 180) * ((16.0 - 14.0) / (240.0 - 180.0)))
      case Some(Club.Category.Woods) =>
        11 + ((clubDistance - 135) * ((14.25 - 11.0) / (180.0 - 135.0)))
      case _ => 1
    }
  }

  def yardsPerRing(club: Club, level: Int, ballPower: Double): Double = {

    val clubDistance = club.power(level - 1) * ballPower
    val clubAccuracy = club.accuracy(level - 1)

    club.clubCategory match {
      case Some(Club.Category.Drivers) =>
        (4.25 - (clubAccuracy * 0.0275)) * ((clubDistance + yards(club, level, ballPower)) / 180)
      case Some(Club.Category.Woods) =>
        (2.75 - (clubAccuracy * 0.018)) * ((clubDistance + yards(club, level, ballPower)) / 135)
      case _ => 1
    }
  }

  def color(power: Double): (Int, Int, Int) = {

    val maxPowerRGB = (255, 255, 255)
    val halfOpRGB   = (255, 255, 133)
    val maxOpRGB    = (255, 0, 0)

    val (aR, aG, aB) = if (power < 0.5) maxPowerRGB else halfOpRGB
    val (bR, bG, bB) = if (power < 0.5) halfOpRGB else maxOpRGB
    val colorRatio   = if (power < 0.5) power else power - 0.5

    val r = (aR + (bR - aR) * colorRatio).round.toInt
    val g = (aG + (bG - aG) * colorRatio).round.toInt
    val b = (aB + (bB - aB) * colorRatio).round.toInt

    (r, g, b)
  }

}
