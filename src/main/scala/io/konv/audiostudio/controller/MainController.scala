package io.konv.audiostudio.controller

import javafx.collections.FXCollections

import com.typesafe.scalalogging.Logger
import slick.jdbc.PostgresProfile.api._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.scene.control.{TableColumn, TableView}
import scalafxml.core.macros.sfxml

import io.konv.audiostudio.Includes._

@sfxml
class MainController(val tableView: TableView[(Int, String)],
                     val nameColumn: TableColumn[(Int, String), String]) {


  val db = Database.forConfig("database")
  nameColumn.cellValueFactory = _.value._2

  def onClick(): Unit = {

    val futureArtist = sql"SELECT * FROM artist".as[(Int, String)]
    db.run(futureArtist).onComplete {
      case Success(v) => tableView.items.set(FXCollections.observableList(v.asJava))
    }
  }

}
