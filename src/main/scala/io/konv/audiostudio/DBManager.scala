package io.konv.audiostudio

import io.konv.audiostudio.models.{Artist, Genre, Record}
import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

object DBManager {

  lazy val db = Database.forConfig("database")

  def artists(): Future[Vector[Artist]] = {
    implicit val getResult = GetResult[Artist](r => Artist(r.<<, r.<<, r.<<))
    val query = sql"SELECT id, name, registered_date FROM artist".as[Artist]
    db.run(query)
  }

  def genres(): Future[Vector[Genre]] = {
    implicit val getGenre = GetResult[Genre](r => Genre(r.<<, r.<<, r.<<))
    val query = sql"SELECT id, name, description FROM genre".as[Genre]
    db.run(query)
  }

  def records(): Future[Vector[Record]] = {
    implicit val getResult = GetResult[Record](r => Record(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<))
    val query = sql"SELECT id, title, price, artist_id, genre_id, path FROM record".as[Record]
    db.run(query)
  }
}
