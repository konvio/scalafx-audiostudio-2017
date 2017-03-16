package io.konv.audiostudio

import io.konv.audiostudio.models.{Artist, Genre}
import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

object DBManager {

  lazy val database = Database.forConfig("database")

  def artists(): Future[Vector[Artist]] = {
    implicit val getResult = GetResult[Artist](r => Artist(r.<<, r.<<, r.<<))
    val query = sql"SELECT id, name, registered_date FROM artist".as[Artist]
    database.run(query)
  }

  def genres(): Future[Vector[Genre]] = {
    implicit val getGenre = GetResult[Genre](r => Genre(r.<<, r.<<, r.<<))
    val query = sql"SELECT id, name, description FROM genre".as[Genre]
    database.run(query)
  }


}
