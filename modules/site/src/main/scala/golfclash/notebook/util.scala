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

import scala.scalajs.js

import monix.eval.Task

object util {

  def isIE(): Boolean = {
    val ua = js.Dynamic.global.window.navigator.userAgent.asInstanceOf[String]
    List("MSIE", "Trident/", "Edge/").exists(ua.indexOf(_) >= 0)
  }

  def isFacebookBrowser(): Boolean = {
    val ua = js.Dynamic.global.window.navigator.userAgent.asInstanceOf[String]
    List("FBAN", "FBAV").exists(ua.indexOf(_) >= 0)
  }

  def printCurrentHoleRatings(): Task[Unit] = {
    for {
      holes   <- Hole.Unskinned
      ratings <- Hole.Ratings
    } yield {
      ratings.sortBy(-_.rating).zipWithIndex.foreach {
        case (HoleRating(holeId, rating), ix) =>
          holes.find(_.id == holeId).map { hole =>
            println(s"[$ix] [$rating] - ${hole.course} - Hole ${hole.number} - Par ${hole.par}")
          }
      }
    }
  }

  def printRatingsYamlFromStore(): Task[Unit] = {
    store.holeranker
      .allRatings()
      .map { ratings =>
        println(
          ratings
            .map { holeRating =>
              s"""|- holeId: ${holeRating.holeId}
                  |  rating: ${holeRating.rating}""".stripMargin
            }
            .mkString("\n")
        )
      }

  }

  def printRatingsJsonFromStore(): Task[Unit] = {
    store.holeranker
      .allRatings()
      .map { ratings =>
        println(
          ratings
            .map { holeRating =>
              s"""{ "holeId": "${holeRating.holeId}", "rating": ${holeRating.rating} }"""
            }
            .mkString("[", ",", "]")
        )
      }
  }

}
