package io.konv.audiostudio.controllers

import javafx.scene.Parent

import io.konv.audiostudio.dialogs.{AddArtistDialog, AddGenreDialog, RecordSongDialog}
import io.konv.audiostudio.models.{Genre, Record}
import io.konv.audiostudio.{Alerts, DBManager, Main}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.application.Platform
import scalafx.scene.control.TabPane
import scalafxml.core.FXMLLoader
import scalafxml.core.macros.sfxml


@sfxml
class MainController(val tabPane: TabPane) {

  val artistsLoader = new FXMLLoader(Main.getClass.getResource("/fxml/tab_artists.fxml"), null)
  val recordsLoader = new FXMLLoader(Main.getClass.getResource("/fxml/tab_records.fxml"), null)
  val genresLoader = new FXMLLoader(Main.getClass.getResource("/fxml/tab_genres.fxml"), null)

  tabPane.getTabs.get(0).setContent(artistsLoader.load[Parent])
  tabPane.getTabs.get(1).setContent(recordsLoader.load[Parent])
  tabPane.getTabs.get(2).setContent(genresLoader.load[Parent])

  def addArtist(): Unit = {
    val result = new AddArtistDialog().showAndWait()
    result match {
      case Some(v) => DBManager.db.run(sqlu"INSERT INTO artist(name) VALUES ('#$v')").onComplete {
        case Success(v) => ()
        case Failure(v) => ()
      }
    }
  }

  def addRecord(): Unit = new RecordSongDialog().showAndWait() match {
    case Some(Record(i, t, p, a, g, f)) => {
      DBManager.db.run(sqlu"INSERT INTO record(title, price, artist_id, genre_id, path) VALUES (${t},${p},${a},${g},${f})").onComplete {
        case Success(v) => {
          recordsLoader.getController[RecordTabTrait].update()
          Platform.runLater(Alerts.info("Record Song", s"Song $t was successfully recorded"))
        }
        case Failure(v) => Alerts.error("Record Song", "Something went wrong")
      }
    }
    case None => ()
  }

  def addGenre(): Unit = new AddGenreDialog().showAndWait() match {
    case Some(Genre(i, n, d)) => {
      DBManager.db.run(sqlu"INSERT INTO genre(name, description) VALUES (${n}, ${d})").onComplete {
        case Success(v) => {
          genresLoader.getController[GenresTabTrait].update()
          Platform.runLater(Alerts.info("Add Genre", s"Genre $n is successfully added"))
        }
        case Failure(v) => Alerts.error("Record Song", "Something went wrong")
      }
    }
    case None => ()
  }
}
