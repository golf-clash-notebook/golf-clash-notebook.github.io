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
import scala.scalajs.js.|
import scala.scalajs.js.annotation.JSGlobal

object firestore {

  @js.native
  @JSGlobal("firebase.firestore.CollectionReference")
  class CollectionReference extends Query {
    def id: String                = js.native
    def parent: DocumentReference = js.native

    def add(data: js.Any): Promise[DocumentReference, FirestoreError] = js.native
    def doc(documentPath: js.UndefOr[String]): DocumentReference      = js.native
  }

  @js.native
  trait DocumentChange extends js.Object {
    // TODO: Fill this out...
  }

  @js.native
  trait DocumentReference extends js.Object {
    def firestore: Firestore      = js.native
    def id: String                = js.native
    def parent: DocumentReference = js.native

    def collection(collectionPath: String): CollectionReference = js.native
    def delete(): Promise[Unit, FirestoreError]                 = js.native
    def get(): Promise[DocumentSnapshot, FirestoreError]        = js.native
    // onSnapshot(optionsOrObserverOrOnNext, observerOrOnNextOrOnError, onError)
    def set(
      data: js.Any,
      options: js.UndefOr[js.Object] = js.undefined
    ): Promise[Unit, FirestoreError] =
      js.native
    def update(var_args: js.Any): Promise[Unit, FirestoreError] = js.native
  }

  @js.native
  trait DocumentSnapshot extends js.Object {
    def data(): js.Any                                         = js.native
    def get(fieldPath: String | FieldPath): js.UndefOr[js.Any] = js.native

    def exists: Boolean
    def id: String
    def metadata: SnapshotMetadata
    def ref: DocumentReference
  }

  @js.native
  @JSGlobal("firebase.firestore.FieldPath")
  class FieldPath(var_args: js.Object*) extends js.Object

  @js.native
  @JSGlobal("firebase.firestore.FieldPath")
  object FieldPath extends js.Object {
    def documentId(): FieldPath = js.native
  }

  @js.native
  trait Firestore extends js.Object {
    def app: App                                                                   = js.native
    def batch(): WriteBatch                                                        = js.native
    def collection(collectionPath: String): CollectionReference                    = js.native
    def doc(documentPath: String): DocumentReference                               = js.native
    def enablePersistence(): Promise[Unit, FirestoreError]                         = js.native
    def runTransaction(updateFunction: Transaction): Promise[Unit, FirestoreError] = js.native
    def setLogLevel(logLevel: String): Unit                                        = js.native
    def settings(settings: Settings): Unit                                         = js.native
  }

  @js.native
  trait FirestoreError extends js.Object {
    // TODO: Can use a sealed trait here for 'code'
    def code: String    = js.native
    def message: String = js.native
  }

  @js.native
  trait Query extends js.Object {
    def firestore: Firestore = js.native

    def endAt(snapshotOrVarArgs: DocumentSnapshot | js.Any*): Query     = js.native
    def endBefore(snapshotOrVarArgs: DocumentSnapshot | js.Any*): Query = js.native
    def get(): Promise[QuerySnapshot, FirestoreError]                   = js.native
    def limit(limit: Int): Query                                        = js.native
    // onSnapshot(optionsOrObserverOrOnNext, observerOrOnNextOrOnError, onError, onCompletion)
    def orderBy(
      fieldPath: String | FieldPath,
      directionStr: js.UndefOr[String] = js.undefined
    ): Query                                                                      = js.native
    def startAfter(snapshotOrVarArgs: DocumentSnapshot | js.Any*): Query          = js.native
    def startAt(snapshotOrVarArgs: DocumentSnapshot | js.Any*): Query             = js.native
    def where(fieldPath: String | FieldPath, opStr: String, value: js.Any): Query = js.native
  }

  @js.native
  trait QuerySnapshot extends js.Object {
    def docChanges: js.Array[DocumentChange] = js.native
    def docs: js.Array[DocumentSnapshot]     = js.native
    def empty: Boolean                       = js.native
    def metadata: SnapshotMetadata           = js.native
    def query: Query                         = js.native
    def size: Int                            = js.native

    def forEach(
      callback: js.Function1[DocumentSnapshot, Unit],
      thisArg: js.UndefOr[js.Any] = js.undefined
    ): Unit =
      js.native
  }

  @js.native
  trait Settings extends js.Object

  @js.native
  trait SnapshotMetadata extends js.Object {
    def fromCache: Boolean
    def hasPendingWrites: Boolean
  }

  @js.native
  trait Transaction extends js.Object {
    def delete(documentRef: DocumentReference): Transaction                            = js.native
    def get(documentRef: DocumentReference): Promise[DocumentSnapshot, FirestoreError] = js.native
    def set(
      documentRef: DocumentReference,
      data: js.Any,
      options: js.UndefOr[js.Object] = js.undefined
    ): Transaction                                                               = js.native
    def update(documentRef: DocumentReference, var_args: js.Object): Transaction = js.native
  }

  @js.native
  trait WriteBatch extends js.Object {
    def commit(): Promise[Unit, FirestoreError]            = js.native
    def delete(documentRef: DocumentReference): WriteBatch = js.native
    def set(
      documentRef: DocumentReference,
      data: js.Any,
      options: js.UndefOr[js.Object] = js.undefined
    ): WriteBatch                                                               = js.native
    def update(documentRef: DocumentReference, var_args: js.Object): WriteBatch = js.native
  }

}
