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

object auth {

  @js.native
  trait ActionCodeSettings extends js.Object {
    def url: String                                    = js.native
    def iOS: js.UndefOr[String]                        = js.native
    def android: js.UndefOr[AndroidActionCodeSettings] = js.native
    def handleCodeInApp: js.UndefOr[Boolean]           = js.native
  }

  @js.native
  trait AndroidActionCodeSettings extends js.Object {
    def packageName: String                = js.native
    def installApp: js.UndefOr[Boolean]    = js.native
    def minimumVersion: js.UndefOr[String] = js.native
  }

  @js.native
  trait Auth extends js.Object {
    def app: App                         = js.native
    def currentUser: js.UndefOr[User]    = js.native
    def languageCode: js.UndefOr[String] = js.native

    // applyActionCode(code) returns firebase.Promise containing void
    // checkActionCode(code) returns firebase.Promise containing non-null firebase.auth.ActionCodeInfo
    def confirmPasswordReset(code: String, newPassword: String): Promise[Unit, Error] = js.native
    def createUserAndRetrieveDataWithEmailAndPassword(
        email: String,
        password: String
    ): Promise[UserCredential, Error] =
      js.native
    def createUserWithEmailAndPassword(email: String, password: String): Promise[User, Error] =
      js.native
    def fetchProvidersForEmail(email: String): Promise[js.Array[String], Error] = js.native
    def getRedirectResult(): Promise[UserCredential, Error]                     = js.native
    def onAuthStateChanged(
        nextOrObserver: js.Function1[js.UndefOr[User], Unit],
        error: js.UndefOr[js.Function1[Error, Unit]] = js.undefined,
        completed: js.UndefOr[js.Function0[Unit]] = js.undefined
    ): js.Function0[Unit] = js.native
    // onIdTokenChanged(nextOrObserver, error, completed) returns function()
    def sendPasswordResetEmail(
        email: String,
        actionCodeSettings: js.UndefOr[ActionCodeSettings]
    ): Promise[Unit, Error] =
      js.native
    def setPersistence(persistence: String): Promise[Unit, Error] = js.native
    def signInAndRetrieveDataWithCredential(
        credential: AuthCredential
    ): Promise[UserCredential, Error] =
      js.native
    def signInAndRetrieveDataWithCustomToken(token: String): Promise[UserCredential, Error] =
      js.native
    def signInAndRetrieveDataWithEmailAndPassword(
        email: String,
        password: String
    ): Promise[UserCredential, Error] =
      js.native
    // signInAnonymously() returns firebase.Promise containing non-null firebase.User
    // signInAnonymouslyAndRetrieveData() returns firebase.Promise containing non-null firebase.auth.UserCredential
    def signInWithCredential(credential: AuthCredential): Promise[User, Error] = js.native
    def signInWithCustomToken(token: String): Promise[User, Error]             = js.native
    def signInWithEmailAndPassword(email: String, password: String): Promise[User, Error] =
      js.native
    // signInWithPhoneNumber(phoneNumber, applicationVerifier) returns firebase.Promise containing non-null firebase.auth.ConfirmationResult
    def signInWithPopup(provider: OAuthProvider): Promise[UserCredential, Error] = js.native
    def signInWithRedirect(provider: OAuthProvider): Promise[Unit, Error]        = js.native
    def signOut(): Promise[Unit, Error]                                          = js.native
    def useDeviceLanguage(): Unit                                                = js.native
    def verifyPasswordResetCode(code: String): Promise[String, Error]            = js.native

  }

  @js.native
  trait AuthCredential extends js.Object {
    def providerId: String = js.native
  }

  @js.native
  trait AuthProvider extends js.Object {
    def providerId: String = js.native
  }

  @js.native
  trait OAuthCredential extends AuthCredential {
    def accessToken: js.UndefOr[String] = js.native
    def idToken: js.UndefOr[String]     = js.native
    def secret: js.UndefOr[String]      = js.native
  }

  @js.native
  @JSGlobal("firebase.auth.OAuthProvider")
  class OAuthProvider(providerId: String) extends AuthProvider {
    def addScope(scope: String): OAuthProvider = js.native
    def credential(
        idToken: js.UndefOr[String] = js.undefined,
        accessToken: js.UndefOr[String] = js.undefined
    ): OAuthCredential =
      js.native
    def setCustomParameters(customOAuthParameters: js.Object): OAuthProvider = js.native
  }

  @js.native
  @JSGlobal("firebase.auth.EmailAuthProvider")
  class EmailAuthProvider extends AuthProvider

  @js.native
  @JSGlobal("firebase.auth.EmailAuthProvider")
  object EmailAuthProvider extends js.Object {
    def PROVIDER_ID: String                                         = js.native
    def credential(email: String, password: String): AuthCredential = js.native
  }

  @js.native
  @JSGlobal("firebase.auth.FacebookAuthProvider")
  class FacebookAuthProvider extends OAuthProvider(FacebookAuthProvider.PROVIDER_ID)

  @js.native
  @JSGlobal("firebase.auth.FacebookAuthProvider")
  object FacebookAuthProvider extends js.Object {
    def PROVIDER_ID: String                        = js.native
    def credential(token: String): OAuthCredential = js.native
  }

  @js.native
  @JSGlobal("firebase.auth.GoogleAuthProvider")
  class GoogleAuthProvider extends OAuthProvider(GoogleAuthProvider.PROVIDER_ID)

  @js.native
  @JSGlobal("firebase.auth.GoogleAuthProvider")
  object GoogleAuthProvider extends js.Object {
    def PROVIDER_ID: String = js.native
    def credential(
        idToken: js.UndefOr[String] = js.undefined,
        accessToken: js.UndefOr[String] = js.undefined
    ): OAuthCredential =
      js.native
  }

  @js.native
  @JSGlobal("firebase.auth.TwitterAuthProvider")
  class TwitterAuthProvider extends OAuthProvider(TwitterAuthProvider.PROVIDER_ID)

  @js.native
  @JSGlobal("firebase.auth.TwitterAuthProvider")
  object TwitterAuthProvider extends js.Object {
    def PROVIDER_ID: String = js.native
    def credential(
        idToken: js.UndefOr[String] = js.undefined,
        accessToken: js.UndefOr[String] = js.undefined
    ): OAuthCredential =
      js.native
  }

  @js.native
  trait UserCredential extends js.Object {
    def user: User                                         = js.native
    def credential: js.UndefOr[AuthCredential]             = js.native
    def operationType: js.UndefOr[String]                  = js.native
    def additionalUserInfo: js.UndefOr[AdditionalUserInfo] = js.native
  }

  @js.native
  trait AdditionalUserInfo extends js.Object {
    def providerId: String             = js.native
    def profile: js.UndefOr[js.Object] = js.native
    def username: js.UndefOr[String]   = js.native
    def isNewUser: Boolean             = js.native
  }

  @js.native
  trait Error extends js.Object {
    def code: String    = js.native
    def message: String = js.native
  }

  @js.native
  trait UserInfo extends js.Object {
    def displayName: js.UndefOr[String] = js.native
    def email: js.UndefOr[String]       = js.native
    def photoURL: js.UndefOr[String]    = js.native
    def providerId: String              = js.native
    def uid: String                     = js.native
  }

  @js.native
  trait UserMetadata extends js.Object {
    def creationTime: js.UndefOr[String]   = js.native
    def lastSignInTime: js.UndefOr[String] = js.native
  }

  @js.native
  trait User extends UserInfo {

    def emailVerified: Boolean           = js.native
    def isAnonymous: Boolean             = js.native
    def metadata: UserMetadata           = js.native
    def phoneNumber: js.UndefOr[String]  = js.native
    def providerData: js.Array[UserInfo] = js.native
    def refreshToken: String             = js.native

    def delete(): Promise[Unit, Error] = js.native
    def getIdToken(forceRefresh: js.UndefOr[Boolean] = js.undefined): Promise[String, Error] =
      js.native
    def linkAndRetrieveDataWithCredential(
        credential: AuthCredential
    ): Promise[UserCredential, Error]                                        = js.native
    def linkWithCredential(credential: AuthCredential): Promise[User, Error] = js.native
    // linkWithPhoneNumber(phoneNumber, applicationVerifier) returns firebase.Promise containing non-null firebase.auth.ConfirmationResult
    def linkWithPopup(provider: OAuthProvider): Promise[UserCredential, Error] = js.native
    def linkWithRedirect(provider: OAuthProvider): Promise[Unit, Error]        = js.native
    def reauthenticateAndRetrieveDataWithCredential(
        credential: AuthCredential
    ): Promise[UserCredential, Error]                                                  = js.native
    def reauthenticateWithCredential(credential: AuthCredential): Promise[Unit, Error] = js.native
    // reauthenticateWithPhoneNumber(phoneNumber, applicationVerifier) returns firebase.Promise containing non-null firebase.auth.ConfirmationResult
    def reauthenticateWithPopup(provider: OAuthProvider): Promise[UserCredential, Error] = js.native
    def reauthenticateWithRedirect(provider: OAuthProvider): Promise[Unit, Error]        = js.native
    def reload(): Promise[Unit, Error]                                                   = js.native
    def sendEmailVerification(
        actionCodeSettings: js.UndefOr[ActionCodeSettings] = js.undefined
    ): Promise[Unit, Error] =
      js.native
    def toJSON(): js.Object                                       = js.native
    def unlink(providerId: String): Promise[User, Error]          = js.native
    def updateEmail(newEmail: String): Promise[Unit, Error]       = js.native
    def updatePassword(newPassword: String): Promise[Unit, Error] = js.native
    // updatePhoneNumber(phoneCredential) returns firebase.Promise containing void
    // updateProfile(profile) returns firebase.Promise containing void

  }

  object Persistance {
    val Local   = "LOCAL"
    val None    = "NONE"
    val Session = "SESSION"
  }

}
