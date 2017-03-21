package io.konv.audiostudio.controllers

import javafx.collections.FXCollections

import io.konv.audiostudio.DBManager
import io.konv.audiostudio.dialogs.SongDialog
import io.konv.audiostudio.models.{Album, Record}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.scene.control.ChoiceBox
import scalafxml.core.macros.sfxml

trait AddSongToAlbumTrait {
  def get(): SongDialog
}

@sfxml
class AddSongToAlbumController(albumChoiceBox: ChoiceBox[Album],
                               recordChoiceBox: ChoiceBox[Record]) extends AddSongToAlbumTrait {

  DBManager.albums().onComplete {
    case Success(v) => albumChoiceBox.items.set(FXCollections.observableList(v.asJava))
    case Failure(v) => ()
  }

  DBManager.records().onComplete {
    case Success(v) => recordChoiceBox.items.set(FXCollections.observableList(v.asJava))
    case Failure(v) => ()
  }

  override def get(): SongDialog = {
    val albumId = albumChoiceBox.getSelectionModel.getSelectedItem.id
    val recordId = recordChoiceBox.getSelectionModel.getSelectedItem.id
    SongDialog(recordId, albumId)
  }
}
