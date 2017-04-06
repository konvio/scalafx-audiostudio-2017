package io.konv.audiostudio

import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.application.Platform
import scalafx.scene.control.TextInputDialog

object QueriesController {
  def query1(): Unit = {

    val dialog = new TextInputDialog() {
      title = "Query Input"
      headerText = "Назви виконавців, які виконують композиції у заданому жанрі"
      contentText = "Жанр"
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
      headerText = "Назви виконавців, що виконують заданий альбом"
      contentText = "Альбом"
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
      title = "Query Input"
      headerText = "Назви виконавців, що не виконують заданий альбом"
      contentText = "Альбом"
    }

    dialog.showAndWait() match {
      case Some(input) => {
        val query =
          sql""" WITH artists_of_album AS (
                    SELECT record.artist_id AS id
                    FROM record JOIN album_record ON record.id = album_record.record_id
                                JOIN album ON album_record.album_id = album.id
                    WHERE album.title = '#$input')
                 SELECT artist.name
                  FROM artist
                  WHERE artist.id NOT IN (SELECT id FROM artists_of_album) ;
            """.as[String]
        DBManager.db.run(query).onComplete {
          case Success(v) => Platform.runLater(Alerts.showQueryResult(v, "Query 2"))

          case Failure(v) => ()
        }
      }
      case None => ()
    }
  }

  def query4(): Unit = ???

  def query5(): Unit = ???

  def query6(): Unit = ???
}
