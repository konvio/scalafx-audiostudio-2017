package io.konv.audiostudio

import javafx.stage.Stage

import io.konv.audiostudio.Models.{Artist, Record}

import scalafx.Includes._
import scalafx.scene.control.{ButtonType, ChoiceBox, Dialog, TextInputDialog}
import scalafx.scene.image.Image

object Dialogs {

  val addArtist = new TextInputDialog() {
    title = "Audio Studio"
    headerText = "Add Artist"
    contentText = "Name"
    dialogPane.value.getScene.getWindow.asInstanceOf[Stage].icons += new Image("img/icon.png")
  }

  val recordSong = new Dialog[Record]() {
    title = "Audio Studio"
    headerText = "Record Song"
  }

  private def initRecordSongDialog() = {
    recordSong.dialogPane().buttonTypes = Seq(ButtonType.OK, ButtonType.Cancel)

    val b = new ChoiceBox[Artist]()

  }
}
