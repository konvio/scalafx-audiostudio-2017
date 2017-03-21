package io.konv.audiostudio.controllers

import java.sql.Date
import javafx.collections.FXCollections
import javafx.scene.input.KeyCode

import io.konv.audiostudio.{Alerts, DBManager}
import io.konv.audiostudio.Includes._
import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.scene.control.{ButtonType, TableColumn, TableView}
import scalafxml.core.macros.sfxml

case class RecordTab(id: Int, title: String, price: Int, date: Date, path: String, artist: String)

trait RecordTabTrait {
  def update(): Unit
}

@sfxml
class RecordTabController(table: TableView[RecordTab],
                          title: TableColumn[RecordTab, String],
                          artist: TableColumn[RecordTab, String],
                          price: TableColumn[RecordTab, String],
                          date: TableColumn[RecordTab, String],
                          path: TableColumn[RecordTab, String]
                         ) extends RecordTabTrait {

  title.cellValueFactory = v => v.value.title
  artist.cellValueFactory = v => v.value.artist
  price.cellValueFactory = v => v.value.price.toString
  date.cellValueFactory = v => v.value.date.toString
  path.cellValueFactory = v => v.value.path

  update()

  table.onKeyPressed = k => k.getCode match {
    case KeyCode.DELETE => delete()
    case KeyCode.F5 => update()
    case _ => ()
  }

  override def update(): Unit = {
    implicit val getResult = GetResult(r => RecordTab(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<))
    val query = sql"""
                  SELECT record.id, record.title, record.price, record.released_date, record.path, artist.name
                  FROM record INNER JOIN artist ON record.artist_id = artist.id
      """.as[RecordTab]
    DBManager.db.run(query).onComplete {
      case Success(v) => table.items.set(FXCollections.observableList(v.asJava))
      case Failure(v) => ()
    }
  }

  private def delete(): Unit = {
    if (table.getSelectionModel.isEmpty) return
    val record = table.getSelectionModel.getSelectedItem
    Alerts.confirm("Delete Record", s"Do you really want to remove record ${record.title}?") match {
      case Some(ButtonType.OK) => {
        val query = sqlu"DELETE FROM record WHERE id = ${record.id}"
        DBManager.db.run(query).onComplete {
          case Success(v) => update()
          case Failure(v) => ()
        }
      }
      case _ => ()
    }
  }
}