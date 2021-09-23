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

package jspdf

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation.JSGlobal

object JSPdf {
  def apply(orientation: String, unit: String): JSPdf = {
    new JSPdf(
      js.Dynamic.literal(
        "orientation" -> orientation,
        "unit"        -> unit
      )
    )

  }
}

@js.native
@JSGlobal("jsPDF")
class JSPdf(options: js.UndefOr[js.Object] = js.undefined) extends js.Object {

  def addFont(postscript: String, name: String, font: String): Unit = js.native

  def addImage(base64: String, format: String, x: Double, y: Double, w: Double, h: Double): Unit =
    js.native

  def addPage(): Unit = js.native

  def circle(x: Double, y: Double, r: Double, style: js.UndefOr[String] = js.undefined): JSPdf =
    js.native

  def ellipse(
      x: Double,
      y: Double,
      rx: Double,
      ry: Double,
      style: js.UndefOr[String] = js.undefined
  ): JSPdf = js.native

  def line(
      x0: Double,
      y0: Double,
      x1: Double,
      y1: Double,
      style: js.UndefOr[String] = js.undefined
  ): JSPdf = js.native

  def rect(
      x: Double,
      y: Double,
      w: Double,
      h: Double,
      style: js.UndefOr[String] = js.undefined
  ): JSPdf = js.native

  def roundedRect(
      x: Double,
      y: Double,
      w: Double,
      h: Double,
      rx: Double,
      ry: Double,
      style: js.UndefOr[String] = js.undefined
  ): JSPdf = js.native

  def save(filename: String): JSPdf = js.native

  def setDrawColor(
      ch1: Double | String,
      ch2: js.UndefOr[Double | String] = js.undefined,
      ch3: js.UndefOr[Double | String] = js.undefined,
      ch4: js.UndefOr[Double | String] = js.undefined
  ): JSPdf = js.native

  def setFillColor(
      ch1: Double | String,
      ch2: js.UndefOr[Double | String] = js.undefined,
      ch3: js.UndefOr[Double | String] = js.undefined,
      ch4: js.UndefOr[Double | String] = js.undefined
  ): JSPdf = js.native

  def setFont(fontName: String, fontStyle: String): JSPdf = js.native

  def setFontSize(fontSize: Double): JSPdf = js.native

  def setFontStyle(fontStyle: String): JSPdf = js.native

  def setLineWidth(width: Double): JSPdf = js.native

  def setPage(pageNum: Int): Unit = js.native

  def setTextColor(
      ch1: Double | String,
      ch2: js.UndefOr[Double | String] = js.undefined,
      ch3: js.UndefOr[Double | String] = js.undefined
  ): JSPdf = js.native

  def text(
      text: String,
      x: Double,
      y: Double,
      flags: js.UndefOr[js.Any | String] = js.undefined
  ): JSPdf =
    js.native
}
