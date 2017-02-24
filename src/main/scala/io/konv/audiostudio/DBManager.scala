package io.konv.audiostudio

import slick.jdbc.PostgresProfile.api._

object DBManager {

  lazy val database = Database.forConfig("database")

}
