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

object core {

  case class HoleData(id: String, aliases: List[String])
  case class HoleNote(
      id: Option[String],
      userId: String,
      holeId: String,
      category: String,
      content: String
  )

  case class ClubRatings(clubId: String, levelRatings: List[Double])
  case class HoleClubRatings(
      holeId: String,
      ratings: Map[String, List[Double]]
  ) {
    def update(club: Club, level: Int, rating: Double): HoleClubRatings = {
      val newRatings =
        ratings
          .get(club.id)
          .map { oldRatings: List[Double] =>
            ratings.updated(club.id, oldRatings.updated(level - 1, rating))
          }
          .getOrElse {
            ratings.updated(
              club.id,
              List.tabulate(club.maxLevel)(ix => if (ix + 1 == level) rating else elo.InitialRating)
            )
          }

      copy(ratings = newRatings)
    }
  }

  object HoleClubRatings {
    def empty(holeId: String) = HoleClubRatings(holeId, Map.empty[String, List[Double]])
  }
}
