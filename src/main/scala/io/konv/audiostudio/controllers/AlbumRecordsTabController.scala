package io.konv.audiostudio.controllers

import javafx.collections.FXCollections
import javafx.scene.input.KeyCode

import io.konv.audiostudio.{Alerts, DBManager}
import io.konv.audiostudio.Includes._
import slick.jdbc.GetResult

import scalafx.scene.control.{ButtonType, TableColumn, TableView}
import scalafxml.core.macros.sfxml
import slick.jdbc.PostgresProfile.api._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

case class AlbumRecordsTabRow(id: Int, artist: String, title: String, album: String)

trait AlbumRecordsTabTrait {
  def update(): Unit
}

@sfxml
class AlbumRecordsTabController(val table: TableView[AlbumRecordsTabRow],
                                val artist: TableColumn[AlbumRecordsTabRow, String],
                                val title: TableColumn[AlbumRecordsTabRow, String],
                                val album: TableColumn[AlbumRecordsTabRow, String]
                               ) extends AlbumRecordsTabTrait {
  artist.cellValueFactory = v => v.value.artist
  title.cellValueFactory = v => v.value.title
  album.cellValueFactory = v => v.value.album

  table.onKeyPressed = k => k.getCode match {
    case KeyCode.DELETE => delete()
    case KeyCode.F5 => update()
    case _ => ()
  }

  override def update(): Unit = {
    implicit val getResult = GetResult[AlbumRecordsTabRow](r => AlbumRecordsTabRow(r.<<, r.<<, r.<<, r.<<))
    val query =
      sql"""
      SELECT album_record.id, artist.name, record.title, album.title
      FROM album_record INNER JOIN record ON album_record.record_id = record.id
                        INNER JOIN album ON album_record.album_id = album.id
                        INNER JOIN artist ON record.artist_id = artist.id
                        """.as[AlbumRecordsTabRow]
    DBManager.db.run(query).onComplete {
      case Success(v) => table.items.set(FXCollections.observableList(v.asJava))
      case Failure(v) => ()
    }
  }

  private def delete(): Unit = {
    if (table.getSelectionModel.isEmpty) return
    val item = table.getSelectionModel.getSelectedItem
    Alerts.confirm("Delete", s"Do you really want to remove record from album?") match {
      case Some(ButtonType.OK) => {
        val query = sqlu"DELETE FROM album_record WHERE id = ${item.id}"
        DBManager.db.run(query).onComplete {
          case Success(v) => update()
          case Failure(v) => ()
        }
      }
      case _ => ()
    }
  }
}
