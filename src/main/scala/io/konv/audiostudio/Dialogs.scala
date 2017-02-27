package io.konv.audiostudio

import scalafx.scene.control.{Dialog, TextInputDialog}

object Dialogs {

  val addArtist = new TextInputDialog() {
    title = "Audio Studio"
    headerText = "Add Artist"
    contentText = "Name"
  }

}
