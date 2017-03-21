package io.konv.audiostudio.dialogs

import javafx.scene.Parent
import javafx.stage.Stage

import io.konv.audiostudio.Main
import io.konv.audiostudio.controllers.{AddGenreForm, AddSongToAlbumTrait}

import scalafx.Includes._
import scalafx.scene.control.{ButtonType, Dialog}
import scalafx.scene.image.Image
import scalafxml.core.FXMLLoader
import scalafxml.core.macros.sfxml

case class SongDialog(songId: Int, albumId: Int)

class AddSongToAlbumDialog extends Dialog[SongDialog] {
  val loader = new FXMLLoader(Main.getClass.getResource("/fxml/dialog_add_song_album.fxml"), null)

  title = "Audio Studio Manager"
  headerText = "Add Song To Album"

  dialogPane().content = loader.load[Parent]
  dialogPane().buttonTypes = Seq(ButtonType.OK, ButtonType.Cancel)
  dialogPane().getScene.getWindow.asInstanceOf[Stage].icons += new Image("img/icon.png")

  resultConverter = buttonType => {
    if (buttonType == ButtonType.OK) loader.getController[AddSongToAlbumTrait].get()
    else null
  }

}
