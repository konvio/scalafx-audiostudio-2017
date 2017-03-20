package io.konv.audiostudio.controllers

import javafx.collections.FXCollections

import io.konv.audiostudio.DBManager
import io.konv.audiostudio.Includes._
import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.scene.control.{TableColumn, TableView}
import scalafxml.core.macros.sfxml

case class GenresTabRow(id: Int, genre: String, description: String)

trait GenresTabTrait {
  def update(): Unit
}

@sfxml
class GenresTabController(table: TableView[GenresTabRow],
                          genre: TableColumn[GenresTabRow, String],
                          description: TableColumn[GenresTabRow, String]
                         ) extends GenresTabTrait {

  genre.cellValueFactory = v => v.value.genre
  description.cellValueFactory = v => v.value.description

  update()

  override def update(): Unit = {
    implicit val getResult = GetResult[GenresTabRow](r => GenresTabRow(r.<<, r.<<, r.<<))
    val query = sql"SELECT id, name, description FROM genre".as[GenresTabRow]
    DBManager.db.run(query).onComplete {
      case Success(v) => table.items.set(FXCollections.observableList(v.asJava))
      case Failure(v) => ()
    }
  }
}
