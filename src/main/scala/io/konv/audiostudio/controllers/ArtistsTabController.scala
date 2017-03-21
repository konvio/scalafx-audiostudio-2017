package io.konv.audiostudio.controllers

import javafx.collections.FXCollections
import javafx.scene.input.KeyCode

import io.konv.audiostudio.Includes._
import io.konv.audiostudio.models.Artist
import io.konv.audiostudio.{Alerts, DBManager}
import slick.jdbc.PostgresProfile.api._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.scene.control.{ButtonType, TableColumn, TableView}
import scalafxml.core.macros.sfxml

trait ArtistsTabTrait {
  def update(): Unit
}

@sfxml
class ArtistsTabController(val tableView: TableView[Artist],
                           val id: TableColumn[Artist, String],
                           val name: TableColumn[Artist, String],
                           val date: TableColumn[Artist, String]) extends ArtistsTabTrait {

  id.cellValueFactory = v => v.value.id.toString
  name.cellValueFactory = v => v.value.name
  date.cellValueFactory = v => v.value.date.toString

  tableView.onKeyPressed = k => k.getCode match {
    case KeyCode.DELETE => delete()
    case KeyCode.F5 => update()
    case _ => ()
  }

  update()

  override def update(): Unit = DBManager.artists().onComplete {
    case Success(v) => tableView.items.set(FXCollections.observableList(v.asJava))
    case Failure(v) => ()
  }

  private def delete(): Unit = {
    if (tableView.getSelectionModel.isEmpty) return
    val artist = tableView.getSelectionModel.getSelectedItem
    Alerts.confirm("Delete Artist", s"Do you really want to remove artist ${artist.name}?") match {
      case Some(ButtonType.OK) => {
        val query = sqlu"DELETE FROM artist WHERE id = ${artist.id}"
        DBManager.db.run(query).onComplete {
          case Success(v) => update()
          case Failure(v) => ()
        }
      }
      case _ => ()
    }
  }
}
