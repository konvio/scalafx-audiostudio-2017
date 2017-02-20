package io.konv.audiostudio.controller

import io.konv.audiostudio.model.ArtistModel

import scalafx.scene.control.TableView
import scalafx.scene.layout.BorderPane
import scalafxml.core.macros.sfxml

@sfxml
class MainController(val borderPane: BorderPane) {
  val tableView = new TableView[ArtistModel]
}
