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

package firebase

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@JSGlobal("firebase")
@js.native
object Firebase extends js.Object {

  def SDK_VERSION: String = js.native

  def app(name: js.UndefOr[String] = js.undefined): firebase.app.App             = js.native
  def auth(app: js.UndefOr[firebase.app.App] = js.undefined): firebase.auth.Auth = js.native
  def firestore(): firebase.firestore.Firestore                                  = js.native

  def initializeApp(options: FirebaseConfig,
                    name: js.UndefOr[String] = js.undefined): firebase.app.App = js.native

}

@js.native
trait FirebaseConfig extends js.Object {
  def apiKey: String                        = js.native
  def authDomain: js.UndefOr[String]        = js.native
  def databaseURL: js.UndefOr[String]       = js.native
  def projectId: js.UndefOr[String]         = js.native
  def storageBucket: js.UndefOr[String]     = js.native
  def messagingSenderId: js.UndefOr[String] = js.native
}

object FirebaseConfig {
  def apply(
    apiKey: String,
    authDomain: js.UndefOr[String] = js.undefined,
    databaseURL: js.UndefOr[String] = js.undefined,
    projectId: js.UndefOr[String] = js.undefined,
    storageBucket: js.UndefOr[String] = js.undefined,
    messagingSenderId: js.UndefOr[String] = js.undefined
  ): FirebaseConfig =
    js.Dynamic
      .literal(
        "apiKey"            -> apiKey,
        "authDomain"        -> authDomain,
        "databaseURL"       -> databaseURL,
        "projectId"         -> projectId,
        "storageBucket"     -> storageBucket,
        "messagingSenderId" -> messagingSenderId
      )
      .asInstanceOf[FirebaseConfig]
}

@js.native
trait FirebaseError extends js.Object {
  def code: String              = js.native
  def message: String           = js.native
  def name: String              = js.native
  def stack: js.UndefOr[String] = js.native
}

@js.native
trait Thenable[T, E] extends js.Object {
  def `catch`[U](onReject: js.Function1[E, U]): js.Dynamic = js.native
  def `then`[U](onResolve: js.Function1[T, U],
                onReject: js.Function1[E, U]): firebase.Thenable[U, E] =
    js.native
}

@JSGlobal("firebase.Promise")
@js.native
class Promise[T, E] protected () extends firebase.Thenable[T, E] {
  def this(resolver: js.Function2[js.Function1[T, Unit], js.Function1[E, Unit], js.Any]) =
    this()
}

@js.native
@JSGlobal("firebase.Promise")
object Promise extends js.Object {
  def all(values: js.Array[Promise[js.Any, js.Any]]): Promise[js.Array[js.Any], js.Any] = js.native
}
