package io.konv.audiostudio.controllers

import javafx.scene.Parent

import io.konv.audiostudio.dialogs.{AddArtistDialog, RecordSongDialog}
import io.konv.audiostudio.models.Record
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

  val artistsLoader = new FXMLLoader(Main.getClass.getResource("/fxml/table_artist.fxml"), null)
  val recordsLoader = new FXMLLoader(Main.getClass.getResource("/fxml/table_record.fxml"), null)

  tabPane.getTabs.get(0).setContent(artistsLoader.load[Parent])
  tabPane.getTabs.get(1).setContent(recordsLoader.load[Parent])
  def addArtist(): Unit = {
    val result = new AddArtistDialog().showAndWait()
    result match {
      case Some(v) => DBManager.db.run(sqlu"INSERT INTO artist(name) VALUES ('#$v')").onComplete {
        case Success(v) => ()
      }
    }
  }

  def recordSong(): Unit = new RecordSongDialog().showAndWait() match {
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
}
