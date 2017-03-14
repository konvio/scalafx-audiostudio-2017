package io.konv.audiostudio.dialogs

import javafx.stage.Stage

import scalafx.Includes._
import scalafx.scene.control.TextInputDialog
import scalafx.scene.image.Image

class AddArtistDialog extends TextInputDialog {
  title = "Audio Studio Manager"
  headerText = "Add Artist"
  contentText = "Name"
  dialogPane().getScene.getWindow.asInstanceOf[Stage].icons += new Image("img/icon.png")
}
