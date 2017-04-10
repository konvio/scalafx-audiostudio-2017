package io.konv.audiostudio.controllers

import io.konv.audiostudio.Alerts
import io.konv.audiostudio.models.Genre

import scalafx.scene.control.{TextArea, TextField}
import scalafxml.core.macros.sfxml

trait AddGenreForm {
  def get(): Genre
}

@sfxml
class AddGenreDialogController(genreField: TextField, descriptionField: TextArea) extends AddGenreForm {

  override def get(): Genre = {
    val genre = genreField.getText
    if (genre.length == 0) {
      Alerts.info("Invalid input", "Genre title should not be empty")
      return null
    }
    val description = descriptionField.getText
    Genre(-1, genre, description)
  }
}
