package io.konv.audiostudio.controllers

import javafx.collections.FXCollections

import io.konv.audiostudio.DBManager
import io.konv.audiostudio.dialogs.RecordSongForm
import io.konv.audiostudio.models.Artist

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.scene.control.{ChoiceBox, TextField}
import scalafxml.core.macros.sfxml

trait RecordSongTrait {
  def input(): RecordSongForm
}

@sfxml
class RecordSongController(val artist: ChoiceBox[Artist],
                           val title: TextField,
                           val price: TextField) extends RecordSongTrait {

  override def input() = RecordSongForm(null, title.getText, price.getText)

  DBManager.artists.onComplete {
    case Success(v) => artist.items.set(FXCollections.observableList(v.asJava))
    case Failure(v) => ()
  }
}
