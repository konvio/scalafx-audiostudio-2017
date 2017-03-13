package io.konv.audiostudio

import javafx.scene.Parent
import javafx.scene.control.ButtonBar.ButtonData
import javafx.stage.Stage

import io.konv.audiostudio.Models.RecordSongForm
import io.konv.audiostudio.controller.{RecordSongController, RecordSongControllerInterface}

import scalafx.Includes._
import scalafx.scene.control._
import scalafx.scene.image.Image
import scalafxml.core.FXMLLoader

object Dialogs {

  val addArtistDialog = new TextInputDialog() {
    title = "Audio Studio Manager"
    headerText = "Add Artist"
    contentText = "Name"
    dialogPane.value.getScene.getWindow.asInstanceOf[Stage].icons += new Image("img/icon.png")
  }

  val recordSongDialog = new Dialog[RecordSongForm]() {
    title = "Audio Studio Manager"
    headerText = "Record Song"

    dialogPane().buttonTypes = Seq(ButtonType.OK, ButtonType.Cancel)
    dialogPane().getScene.getWindow.asInstanceOf[Stage].icons += new Image("img/icon.png")

    val loader = new FXMLLoader(Main.getClass.getResource("/fxml/dialog_record_song.fxml"), null)
    dialogPane().content = loader.load[Parent]

    val controller = loader.getController[RecordSongControllerInterface]()

    resultConverter = buttonType => {
      if (buttonType == ButtonType.OK) controller.model()
      else null
    }
  }

}


