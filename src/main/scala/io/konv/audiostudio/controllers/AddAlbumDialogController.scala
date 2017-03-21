package io.konv.audiostudio.controllers

import io.konv.audiostudio.Alerts
import io.konv.audiostudio.models.Album

import scalafx.scene.control.TextField
import scalafxml.core.macros.sfxml

trait AddAlbumForm {
  def get(): Album
}

@sfxml
class AddAlbumDialogController(titleField: TextField, priceField: TextField) extends AddAlbumForm {

  override def get() = {
    try {
      val title = titleField.getText
      if (title.length == 0) throw new IllegalStateException("Title should not be empty")
      val price = priceField.getText.toInt
      Album(0, title, price)
    } catch {
      case v: IllegalStateException => Alerts.info("Invalid input", v.getMessage)
        null
      case v: NumberFormatException => Alerts.info("Invalid input", "Please, enter numeric price")
        null
      case _: Throwable => Alerts.error("Something went wrong", null)
        null
    }
  }
}
