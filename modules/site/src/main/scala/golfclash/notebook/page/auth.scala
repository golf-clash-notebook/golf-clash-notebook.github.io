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

package golfclash.notebook.page

import scala.scalajs.js
import org.scalajs.jquery.jQuery

import cats.implicits._
import firebase._
import firebase.auth._
import monix.execution.Scheduler.Implicits.global
import monix.reactive.subjects.Var

object auth {

  val CurrentUser = Var(none[User])

  def init() = {
    initUserLogin()
  }

  def initUserLogin() = {

    Firebase
      .app()
      .auth()
      .onAuthStateChanged { user =>
        CurrentUser := user.toOption.filter(_ != null)
        ()
      }

    // Drop the initial value so the first item is the value from Firebase...
    CurrentUser.drop(1).foreach { maybeUser =>
      maybeUser match {
        case Some(user) => {
          jQuery("#login-btn").addClass("hidden")

          (user.photoURL.toOption, user.displayName.toOption) match {
            case (Some(photoURL), _) => {
              jQuery("#username-photo-img").attr("src", photoURL).removeClass("hidden")
              jQuery("#username-menu-item").addClass("hidden")
            }
            case (None, Some(displayName)) => {
              jQuery("#username-photo-img").addClass("hidden")
              jQuery("#username-menu-item")
                .text(displayName)
                .removeClass("hidden")
            }
            case (_, _) => {
              jQuery("#username-photo-img").addClass("hidden")
              jQuery("#username-menu-item").text("Logged In").removeClass("hidden")
            }
          }

          jQuery("#logged-in-menu").removeClass("hidden")
        }
        case None => {
          jQuery("#username-menu-item").text("")
          jQuery("#logged-in-menu").addClass("hidden")
          jQuery("#login-btn").removeClass("hidden")
        }
      }
      ()
    }

    jQuery("#login-via-facebook").click(() => loginVia(new firebase.auth.FacebookAuthProvider()))
    jQuery("#login-via-google").click(() => loginVia(new firebase.auth.GoogleAuthProvider()))
    jQuery("#login-via-twitter").click(() => loginVia(new firebase.auth.TwitterAuthProvider()))

    jQuery("#logout-btn").click { () =>
      Firebase
        .app()
        .auth()
        .signOut()
        .`catch`(err => println(s"Error signing out: $err"))
    }

  }

  private def loginVia(provider: OAuthProvider) = {
    Firebase
      .app()
      .auth()
      .signInWithPopup(provider)
      .`then`(
        _ => {
          jQuery("#sign-in-error-alert").text("").addClass("hidden")
          jQuery("#sign-in-modal").asInstanceOf[js.Dynamic].modal("hide")
          ()
        },
        err => {
          jQuery("#sign-in-error-alert").text(err.message).removeClass("hidden")
          ()
        }
      )
  }

}
