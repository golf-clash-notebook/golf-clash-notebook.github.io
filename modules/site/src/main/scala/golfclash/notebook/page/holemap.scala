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
package page

import scala.scalajs.js
import org.scalajs.dom.Element
import org.scalajs.jquery._

import cats.implicits._
import golfclash.notebook.core._
import markedjs._
import monix.execution.Scheduler.Implicits.global

object holemap {

  var PageHoleData    = none[HoleData]
  var CurrentHoleNote = none[HoleNote]

  val init = () => {
    initHoleMap()
    initLevelButtonToggles()
    initHoleNotes()
  }

  def initHoleMap() = {
    jQuery("img.course-hole-map")
      .one("load", () => { initGuides(); showLevel("Rookie") })
      .each({ (img: Element) =>
        try {
          if (img.asInstanceOf[js.Dynamic].complete.asInstanceOf[Boolean])
            jQuery(img).trigger("load")
        } catch {
          case t: Throwable =>
            println(s"Error triggering course hole map load: ${t.printStackTrace()}")
        }
      })
  }

  def initLevelButtonToggles() = {
    jQuery(".guide-button-toggles").find("input").each { input: Element =>
      jQuery(input).change { evt: JQueryEventObject =>
        val level = jQuery(evt.target).attr("id").asInstanceOf[String]
        showLevel(level)
      }
    }
  }

  def showLevel(level: String) = {

    // Text descriptions
    jQuery(s"[class*=guide-text]:not([class*=$level])").addClass("hidden")
    jQuery(s".${level}-guide-text").removeClass("hidden")

    // Club recommendations
    jQuery(s"[class*=club-recommendations]:not([class*=$level])").addClass("hidden")
    jQuery(s".${level}-club-recommendations").removeClass("hidden")

    // Overlays
    jQuery(s"[class*=guide-overlay]:not([class*=$level])").children().hide()
    jQuery(s".${level}-guide-overlay").children().show()
  }

  def initGuides() = {
    if (jQuery(s"[class*=-guide-overlay]").length > 0) {
      jQuery(s"[class*=-guide-overlay]").map { element =>
        for {
          classAttr <- jQuery(element).attr("class").toOption
          classes = classAttr.split(" ")
          levelClass <- classes.find(_.contains("-guide-overlay"))
          level = levelClass.replaceAll("-guide-overlay", "")
        } yield {

          val lengths =
            (0 until numGuides(level)).map { guideNum =>
              guideLength(level, guideNum)
            }

          (0 until numGuides(level)).map { guideNum =>
            initGuidePaths(level, guideNum)
            initGuidePoints(level, guideNum)
          }

          val maxLength       = lengths.foldLeft(0)(_.max(_))
          val annotationDelay = pathLengthToDuration(maxLength)

          jQuery(".guide-image-annotation").each(
            (ix, element) => initGuideImageAnnotation(element, annotationDelay + (ix * 0.25))
          )

        }
      }
    } else {
      jQuery(".guide-image-annotation").each(
        (ix, element) => initGuideImageAnnotation(element, 0.25 + (ix * 0.25))
      )
    }
  }

  def numGuides(level: String): Int = {
    jQuery(s".$level-guide-overlay").length
  }

  def guideLength(level: String, guideNum: Int): Int = {
    jQuery(s".$level-guide-overlay:eq($guideNum) > .guide-path")
      .map(_.asInstanceOf[js.Dynamic].getTotalLength())
      .toArray
      .map(_.asInstanceOf[Double])
      .foldLeft(0)(_ + _.toInt)
  }

  def segmentLength(level: String, guideNum: Int, segmentNum: Int): List[Int] = {
    jQuery(s".$level-guide-overlay:eq($guideNum) > .guide-path[data-guide-segment='$segmentNum']")
      .map(_.asInstanceOf[js.Dynamic].getTotalLength())
      .toArray
      .map(_.asInstanceOf[Double].toInt)
      .toList
    // .foldLeft(0)((max, segLength) => max.max(segLength.toInt))
  }

  def lengthBeforeSegment(level: String, guideNum: Int, segmentNum: Int): Int = {
    (0 to (segmentNum - 1)).map(segmentLength(level, guideNum, _).min).foldLeft(0)(_ + _)
  }

  def pathLengthToDuration(pathLength: Int): Double = {
    pathLength / 250d
  }

  def initGuidePaths(level: String, guideNum: Int) = {
    jQuery(s".$level-guide-overlay:eq($guideNum) > .guide-path").each { element =>
      val segmentNum   = jQuery(element).data("guide-segment").asInstanceOf[Int]
      val lengthBefore = lengthBeforeSegment(level, guideNum, segmentNum)
      val length       = segmentLength(level, guideNum, segmentNum).max

      val pathElement = jQuery(element).stop().show()

      if (!util.isIE()) {
        pathElement
          .css("stroke-dasharray", length)
          .css("stroke-dashoffset", length)
          .css("animation", s"guidePathDashOffset ${pathLengthToDuration(length)}s linear forwards")
          .css("animation-delay", s"${pathLengthToDuration(lengthBefore)}s")
      }
    }
  }

  def initGuidePoints(level: String, guideNum: Int) = {
    jQuery(s".$level-guide-overlay:eq($guideNum) > .guide-point").each { element =>
      val segmentNum   = jQuery(element).data("guide-segment").asInstanceOf[Int]
      val lengthBefore = lengthBeforeSegment(level, guideNum, segmentNum)

      val pointElement = jQuery(element).stop().show()

      if (!util.isIE()) {
        pointElement
          .css("animation", s"guidePointScale 0.5s ease-out forwards")
          .css("animation-delay", s"${pathLengthToDuration(lengthBefore)}s")
      }

    }
  }

  def initGuideImageAnnotation(annotation: Element, delay: Double) = {

    val annotationImageAnimationDuration  = 0.6
    val annotationLineAnimationDuration   = 0.5
    val annotationCircleAnimationDuration = 0.5

    val annotationImageAnimationDelay  = delay
    val annotationLineAnimationDelay   = annotationImageAnimationDelay + annotationImageAnimationDuration
    val annotationCircleAnimationDelay = annotationLineAnimationDelay + annotationLineAnimationDuration

    val jqAnnotation = jQuery(annotation)

    val imageElement      = jqAnnotation.find("image").stop().show()
    val connectorElements = jqAnnotation.find("path").stop().show()
    val pointElements     = jqAnnotation.find("circle").stop().show()

    if (!util.isIE()) {
      imageElement
        .css("animation", s"guidePointScale ${annotationImageAnimationDuration}s ease-out forwards")
        .css("animation-delay", s"${annotationImageAnimationDelay}s")

      connectorElements.each { path: Element =>
        val pathDynamic = path.asInstanceOf[js.Dynamic]

        val pathLength = pathDynamic.getTotalLength()

        jQuery(path)
          .css("stroke-dasharray", pathLength)
          .css("stroke-dashoffset", pathLength)
          .css(
            "animation",
            s"guidePathDashOffset ${annotationLineAnimationDuration}s linear forwards"
          )
          .css("animation-delay", s"${annotationLineAnimationDelay}s")
      }

      pointElements.each { path: Element =>
        jQuery(path)
          .css(
            "animation",
            s"guidePointScale ${annotationCircleAnimationDuration}s ease-out forwards"
          )
          .css("animation-delay", s"${annotationCircleAnimationDelay}s")
      }
    }

  }

  def initHoleNotes() = {

    auth.CurrentUser.foreach { currentUser =>
      currentUser match {
        case Some(user) => {
          PageHoleData.map { data =>
            (data.id :: data.aliases)
              .traverse(holeId => store.notes.holeNotesForUser(user, holeId))
              .map(_.flatten.foreach(addNote))
              .runAsync
          }

          jQuery("#user-notes").removeClass("hidden")
          jQuery(".gcn-btn-add-hole-note, .gcn-btn-hole-note-tutorial").removeClass("hidden")
          ()
        }
        case None => {
          jQuery(".gcn-hole-note").remove()
          jQuery("#user-notes").addClass("hidden")
          jQuery(".gcn-btn-add-hole-note, .gcn-btn-hole-note-tutorial").addClass("hidden")
          ()
        }
      }
    }

    PageHoleData = {
      Some(
        HoleData(
          jQuery("#hole-data").data("hole-id").asInstanceOf[String],
          jQuery("#hole-data").data("hole-aliases").asInstanceOf[js.Array[String]].toList
        )
      )
    }

    jQuery("#hole-note-edit-modal").on("hidden.bs.modal", (e: js.Any) => clearNoteForm())

    jQuery("#hole-note-add-btn").click { () =>
      jQuery("#hole-note-edit-modal").asInstanceOf[js.Dynamic].modal("show")
    }

    jQuery("#note-cancel-btn").click { () =>
      CurrentHoleNote = None
      clearNoteForm()
    }

    jQuery("#note-cancel-delete-btn").click { () =>
      CurrentHoleNote = None
    }

    jQuery("#note-save-btn").click { () =>
      CurrentHoleNote match {
        case Some(noteToUpdate) => {
          noteFromForm.map { newNote =>
            store.notes
              .updateNote(
                noteToUpdate.copy(
                  category = newNote.category,
                  content = newNote.content
                )
              )
              .map { updatedNote =>
                updateNote(updatedNote)
              }
              .runAsync
          }
        }
        case None => {
          noteFromForm.map { newNote =>
            store.notes
              .saveNote(newNote)
              .map { savedNote =>
                addNote(savedNote)
              }
              .runAsync
          }
        }
      }

      jQuery("#hole-note-edit-modal").asInstanceOf[js.Dynamic].modal("hide")
      clearNoteForm()
      CurrentHoleNote = None
    }

    jQuery("#note-delete-btn").click { () =>
      CurrentHoleNote match {
        case Some(note) => {
          deleteNote(note)
        }
        case None => ()
      }
    }
  }

  def addNote(note: HoleNote) = {
    jQuery("#user-notes").append(createNoteElement(note).render)
    sortNoteElements()
  }

  def editNote(note: HoleNote) = {
    CurrentHoleNote = Some(note)

    jQuery("#noteId").`val`(note.id.getOrElse(""))
    jQuery("#noteCategory").`val`(note.category)
    jQuery("#noteContents").`val`(note.content)
    jQuery("#hole-note-edit-modal").asInstanceOf[js.Dynamic].modal("show")
  }

  def updateNote(note: HoleNote) = {
    note.id.map { id =>
      store.notes.updateNote(note)
      jQuery(s""".gcn-hole-note[data-note-id="$id"]""").replaceWith(createNoteElement(note).render)
    }

    sortNoteElements()
  }

  def confirmDeleteNote(note: HoleNote) = {
    note.id.map { id =>
      CurrentHoleNote = Some(note)
      jQuery("#deleteNoteId").`val`(id)
      jQuery("#hole-note-confirm-delete-modal").asInstanceOf[js.Dynamic].modal("show")
    }
  }

  def deleteNote(note: HoleNote) = {
    note.id.map { id =>
      store.notes
        .deleteNote(note)
        .map { _ =>
          jQuery(s""".gcn-hole-note[data-note-id="$id"]""").remove()
          jQuery("#hole-note-confirm-delete-modal").asInstanceOf[js.Dynamic].modal("hide")
          CurrentHoleNote = None
        }
        .runAsync
    }
  }

  def clearNoteForm(): Unit = {
    jQuery("#noteId").`val`("")
    jQuery("#noteCategory").`val`("general")
    jQuery("#noteContents").`val`("")
    ()
  }

  def noteFromForm(): Option[HoleNote] = {
    for {
      user     <- auth.CurrentUser()
      holeData <- PageHoleData
    } yield {
      val id       = Some(jQuery("#noteId").`val`.asInstanceOf[String]).filter(_.nonEmpty)
      val category = jQuery("#noteCategory").`val`.asInstanceOf[String]
      val content  = jQuery("#noteContents").`val`.asInstanceOf[String]
      HoleNote(id, user.uid, holeData.id, category, content)
    }
  }

  def sortNoteElements() = {
    val sortedElements =
      jQuery(".gcn-hole-note").toArray.toList
        .sortWith { (noteElementA, noteElementB) =>
          val categoryA = jQuery(noteElementA).data("note-category").asInstanceOf[String]
          val categoryB = jQuery(noteElementB).data("note-category").asInstanceOf[String]
          categoryToInt(categoryA) < categoryToInt(categoryB)
        }

    jQuery("#user-notes").children().not(":first").remove()
    sortedElements.foreach(e => jQuery("#user-notes").append(e))
  }

  def categoryToInt(category: String) = {
    category match {
      case "general" => 0
      case "masters" => 1
      case "expert"  => 2
      case "pro"     => 3
      case "rookie"  => 4
      case _         => 5
    }
  }

  def createNoteElement(note: HoleNote) = {

    import scalatags.JsDom.all._

    div(
      cls := s"gcn-hole-note gcn-hole-note-${note.category}",
      data("note-id") := note.id.getOrElse(""),
      data("note-category") := note.category
    )(
      div(cls := "toolbar")(
        button(
          `type` := "button",
          onclick := { () =>
            editNote(note)
          },
          cls := "btn btn-link",
          title := "Edit"
        )(
          i(cls := "far fa-edit")
        ),
        button(
          `type` := "button",
          onclick := { () =>
            confirmDeleteNote(note)
          },
          cls := "btn btn-link",
          title := "Delete"
        )(
          i(cls := "fas fa-trash")
        )
      ),
      div(cls := "note-contents")(
        raw(MarkedJs(note.content))
      )
    )

  }

}
