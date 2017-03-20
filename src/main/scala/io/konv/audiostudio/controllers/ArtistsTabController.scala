package io.konv.audiostudio.controllers

import javafx.collections.FXCollections
import javafx.scene.input.KeyCode

import io.konv.audiostudio.DBManager
import io.konv.audiostudio.Includes._
import io.konv.audiostudio.models.Artist

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.scene.control.{TableColumn, TableView}
import scalafxml.core.macros.sfxml

@sfxml
class ArtistsTabController(val tableView: TableView[Artist],
                           val id: TableColumn[Artist, String],
                           val name: TableColumn[Artist, String],
                           val date: TableColumn[Artist, String]) {

  id.cellValueFactory = v => v.value.id.toString
  name.cellValueFactory = v => v.value.name
  date.cellValueFactory = v => v.value.date.toString

  tableView.onKeyPressed = k => k.getCode match {
    case KeyCode.DELETE => delete()
    case _ => ()
  }

  update()

  private def update(): Unit = DBManager.artists().onComplete {
    case Success(v) => tableView.items.set(FXCollections.observableList(v.asJava))
    case Failure(v) => ()
  }

  private def delete(): Unit = {
    if (tableView.getSelectionModel.isEmpty) return
    val artist = tableView.getSelectionModel.getSelectedItem
    

  }
}
