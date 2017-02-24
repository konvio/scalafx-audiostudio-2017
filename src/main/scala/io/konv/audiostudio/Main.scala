package io.konv.audiostudio

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafxml.core.FXMLView

import slick.jdbc.PostgresProfile.api._

object Main extends JFXApp {

  val root = FXMLView(getClass.getResource("/fxml/main.fxml"), null)

  stage = new JFXApp.PrimaryStage() {
    title = "Audio Studio Manager"
    scene = new Scene(root)
  }
}