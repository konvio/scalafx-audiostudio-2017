package io.konv.audiostudio

import io.konv
import io.konv.audiostudio
import io.konv.audiostudio.Tables.{Album, Artist, Genre, Record}
import slick.lifted.TableQuery

class SlickDBManager {

  def records(): TableQuery[audiostudio.Tables.Record] = TableQuery[Record]

  def albums(): TableQuery[konv.audiostudio.Tables.Album] = TableQuery[Album]

  def genres(): TableQuery[_root_.io.konv.audiostudio.Tables.Genre] = TableQuery[Genre]

  def artists(): TableQuery[_root_.io.konv.audiostudio.Tables.Artist] = TableQuery[Artist]

}
