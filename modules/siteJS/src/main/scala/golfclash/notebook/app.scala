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

import org.scalajs.dom
import org.scalajs.jquery.jQuery
import scala.scalajs.js

import firebase._
import golfclash.notebook.page._

object GolfClashNotebookApp {

  def main(args: Array[String]): Unit = {

    println(s"Welcome to the Golf Clash Notebook! [${currentMode()}]")

    Firebase.initializeApp(configForMode(currentMode()))

    // For gathering current hole ratings...
    // Firebase.initializeApp(configForMode(Prod))
    // import monix.execution.Scheduler.Implicits.global
    // util.printRatingsYamlFromStore().runAsyncAndForget

    jQuery(dom.document).ready { () =>
      common.init()

      for {
        currentPage <- Page.forUrlPath(
          js.Dynamic.global.window.location.pathname.asInstanceOf[String]
        )
        initF <- currentPage.init
      } yield initF()
    }
    ()
  }

  def currentMode(): Mode = {
    if (js.Dynamic.global.window.location.host.asInstanceOf[String] == "golfclashnotebook.io") Prod
    else Dev
  }

  def configForMode(mode: Mode) = {
    mode match {
      case Dev => {
        FirebaseConfig(
          "AIzaSyCcrJPaeo90K_9VwVo2KpRozYRf0BB1Xao",
          "golf-clash-notebook-dev.firebaseapp.com",
          "https://golf-clash-notebook-dev.firebaseio.com",
          "golf-clash-notebook-dev",
          "golf-clash-notebook-dev.appspot.com",
          "895790990892"
        )
      }
      case Prod => {
        FirebaseConfig(
          "AIzaSyDxRKAcs0vk1D3KKzNDRpBzhgowkaSmmvg",
          "golf-clash-notebook.firebaseapp.com",
          "https://golf-clash-notebook.firebaseio.com",
          "golf-clash-notebook",
          "golf-clash-notebook.appspot.com",
          "101324820979"
        )
      }
    }
  }

  sealed trait Mode extends Product with Serializable
  case object Dev   extends Mode
  case object Prod  extends Mode

}
