package io.konv.audiostudio.controller

import java.sql.Date
import javafx.collections.FXCollections

import io.konv.audiostudio.DBManager
import slick.jdbc.PostgresProfile.api._

import scalafx.Includes._
import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.scene.control.{TableColumn, TableView, TextInputDialog}
import scalafxml.core.macros.sfxml
import io.konv.audiostudio.Includes._
import slick.jdbc.GetResult

case class Artist(id: Int, name: String, date: Date)

@sfxml
class MainController(val tableView: TableView[Artist],
                     val id: TableColumn[Artist, String],
                     val name: TableColumn[Artist, String],
                     val date: TableColumn[Artist, String]) {

  implicit val GetArtist = GetResult[Artist](r => Artist(r.<<, r.<<, r.<<))

  private val db = DBManager.database
  private val items = tableView.items.get()

  initTable()
  populateData()

  private def initTable(): Unit = {
    id.cellValueFactory = v => v.value.id.toString
    name.cellValueFactory = v => v.value.name
    date.cellValueFactory = v => v.value.date.toString
  }

  private def populateData(): Unit = {
    val query = sql"SELECT id, name, registered_date FROM artist".as[Artist]
    db.run(query).onComplete {
      case Success(v) => tableView.items.set(FXCollections.observableList(v.asJava))
      case Failure(v) => ()
    }
  }

  def addArtist(): Unit = {
    val dialog = new TextInputDialog() {
      initOwner(tableView.scene.value.getWindow)
      title = "Title"
      headerText = "Header"
      contentText = "Content"
    }

    val result = dialog.showAndWait()

    result match {
      case Some(name) => db.run(sqlu"INSERT INTO artist(name) VALUES ('#$name')").onComplete {
        case Success(v) => populateData()
      }
    }

  }

}
