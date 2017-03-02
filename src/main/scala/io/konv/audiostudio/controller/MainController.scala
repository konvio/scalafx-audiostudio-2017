package io.konv.audiostudio.controller

import java.sql.Date
import javafx.collections.FXCollections

import io.konv.audiostudio.Includes._
import io.konv.audiostudio.Models._
import io.konv.audiostudio.{DBManager, Dialogs}
import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.scene.control.{TableColumn, TableView}
import scalafxml.core.macros.sfxml


@sfxml
class MainController(val tableView: TableView[Artist],
                     val id: TableColumn[Artist, String],
                     val name: TableColumn[Artist, String],
                     val date: TableColumn[Artist, String]) {


  private val db = DBManager.database
  private val items = tableView.items.get()

  initTable()
  update()

  def addArtist(): Unit = {
    val result = Dialogs.addArtist.showAndWait()
    result match {
      case Some(v) => db.run(sqlu"INSERT INTO artist(name) VALUES ('#$v')").onComplete {
        case Success(v) => update()
      }
    }
  }

  def recordSong(): Unit = {
    val result = Dialogs.recordSong.showAndWait()
  }

  private def update(): Unit = {
    DBManager.artists.onComplete {
      case Success(v) => tableView.items.set(FXCollections.observableList(v.asJava))
      case Failure(v) => ()
    }
  }

  private def initTable(): Unit = {
    id.cellValueFactory = v => v.value.id.toString
    name.cellValueFactory = v => v.value.name
    date.cellValueFactory = v => v.value.date.toString
  }


}
