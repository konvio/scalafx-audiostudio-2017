package io.konv.audiostudio.controllers

import io.konv.audiostudio.dialogs.RecordSongForm

import scalafx.scene.control.TextField
import scalafxml.core.macros.sfxml

trait RecordSongTrait {
  def input(): RecordSongForm
}

@sfxml
class RecordSongController(val name: TextField,
                           val title: TextField,
                           val price: TextField) extends RecordSongTrait {

  override def input() = RecordSongForm(name.getText, title.getText, price.getText)

}
