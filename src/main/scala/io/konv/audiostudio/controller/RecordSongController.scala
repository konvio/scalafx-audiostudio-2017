package io.konv.audiostudio.controller

import io.konv.audiostudio.Models.RecordSongForm

import scalafx.scene.control.TextField
import scalafxml.core.macros.sfxml

trait RecordSongControllerInterface {
  def model(): RecordSongForm
}

@sfxml
class RecordSongController(val name: TextField,
                           val title: TextField,
                           val price: TextField) extends RecordSongControllerInterface {

  override def model(): RecordSongForm = RecordSongForm(name.getText, title.getText, price.getText)
}
