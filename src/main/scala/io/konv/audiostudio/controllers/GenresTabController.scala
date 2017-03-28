package io.konv.audiostudio.controllers

import javafx.collections.FXCollections
import javafx.scene.input.KeyCode

import io.konv.audiostudio.Includes._
import io.konv.audiostudio.{Alerts, DBManager}
import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.scene.control.{ButtonType, TableColumn, TableView, TextField}
import scalafxml.core.macros.sfxml

case class GenresTabRow(id: Int, genre: String, description: String)

trait GenresTabTrait {
  def update(): Unit
}

@sfxml
class GenresTabController(table: TableView[GenresTabRow],
                          genre: TableColumn[GenresTabRow, String],
                          description: TableColumn[GenresTabRow, String],
                          searchField: TextField
                         ) extends GenresTabTrait {

  genre.cellValueFactory = v => v.value.genre
  description.cellValueFactory = v => v.value.description

  table.editable = true

  genre.editable = true
  genre.cellFactory = TextFieldTableCell.forTableColumn[GenresTabRow]()
  genre.onEditCommit = v => {
    if (v.getNewValue.length == 0) Alerts.info("Edit Genre", "Title should not be empty")
    else DBManager.db.run(sqlu"UPDATE genre SET name = ${v.getNewValue} WHERE id = ${v.getRowValue.id}").onComplete {
      case Success(v) => Alerts.info("Edit Genre", "Name changed")
      case Failure(v) => Alerts.info("Edit Genre", "Something went wrong")
    }
  }

  description.editable = true
  description.cellFactory = TextFieldTableCell.forTableColumn[GenresTabRow]()
  description.onEditCommit = v => {
    DBManager.db.run(sqlu"UPDATE genre SET description = ${v.getNewValue} WHERE id = ${v.getRowValue.id}").onComplete {
      case Success(v) => Alerts.info("Edit Genre", "Description changed")
      case Failure(v) => Alerts.info("Edit Genre", "Something went wrong")
    }
  }

  searchField.onAction = v => filter()

  update()

  table.onKeyPressed = k => k.getCode match {
    case KeyCode.DELETE => delete()
    case KeyCode.F5 => update()
    case _ => ()
  }

  var items: Vector[GenresTabRow] = null

  override def update(): Unit = {
    implicit val getResult = GetResult[GenresTabRow](r => GenresTabRow(r.<<, r.<<, r.<<))
    val query = sql"SELECT id, name, description FROM genre".as[GenresTabRow]
    DBManager.db.run(query).onComplete {
      case Success(v) => {
        items = v
        table.items.set(FXCollections.observableList(v.asJava))
      }
      case Failure(v) => ()
    }
  }

  private def delete(): Unit = {
    if (table.getSelectionModel.isEmpty) return
    val genre = table.getSelectionModel.getSelectedItem
    Alerts.confirm("Delete Genre", s"Do you really want to remove genre ${genre.genre}?") match {
      case Some(ButtonType.OK) => {
        val query = sqlu"DELETE FROM genre WHERE id = ${genre.id}"
        DBManager.db.run(query).onComplete {
          case Success(v) => update()
          case Failure(v) => ()
        }
      }
      case _ => ()
    }
  }

  private def filter(): Unit = {
    val query = searchField.getText.toLowerCase()
    if (query.length == 0) update()
    else table.items.set(FXCollections.observableList(items.filter(v => {
      var result = true
      val words = query.split("\\W+")
      val text = v.description.toLowerCase()
      for (w <- words) if (!text.contains(w)) result = false
      result
    }).asJava))
  }
}
