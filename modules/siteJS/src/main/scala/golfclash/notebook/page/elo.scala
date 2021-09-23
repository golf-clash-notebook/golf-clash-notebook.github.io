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

object elo {

  val K             = 10d
  val InitialRating = 1500d

  def calculateRatings(K: Double)(winnerRating: Double, loserRating: Double): (Double, Double) = {

    val winnerXRating = math.pow(10, winnerRating / 400d)
    val loserXRating  = math.pow(10, loserRating / 400d)

    val expectedWinnerScore = winnerXRating / (winnerXRating + loserXRating)
    val expectedLoserScore  = loserXRating / (winnerXRating + loserXRating)

    val newWinnerRating = winnerRating + K * (1 - expectedWinnerScore)
    val newLoserRating  = loserRating + K * (0 - expectedLoserScore)

    (newWinnerRating, newLoserRating)
  }

}
