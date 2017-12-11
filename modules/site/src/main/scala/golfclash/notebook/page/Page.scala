/*
 * MIT License
 *
 * Copyright (c) 2017 golf-clash-notebook
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

package golfclash.notebook.page

import scala.scalajs.js

sealed abstract class Page(val baseUrl: String, val init: Option[js.Function0[_]] = None)
    extends Product
    with Serializable

object Page {

  case object Balls       extends Page("/balls/")
  case object Clubs       extends Page("/clubs/", Some(clubs.init))
  case object Courses     extends Page("/courses/", Some(courses.init))
  case object Home        extends Page("/")
  case object Tournaments extends Page("/tournaments/", Some(tournaments.init))
  case object Tours       extends Page("/tours/")

  // TODO: Probably want to use regex and be a little more specific which page we're on
  // (e.g. tournament list page, tournament hole page, etc.)
  // Currently, Home will match anything that falls through
  private val all = List(Balls, Clubs, Courses, Tournaments, Tours, Home)

  def forUrlPath(path: String): Option[Page] = {
    all.find(page => path.startsWith(page.baseUrl))
  }
}
