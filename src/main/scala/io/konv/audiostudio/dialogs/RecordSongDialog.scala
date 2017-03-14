package io.konv.audiostudio.dialogs

import javafx.scene.Parent
import javafx.stage.Stage

import io.konv.audiostudio.Main
import io.konv.audiostudio.controllers.RecordSongTrait

import scalafx.Includes._
import scalafx.scene.control.{ButtonType, Dialog}
import scalafx.scene.image.Image
import scalafxml.core.FXMLLoader

class RecordSongDialog extends Dialog[RecordSongForm] {
  title = "Audio Studio Manager"
  headerText = "Record Song"

  val loader = new FXMLLoader(Main.getClass.getResource("/fxml/dialog_record_song.fxml"), null)

  dialogPane().content = loader.load[Parent]
  dialogPane().buttonTypes = Seq(ButtonType.OK, ButtonType.Cancel)
  dialogPane().getScene.getWindow.asInstanceOf[Stage].icons += new Image("img/icon.png")

  resultConverter = buttonType => {
    if (buttonType == ButtonType.OK) loader.getController[RecordSongTrait].input()
    else null
  }
}

case class RecordSongForm(name: String, title: String, price: String)
