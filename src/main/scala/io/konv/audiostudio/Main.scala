package io.konv.audiostudio

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafxml.core.FXMLView

object Main extends JFXApp {

  val root = FXMLView(getClass.getResource("/fxml/main.fxml"), null)

  stage = new JFXApp.PrimaryStage() {
    title = "Audio Studio Manager"
    scene = new Scene(root) {
      stylesheets = List(Main.getClass.getResource("/css/style.css").toExternalForm)
    }
    icons += new Image("img/icon.png")
    minWidth = 600
  }
}