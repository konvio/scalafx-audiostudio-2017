package io.konv.audiostudio.controller

import scalafx.scene.control.TableView
import scalafx.scene.layout.BorderPane
import scalafxml.core.macros.sfxml

case class Person(firstName: String, lastName: String)

@sfxml
class MainController(val borderPane: BorderPane) {

  def onClick(): Unit = {
    borderPane.center.asInstanceOf[TableView[Person]].items.get().add(Person("hello", "world"))

  }

}
