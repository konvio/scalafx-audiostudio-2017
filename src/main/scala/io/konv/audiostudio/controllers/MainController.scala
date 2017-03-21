package io.konv.audiostudio.controllers

import javafx.scene.Parent

import io.konv.audiostudio.dialogs._
import io.konv.audiostudio.models.{Album, Genre, Record}
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
  val albumsLoader = new FXMLLoader(Main.getClass.getResource("/fxml/tab_albums.fxml"), null)
  tabPane.getTabs.get(0).setContent(artistsLoader.load[Parent])
  tabPane.getTabs.get(1).setContent(recordsLoader.load[Parent])
  tabPane.getTabs.get(2).setContent(genresLoader.load[Parent])
  tabPane.getTabs.get(3).setContent(albumsLoader.load[Parent])

  def addArtist(): Unit = {
    val result = new AddArtistDialog().showAndWait()
    result match {
      case Some(v) => DBManager.db.run(sqlu"INSERT INTO artist(name) VALUES ('#$v')").onComplete {
        case Success(v) => artistsLoader.getController[ArtistsTabTrait].update()
        case Failure(v) => ()
      }
    }
  }

  def addRecord(): Unit = new AddRecordDialog().showAndWait() match {
    case Some(Record(i, t, p, a, g, f)) => {
      DBManager.db.run(sqlu"INSERT INTO record(title, price, artist_id, genre_id, path) VALUES (${t},${p},${a},${g},${f})").onComplete {
        case Success(v) => {
          update()
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
          update()
          Platform.runLater(Alerts.info("Add Genre", s"Genre $n is successfully added"))
        }
        case Failure(v) => Alerts.error("Record Song", "Something went wrong")
      }
    }
    case None => ()
  }

  def addAlbum(): Unit = new AddAlbumDialog().showAndWait() match {
    case Some(Album(i, t, p)) => {
      DBManager.db.run(sqlu"INSERT INTO album(title, price) VALUES (${t}, ${p})").onComplete {
        case Success(v) => update()
        case Failure(v) => Alerts.error("Record Song", "Something went wrong")
      }
    }
    case None => ()
  }

  def addSongToAlbum(): Unit = new AddSongToAlbumDialog().showAndWait() match {
    case Some(SongDialog(s, a)) => {
      DBManager.db.run(sqlu"INSERT INTO album_record(album_id, record_id) VALUES (${a}, ${s})").onComplete {
        case Success(v) => update()
        case Failure(v) => ()
      }
    }
  }

  def update(): Unit = {
    artistsLoader.getController[ArtistsTabTrait].update()
    recordsLoader.getController[RecordTabTrait].update()
    genresLoader.getController[GenresTabTrait].update()
    albumsLoader.getController[AlbumsTabTrait].update()
  }
}
