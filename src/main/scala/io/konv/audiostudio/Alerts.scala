package io.konv.audiostudio

import javafx.stage.Stage

import scalafx.Includes._
import scalafx.application.Platform
import scalafx.scene.control.{Alert, ButtonType}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.image.Image

object Alerts {

  def info: (String, String) => Unit = alert(AlertType.Information)

  def error: (String, String) => Unit = alert(AlertType.Error)

  def confirm(header: String, content: String): Option[ButtonType] = new Alert(AlertType.Confirmation) {
    title = "Audio Studio Manager"
    headerText = header
    contentText = content
    dialogPane().getScene.getWindow.asInstanceOf[Stage].icons += new Image("img/icon.png")
  }.showAndWait()

  def alert(alertType: AlertType)(header: String, content: String): Unit = Platform.runLater(new Alert(alertType) {
    title = "Audio Studio Manager"
    headerText = header
    contentText = content
    dialogPane().getScene.getWindow.asInstanceOf[Stage].icons += new Image("img/icon.png")
  }.showAndWait())

  def showQueryResult(result: Vector[String], header: String): Unit = {
    var content = ""
    for (v <- result) content += (v + "\n")
    if (content.length == 0) content = "Empty query"
    val dialog = new Alert(AlertType.Information) {
      title = "Query result"
      headerText = header
      contentText = content
      dialogPane().getScene.getWindow.asInstanceOf[Stage].icons += new Image("img/icon.png")
    }
    dialog.showAndWait()
  }
}
