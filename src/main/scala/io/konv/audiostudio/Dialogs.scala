package io.konv.audiostudio

import javafx.scene.Parent
import javafx.scene.control.ButtonBar.ButtonData
import javafx.stage.Stage

import io.konv.audiostudio.Models.Record
import io.konv.audiostudio.controller.RecordSongController

import scalafx.Includes._
import scalafx.scene.control._
import scalafx.scene.image.Image
import scalafxml.core.FXMLLoader

object Dialogs {

  val addArtistDialog = new TextInputDialog() {
    title = "Audio Studio"
    headerText = "Add Artist"
    contentText = "Name"
    dialogPane.value.getScene.getWindow.asInstanceOf[Stage].icons += new Image("img/icon.png")
  }

  val recordSongDialog = new Dialog[Record]() {
    title = "Audio Studio"
    headerText = "Record Song"
    dialogPane().buttonTypes = Seq(new ButtonType("OK", ButtonData.OK_DONE), new ButtonType("Cancel", ButtonData.CANCEL_CLOSE))
    dialogPane().getScene.getWindow.asInstanceOf[Stage].icons += new Image("img/icon.png")
    val loader = new FXMLLoader(Main.getClass.getResource("/fxml/dialog_record_song.fxml"), null)
    dialogPane.value.content = loader.load[Parent]
  }

}
