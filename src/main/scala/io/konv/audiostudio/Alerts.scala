package io.konv.audiostudio

import javafx.stage.Stage

import scalafx.Includes._
import scalafx.application.Platform
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.image.Image

object Alerts {

  def info: (String, String) => Unit = alert(AlertType.Information)

  def error: (String, String) => Unit = alert(AlertType.Error)

  def confirm: (String, String) => Unit = alert(AlertType.Confirmation)

  def alert(alertType: AlertType)(header: String, content: String): Unit = Platform.runLater(new Alert(alertType) {
    title = "Audio Studio Manager"
    headerText = header
    contentText = content
    dialogPane().getScene.getWindow.asInstanceOf[Stage].icons += new Image("img/icon.png")
  }.showAndWait())
}
