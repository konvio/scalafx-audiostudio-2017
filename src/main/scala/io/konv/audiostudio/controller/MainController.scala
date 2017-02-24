package io.konv.audiostudio.controller

import javafx.collections.FXCollections

import com.typesafe.scalalogging.Logger
import io.konv.audiostudio.DBManager
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

  private val db = DBManager.database
  nameColumn.cellValueFactory = _.value._2

}
