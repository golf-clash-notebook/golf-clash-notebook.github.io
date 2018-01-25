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

import io.circe.generic.auto._
import io.circe.parser._

import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.XMLHttpRequest

import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

case class Club(
  id: String,
  name: String,
  description: String,
  power: List[Int],
  accuracy: List[Int],
  topspin: List[Int],
  backspin: List[Int],
  curl: List[Int],
  ballguide: List[Double]
) {

  val maxLevel = power.size

}

object Club {

  sealed abstract class Category(val name: String) extends Product with Serializable
  object Category {
    case object Drivers    extends Category("Drivers")
    case object Woods      extends Category("Woods")
    case object LongIrons  extends Category("LongIrons")
    case object ShortIrons extends Category("ShortIrons")
    case object Wedges     extends Category("Wedges")
    case object RoughIrons extends Category("RoughIrons")
    case object SandWedges extends Category("SandWedges")

    val All =
      List(Drivers, Woods, LongIrons, ShortIrons, Wedges, RoughIrons, SandWedges)

    def fromString(category: String): Option[Category] = {
      All.find(_.name.toLowerCase == category.toLowerCase)
    }
  }

  val Drivers: Task[List[Club]]    = loadClubs(Category.Drivers)
  val Woods: Task[List[Club]]      = loadClubs(Category.Woods)
  val LongIrons: Task[List[Club]]  = loadClubs(Category.LongIrons)
  val ShortIrons: Task[List[Club]] = loadClubs(Category.ShortIrons)
  val Wedges: Task[List[Club]]     = loadClubs(Category.Wedges)
  val RoughIrons: Task[List[Club]] = loadClubs(Category.RoughIrons)
  val SandWedges: Task[List[Club]] = loadClubs(Category.SandWedges)

  val All: Task[List[Club]] = {
    for {
      drivers    <- Drivers
      woods      <- Woods
      longIrons  <- LongIrons
      shortIrons <- ShortIrons
      wedges     <- Wedges
      roughIrons <- RoughIrons
      sandWedges <- SandWedges
    } yield drivers ::: woods ::: longIrons ::: shortIrons ::: wedges ::: roughIrons ::: sandWedges
  }.memoizeOnSuccess

  def loadClubs(category: Category): Task[List[Club]] =
    Task
      .fromFuture(
        Ajax.get(s"/data/clubs/${category.name.toLowerCase}.json").map { s: XMLHttpRequest =>
          val clubsOrErr =
            for {
              json  <- parse(s.responseText)
              clubs <- json.as[List[Club]]
            } yield clubs

          clubsOrErr.getOrElse(List.empty[Club])
        }
      )
      .memoizeOnSuccess

}
