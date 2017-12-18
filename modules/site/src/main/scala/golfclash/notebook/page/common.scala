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

import org.scalajs.dom
import org.scalajs.jquery.jQuery
import scala.scalajs.js

///////////////////////////////////
// TODO: Make this less terrible //
///////////////////////////////////
object common {

  def init() = {
    affixNav()
    activateNavItem()
    initMobileNavHiding()

    initHorizontalNav()
  }

  var lastDragX = -1

  def initHorizontalNav() = {

    val horizontalNav = jQuery("ul.horizontalNav")

    val scrollLeftButton  = jQuery(".nav-scroll-left")
    val scrollRightButton = jQuery(".nav-scroll-right")

    scrollLeftButton.asInstanceOf[js.Dynamic].touch()
    scrollRightButton.asInstanceOf[js.Dynamic].touch()

    scrollLeftButton.click(() => scrollNav(-15))
    scrollRightButton.click(() => scrollNav(15))
    scrollLeftButton.mousedown(_ => scrollNav(-15))
    scrollRightButton.mousedown(_ => scrollNav(15))
    scrollLeftButton.on("tap", (e: js.Any) => scrollNav(-15))
    scrollRightButton.on("tap", (e: js.Any) => scrollNav(15))

    horizontalNav.asInstanceOf[js.Dynamic].touch()

    horizontalNav.on(
      "dragEnd",
      (event: js.Any, info: js.Any) => lastDragX = -1
    )

    horizontalNav.on(
      "drag",
      (event: js.Any, info: js.Any) => {
        val dragX = info.asInstanceOf[js.Dynamic].x.asInstanceOf[Int]
        if (lastDragX > 0) scrollNav(lastDragX - dragX)
        lastDragX = dragX
      }
    )

    horizontalNav.bind(
      "mousewheel DOMMouseScroll",
      (e: js.Any) => {
        val delta = e.asInstanceOf[js.Dynamic].originalEvent.wheelDelta.asInstanceOf[Int]
        if (delta > 0) scrollNav(-15)
        else scrollNav(15)
        e.asInstanceOf[js.Dynamic].preventDefault()
      }
    )

    horizontalNav.before(scrollLeftButton)
    horizontalNav.after(scrollRightButton)

    checkNavScrollButtons()

    jQuery(dom.window).resize { eventData: js.Any =>
      checkNavScrollButtons()
    }
  }

  def checkNavScrollButtons() = {

    val horizontalNav     = jQuery("ul.horizontalNav")
    val horizontalNavElem = jQuery("ul.horizontalNav").get(0).asInstanceOf[js.Dynamic]
    val navScrollWidth    = horizontalNavElem.scrollWidth.asInstanceOf[Double]

    if (navScrollWidth > jQuery(dom.window).width()) {

      if (horizontalNav.scrollLeft() > 0) {
        jQuery(s".nav-scroll-left").css("visibility", "visible")
      } else {
        jQuery(s".nav-scroll-left").css("visibility", "hidden")
      }

      if (navScrollWidth - horizontalNav.scrollLeft() > horizontalNav.outerWidth()) {
        jQuery(s".nav-scroll-right").css("visibility", "visible")
      } else {
        jQuery(s".nav-scroll-right").css("visibility", "hidden")
      }

    } else {
      jQuery(s".nav-scroll").hide()
    }
  }

  def scrollNav(amount: Int) = {
    val horizontalNav    = jQuery("ul.horizontalNav")
    val currentScrollPos = horizontalNav.scrollLeft()
    horizontalNav.scrollLeft(currentScrollPos + amount)

    checkNavScrollButtons()
  }

  def affixNav() = {
    jQuery("#nav-container")
      .asInstanceOf[js.Dynamic]
      .affix(
        js.Dynamic.literal(
          "offset" -> js.Dynamic.literal(
            "top" -> { () =>
              80
            }
          )
        )
      )
  }

  def activateNavItem() = {

    val currentSection: String =
      js.Dynamic.global.window.location.pathname
        .asInstanceOf[String]
        .drop(1)
        .takeWhile(_ != '/') match {
        case "" => "home"
        case x  => x
      }

    jQuery("ul.horizontalNav li a").each { element =>
      if (jQuery(element).text.toLowerCase == currentSection) {

        jQuery(element).addClass("active")

        val navWidth = jQuery("ul.horizontalNav").width()
        val elementLeft =
          jQuery(element).offset().asInstanceOf[js.Dynamic].left.asInstanceOf[Double]

        jQuery("ul.horizontalNav").scrollLeft((elementLeft - navWidth / 2).toInt)

      }
    }

  }

  def initMobileNavHiding() = {

    jQuery(dom.window).scroll { event: js.Any =>
      val st = jQuery(dom.window).scrollTop()

      if (st > lastScrollTop) {
        jQuery("#nav-container").addClass("mobile-nav-hide")
      } else {
        jQuery("#nav-container").removeClass("mobile-nav-hide")
      }

      lastScrollTop = st
    }
  }

  var lastScrollTop = 0;

}
