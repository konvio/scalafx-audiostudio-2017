package io.konv.audiostudio.controllers

import javafx.collections.FXCollections

import io.konv.audiostudio.{Alerts, DBManager}
import io.konv.audiostudio.Includes._
import io.konv.audiostudio.dialogs.{AddArtistDialog, RecordSongDialog}
import io.konv.audiostudio.models.{Artist, Record}
import slick.jdbc.PostgresProfile.api._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.application.Platform
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
    val result = new AddArtistDialog().showAndWait()
    result match {
      case Some(v) => db.run(sqlu"INSERT INTO artist(name) VALUES ('#$v')").onComplete {
        case Success(v) => update()
      }
    }
  }

  def recordSong(): Unit = new RecordSongDialog().showAndWait() match {
    case Some(Record(i, t, p, a, g, f)) => {
      db.run(sqlu"INSERT INTO record(title, price, artist_id, genre_id, path) VALUES (${t},${p},${a},${g},${f})").onComplete {
        case Success(v) => Platform.runLater(Alerts.info("Record Song", s"Song $t was successfully recorded"))
        case Failure(v) => Alerts.error("Record Song", "Something went wrong")
      }
    }
    case None => ()
  }


  private def update(): Unit = DBManager.artists.onComplete {
    case Success(v) => tableView.items.set(FXCollections.observableList(v.asJava))
    case Failure(v) => ()
  }


  private def initTable(): Unit = {
    id.cellValueFactory = v => v.value.id.toString
    name.cellValueFactory = v => v.value.name
    date.cellValueFactory = v => v.value.date.toString
  }
}
