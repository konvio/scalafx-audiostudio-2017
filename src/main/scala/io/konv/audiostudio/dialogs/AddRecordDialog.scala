package io.konv.audiostudio.dialogs

import javafx.scene.Parent
import javafx.stage.Stage

import io.konv.audiostudio.Main
import io.konv.audiostudio.controllers.RecordForm
import io.konv.audiostudio.models.Record

import scalafx.Includes._
import scalafx.scene.control.{ButtonType, Dialog}
import scalafx.scene.image.Image
import scalafxml.core.FXMLLoader

class AddRecordDialog extends Dialog[Record] {

  val loader = new FXMLLoader(Main.getClass.getResource("/fxml/dialog_add_record.fxml"), null)

  title = "Audio Studio Manager"
  headerText = "Record Song"

  dialogPane().content = loader.load[Parent]
  dialogPane().buttonTypes = Seq(ButtonType.OK, ButtonType.Cancel)
  dialogPane().getScene.getWindow.asInstanceOf[Stage].icons += new Image("img/icon.png")

  resultConverter = buttonType => {
    if (buttonType == ButtonType.OK) loader.getController[RecordForm].get()
    else null
  }
}

