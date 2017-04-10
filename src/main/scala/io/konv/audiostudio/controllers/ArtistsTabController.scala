package io.konv.audiostudio.controllers

import java.sql.Date
import javafx.collections.FXCollections
import javafx.scene.input.KeyCode

import io.konv.audiostudio.Includes._
import io.konv.audiostudio.models.Artist
import io.konv.audiostudio.{Alerts, DBManager}
import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.scene.control.{ButtonType, TableColumn, TableView}
import scalafxml.core.macros.sfxml

trait ArtistsTabTrait {
  def update(): Unit
}

case class ArtistTabRow(id: Int, name: String, songsCount: Int, income: Int, date: Date)

@sfxml
class ArtistsTabController(val tableView: TableView[ArtistTabRow],
                           val id: TableColumn[ArtistTabRow, String],
                           val name: TableColumn[ArtistTabRow, String],
                           val count: TableColumn[ArtistTabRow, String],
                           val sum: TableColumn[ArtistTabRow, String],
                           val date: TableColumn[ArtistTabRow, String]) extends ArtistsTabTrait {

  id.cellValueFactory = v => v.value.id.toString
  name.cellValueFactory = v => v.value.name
  count.cellValueFactory = v => v.value.songsCount.toString
  sum.cellValueFactory = v => v.value.income.toString
  date.cellValueFactory = v => v.value.date.toString

  tableView.editable = true
  name.editable = true
  name.cellFactory = TextFieldTableCell.forTableColumn[ArtistTabRow]()
  name.onEditCommit = v => {
    v.getRowValue.id
    if (v.getNewValue.length == 0) Alerts.info("Edit Artist", "Name should not be empty")
    else DBManager.db.run(sqlu"UPDATE artist SET name = ${v.getNewValue} WHERE id = ${v.getRowValue.id}").onComplete {
      case Success(v) => Alerts.info("Edit Artist", "Name changed")
      case Failure(v) => Alerts.info("Edit Artist", "Something went wrong")
    }
  }

  tableView.onKeyPressed = k => k.getCode match {
    case KeyCode.DELETE => delete()
    case KeyCode.F5 => update()
    case _ => ()
  }

  update()

  override def update(): Unit = {
    implicit val getResult = GetResult[ArtistTabRow](r => ArtistTabRow(r.<<, r.<<, r.<<, r.<<, r.<<))
    val query =
      sql"""
            SELECT artist.id, artist.name, count(record.id), sum(record.price), artist.registered_date
            FROM artist LEFT JOIN record ON artist.id = record.artist_id
            GROUP BY artist.id""".as[ArtistTabRow]
    DBManager.db.run(query).onComplete {
      case Success(v) => tableView.items.set(FXCollections.observableList(v.asJava))
      case Failure(v) => ()
    }
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
