package io.konv.audiostudio.controllers

import javafx.collections.FXCollections

import io.konv.audiostudio.DBManager
import io.konv.audiostudio.dialogs.RecordSongForm
import io.konv.audiostudio.models.{Artist, Record}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.scene.control.{ChoiceBox, TextField}
import scalafxml.core.macros.sfxml

trait RecordForm {
  def input(): RecordSongForm

  def get(): Record

}

@sfxml
class RecordSongController(val artistChoiceBox: ChoiceBox[Artist],
                           val titleField: TextField,
                           val priceField: TextField) extends RecordForm {

  override def input() = RecordSongForm(null, titleField.getText, priceField.getText)

  override def get(): Record = {
    val artistId = artistChoiceBox.getValue.id
    val title = titleField.getText
    val price = priceField.getText


  }

  DBManager.artists().onComplete {
    case Success(v) => artistChoiceBox.items.set(FXCollections.observableList(v.asJava))
    case Failure(v) => ()
  }
}
