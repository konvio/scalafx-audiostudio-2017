package io.konv.audiostudio

import io.konv.audiostudio.models.Artist
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.application.Platform
import scalafx.scene.control.TextInputDialog

object QueriesController {

  def query0(): Unit = {

    val dialog = new TextInputDialog() {
      title = "Query Input"
      headerText = "Artists by Record"
      contentText = "Record"
    }

    dialog.showAndWait() match {
      case Some(title) => {
        val query =
          sql""" SELECT DISTINCT artist.name
                   FROM artist INNER JOIN record ON artist.id = record.artist_id
                   WHERE record.title = ${title}
                 """.as[String]
        DBManager.db.run(query).onComplete {
          case Success(v) => Platform.runLater(Alerts.showQueryResult(v, "Query 1"))
          case Failure(v) =>
        }
      }
      case None => ()
    }
  }

  def query1(): Unit = {

    val dialog = new TextInputDialog() {
      title = "Query Input"
      headerText = "Artists by Genre"
      contentText = "Genre"
    }

    dialog.showAndWait() match {
      case Some(genre) => {
        val query =
          sql""" SELECT DISTINCT artist.name
                   FROM artist INNER JOIN record ON artist.id = record.artist_id
                   INNER JOIN genre ON record.genre_id = genre.id
                   WHERE genre.name = ${genre}
                 """.as[String]
        DBManager.db.run(query).onComplete {
          case Success(v) => Platform.runLater(Alerts.showQueryResult(v, "Query 1"))
          case Failure(v) =>
        }
      }
      case None => ()
    }
  }

  def query2(): Unit = {

    val dialog = new TextInputDialog() {
      title = "Query Input"
      headerText = "Artists by Album"
      contentText = "Album"
    }

    dialog.showAndWait() match {
      case Some(input) => {
        val query =
          sql""" SELECT name
                   FROM artist JOIN record ON artist.id = record.artist_id
                   JOIN album_record ON record.id = album_record.record_id
                   JOIN album ON album_record.album_id = album.id
                   WHERE album.title = '#$input';
            """.as[String]
        DBManager.db.run(query).onComplete {
          case Success(v) => Platform.runLater(Alerts.showQueryResult(v, "Query 2"))

          case Failure(v) => ()
        }
      }
      case None => ()
    }
  }

  def query3(): Unit = {

    val dialog = new TextInputDialog() {
      title = "Query Form"
      headerText = "Artists by Genre Inverse"
      contentText = "Genre"
    }

    dialog.showAndWait() match {
      case Some(input) => {
        val query =
          sql""" WITH artists_of_genre AS (
                    SELECT record.artist_id AS id
                    FROM record JOIN genre ON record.genre_id = genre.id
                    WHERE genre.name = '#$input')
                 SELECT artist.name
                  FROM artist
                  WHERE artist.id NOT IN (SELECT id FROM artists_of_genre) ;
            """.as[String]
        DBManager.db.run(query).onComplete {
          case Success(v) => Platform.runLater(Alerts.showQueryResult(v, "Query 2"))

          case Failure(v) => ()
        }
      }
      case None => ()
    }
  }

  def query4(): Unit = {
    Alerts.chooseArtistDialog("Artists with exactly similar genres (=)").showAndWait() match {
      case Some(Artist(id, name, date)) => {
        val query =
          sql"""
                  SELECT artist.name
                  FROM artist
                  GROUP BY artist.id
                  HAVING artist.id = ${id} OR 2 = ALL(
                      SELECT count(DISTINCT record.artist_id)
                      FROM record
                      WHERE record.artist_id = artist.id OR record.artist_id = ${id}
                      GROUP BY record.genre_id);
          """.as[String]
        DBManager.db.run(query).onComplete {
          case Success(v) => Platform.runLater(Alerts.showQueryResult(v, "Query 4"))
          case Failure(v) => ()
        }
      }
    }
  }

  def query5(): Unit = {
    Alerts.chooseArtistDialog("Artists with only genres (<=)").showAndWait() match {
      case Some(Artist(id, name, date)) => {
        val query =
          sql"""  SELECT artist.name
                  FROM artist
                  WHERE (SELECT count(DISTINCT record.genre_id)
                         FROM record
                         WHERE record.artist_id = artist.id)
                         =
                         (SELECT count(genre_id) OVER()
                          FROM record
                          WHERE artist_id = artist.id OR artist_id = ${id}
                          GROUP BY genre_id
                          HAVING count(DISTINCT artist_id) = 2)
          """.as[String]
        DBManager.db.run(query).onComplete {
          case Success(v) => Platform.runLater(Alerts.showQueryResult(v, "Query 5"))
          case Failure(v) => ()
        }
      }
    }
  }

  def query6(): Unit = {
    Alerts.chooseArtistDialog("Artists with genres (>=)").showAndWait() match {
      case Some(Artist(id, name, date)) => {
        val query =
          sql"""  SELECT artist.name
                  FROM artist
                  WHERE (SELECT count(DISTINCT record.genre_id)
                         FROM record
                         WHERE record.artist_id = ${id})
                         =
                         (SELECT count(genre_id) OVER()
                          FROM record
                          WHERE artist_id = artist.id OR artist_id = ${id}
                          GROUP BY genre_id
                          HAVING count(DISTINCT artist_id) = 2)
          """.as[String]
        DBManager.db.run(query).onComplete {
          case Success(v) => Platform.runLater(Alerts.showQueryResult(v, "Query 5"))
          case Failure(v) => ()
        }
      }
    }
  }

}
