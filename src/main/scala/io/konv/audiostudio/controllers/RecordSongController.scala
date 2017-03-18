package io.konv.audiostudio.controllers

import javafx.collections.FXCollections

import io.konv.audiostudio.{Alerts, DBManager}
import io.konv.audiostudio.models.{Artist, Genre, Record}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.scene.control.{ChoiceBox, TextField}
import scalafxml.core.macros.sfxml

trait RecordForm {
  def get(): Record
}

@sfxml
class RecordSongController(val titleField: TextField,
                           val priceField: TextField,
                           val artistChoiceBox: ChoiceBox[Artist],
                           val genreChoiceBox: ChoiceBox[Genre],
                           val pathField: TextField) extends RecordForm {

  override def get(): Record = {
    try {
      val title = titleField.getText
      if (title == null) throw new IllegalStateException("Title should not be empty")

      val price = priceField.getText

      if (artistChoiceBox.getSelectionModel.isEmpty) throw new IllegalStateException("Artist is not selected")
      val artistId = artistChoiceBox.getValue.id

      if (genreChoiceBox.getSelectionModel.isEmpty) throw new IllegalStateException("Genre is not selected")
      val genreId = genreChoiceBox.getValue.id
      val path = pathField.getText

      Record(0, title, price.toInt, artistId, genreId, path)
    } catch {
      case v: IllegalStateException => {
        Alerts.info("Invalid input", v.getMessage)
        null
      }
      case v: NumberFormatException => {
        Alerts.info("Invalid input", "Please, enter numeric price")
        null
      }
      case _: Throwable => null
    }
  }

  DBManager.artists().onComplete {
    case Success(v) => artistChoiceBox.items.set(FXCollections.observableList(v.asJava))
    case Failure(v) => ()
  }

  DBManager.genres().onComplete {
    case Success(v) => genreChoiceBox.items.set(FXCollections.observableList(v.asJava))
    case Failure(v) => ()
  }
}
