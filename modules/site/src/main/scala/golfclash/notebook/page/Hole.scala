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

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._

import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.XMLHttpRequest

import cats.implicits._
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

case class Hole(
  id: String,
  course: String,
  number: Int,
  par: Int,
)

case class HoleRating(holeId: String, rating: Double)

object Hole {

  val All: Task[List[Hole]]       = loadFromNames(allCourseNames()).memoizeOnSuccess
  val Unskinned: Task[List[Hole]] = loadFromNames(unskinnedCourseNames()).memoizeOnSuccess

  val Ratings: Task[List[HoleRating]] = {
    load[List[HoleRating]]("/data/holeratings/currentratings.json", List.empty[HoleRating])
  }

  private def loadFromNames(namesTask: Task[List[String]]) = {
    for {
      names <- namesTask
      holes <- names.traverse(loadHoles)
    } yield {
      holes.flatten
    }
  }

  def allCourseNames(): Task[List[String]] =
    load[List[String]]("/data/courses/all.json", List.empty[String])

  def unskinnedCourseNames(): Task[List[String]] =
    load[List[String]]("/data/courses/unskinned.json", List.empty[String])

  private def loadHoles(course: String): Task[List[Hole]] = {
    val sanitizedCourseName = course.replaceAll(" ", "").replaceAll("'", "")
    load[List[Hole]](s"/data/courses/${sanitizedCourseName}.json", List.empty[Hole])
  }

  private def load[T: Decoder](url: String, onErr: => T): Task[T] = {
    Task
      .fromFuture(
        Ajax.get(url).map { s: XMLHttpRequest =>
          val resOrErr =
            for {
              json <- parse(s.responseText)
              res  <- json.as[T]
            } yield res

          resOrErr.getOrElse(onErr)
        }
      )
      .memoizeOnSuccess
  }

}
