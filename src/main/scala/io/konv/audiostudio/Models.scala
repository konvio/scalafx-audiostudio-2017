package io.konv.audiostudio

import java.sql.Date

object Models {

  case class Artist(id: Int, name: String, date: Date)

  case class Record(id: Int, title: String, price: Int)

  case class RecordSongForm(name: String, title: String, price: String)

}
