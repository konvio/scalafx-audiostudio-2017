package io.konv.audiostudio

import javafx.stage.Stage

import scalafx.Includes._
import scalafx.scene.control.TextInputDialog
import scalafx.scene.image.Image

object Dialogs {

  val addArtist = new TextInputDialog() {
    title = "Audio Studio"
    headerText = "Add Artist"
    contentText = "Name"
    dialogPane.value.getScene.getWindow.asInstanceOf[Stage].icons += new Image("img/icon.png")
  }


}
