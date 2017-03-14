package io.konv.audiostudio

// AUTO-GENERATED Slick data input
/** Stand-alone Slick data input for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data input trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile

  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Album.schema ++ AlbumRecord.schema ++ Artist.schema ++ Genre.schema ++ Record.schema

  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Album
    *
    * @param id           Database column id SqlType(serial), AutoInc, PrimaryKey
    * @param title        Database column title SqlType(text)
    * @param price        Database column price SqlType(numeric), Default(Some(0))
    * @param releasedDate Database column released_date SqlType(date) */
  final case class AlbumRow(id: Int, title: String, price: Option[scala.math.BigDecimal] = Some(scala.math.BigDecimal("0")), releasedDate: Option[java.sql.Date])

  /** GetResult implicit for fetching AlbumRow objects using plain SQL queries */
  implicit def GetResultAlbumRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[scala.math.BigDecimal]], e3: GR[Option[java.sql.Date]]): GR[AlbumRow] = GR {
    prs =>
      import prs._
      AlbumRow.tupled((<<[Int], <<[String], <<?[scala.math.BigDecimal], <<?[java.sql.Date]))
  }

  /** Table description of table album. Objects of this class serve as prototypes for rows in queries. */
  class Album(_tableTag: Tag) extends profile.api.Table[AlbumRow](_tableTag, "album") {
    def * = (id, title, price, releasedDate) <> (AlbumRow.tupled, AlbumRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(title), price, releasedDate).shaped.<>({ r => import r._; _1.map(_ => AlbumRow.tupled((_1.get, _2.get, _3, _4))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column title SqlType(text) */
    val title: Rep[String] = column[String]("title")
    /** Database column price SqlType(numeric), Default(Some(0)) */
    val price: Rep[Option[scala.math.BigDecimal]] = column[Option[scala.math.BigDecimal]]("price", O.Default(Some(scala.math.BigDecimal("0"))))
    /** Database column released_date SqlType(date) */
    val releasedDate: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("released_date")
  }

  /** Collection-like TableQuery object for table Album */
  lazy val Album = new TableQuery(tag => new Album(tag))

  /** Entity class storing rows of table AlbumRecord
    *
    * @param id          Database column id SqlType(serial), AutoInc, PrimaryKey
    * @param recordId    Database column record_id SqlType(int4), Default(None)
    * @param albumId     Database column album_id SqlType(int4), Default(None)
    * @param trackNumber Database column track_number SqlType(int4), Default(Some(0)) */
  final case class AlbumRecordRow(id: Int, recordId: Option[Int] = None, albumId: Option[Int] = None, trackNumber: Option[Int] = Some(0))

  /** GetResult implicit for fetching AlbumRecordRow objects using plain SQL queries */
  implicit def GetResultAlbumRecordRow(implicit e0: GR[Int], e1: GR[Option[Int]]): GR[AlbumRecordRow] = GR {
    prs =>
      import prs._
      AlbumRecordRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[Int]))
  }

  /** Table description of table album_record. Objects of this class serve as prototypes for rows in queries. */
  class AlbumRecord(_tableTag: Tag) extends profile.api.Table[AlbumRecordRow](_tableTag, "album_record") {
    def * = (id, recordId, albumId, trackNumber) <> (AlbumRecordRow.tupled, AlbumRecordRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), recordId, albumId, trackNumber).shaped.<>({ r => import r._; _1.map(_ => AlbumRecordRow.tupled((_1.get, _2, _3, _4))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column record_id SqlType(int4), Default(None) */
    val recordId: Rep[Option[Int]] = column[Option[Int]]("record_id", O.Default(None))
    /** Database column album_id SqlType(int4), Default(None) */
    val albumId: Rep[Option[Int]] = column[Option[Int]]("album_id", O.Default(None))
    /** Database column track_number SqlType(int4), Default(Some(0)) */
    val trackNumber: Rep[Option[Int]] = column[Option[Int]]("track_number", O.Default(Some(0)))

    /** Foreign key referencing Album (database name album_record_album_id_fkey) */
    lazy val albumFk = foreignKey("album_record_album_id_fkey", albumId, Album)(r => Rep.Some(r.id), onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
    /** Foreign key referencing Record (database name album_record_record_id_fkey) */
    lazy val recordFk = foreignKey("album_record_record_id_fkey", recordId, Record)(r => Rep.Some(r.id), onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)

    /** Uniqueness Index over (recordId,albumId) (database name album_record_record_id_album_id_key) */
    val index1 = index("album_record_record_id_album_id_key", (recordId, albumId), unique = true)
  }

  /** Collection-like TableQuery object for table AlbumRecord */
  lazy val AlbumRecord = new TableQuery(tag => new AlbumRecord(tag))

  /** Entity class storing rows of table Artist
    *
    * @param id             Database column id SqlType(serial), AutoInc, PrimaryKey
    * @param name           Database column name SqlType(text)
    * @param registeredDate Database column registered_date SqlType(date) */
  final case class ArtistRow(id: Int, name: String, registeredDate: Option[java.sql.Date])

  /** GetResult implicit for fetching ArtistRow objects using plain SQL queries */
  implicit def GetResultArtistRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[java.sql.Date]]): GR[ArtistRow] = GR {
    prs =>
      import prs._
      ArtistRow.tupled((<<[Int], <<[String], <<?[java.sql.Date]))
  }

  /** Table description of table artist. Objects of this class serve as prototypes for rows in queries. */
  class Artist(_tableTag: Tag) extends profile.api.Table[ArtistRow](_tableTag, "artist") {
    def * = (id, name, registeredDate) <> (ArtistRow.tupled, ArtistRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), registeredDate).shaped.<>({ r => import r._; _1.map(_ => ArtistRow.tupled((_1.get, _2.get, _3))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(text) */
    val name: Rep[String] = column[String]("name")
    /** Database column registered_date SqlType(date) */
    val registeredDate: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("registered_date")
  }

  /** Collection-like TableQuery object for table Artist */
  lazy val Artist = new TableQuery(tag => new Artist(tag))

  /** Entity class storing rows of table Genre
    *
    * @param id          Database column id SqlType(serial), AutoInc, PrimaryKey
    * @param name        Database column name SqlType(text)
    * @param description Database column description SqlType(text), Default(None) */
  final case class GenreRow(id: Int, name: String, description: Option[String] = None)

  /** GetResult implicit for fetching GenreRow objects using plain SQL queries */
  implicit def GetResultGenreRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]]): GR[GenreRow] = GR {
    prs =>
      import prs._
      GenreRow.tupled((<<[Int], <<[String], <<?[String]))
  }

  /** Table description of table genre. Objects of this class serve as prototypes for rows in queries. */
  class Genre(_tableTag: Tag) extends profile.api.Table[GenreRow](_tableTag, "genre") {
    def * = (id, name, description) <> (GenreRow.tupled, GenreRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), description).shaped.<>({ r => import r._; _1.map(_ => GenreRow.tupled((_1.get, _2.get, _3))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(text) */
    val name: Rep[String] = column[String]("name")
    /** Database column description SqlType(text), Default(None) */
    val description: Rep[Option[String]] = column[Option[String]]("description", O.Default(None))
  }

  /** Collection-like TableQuery object for table Genre */
  lazy val Genre = new TableQuery(tag => new Genre(tag))

  /** Entity class storing rows of table Record
    *
    * @param id           Database column id SqlType(serial), AutoInc, PrimaryKey
    * @param title        Database column title SqlType(text)
    * @param artistId     Database column artist_id SqlType(int4), Default(None)
    * @param genreId      Database column genre_id SqlType(int4), Default(None)
    * @param price        Database column price SqlType(numeric), Default(Some(0))
    * @param releasedDate Database column released_date SqlType(date)
    * @param path         Database column path SqlType(text), Default(None) */
  final case class RecordRow(id: Int, title: String, artistId: Option[Int] = None, genreId: Option[Int] = None, price: Option[scala.math.BigDecimal] = Some(scala.math.BigDecimal("0")), releasedDate: Option[java.sql.Date], path: Option[String] = None)

  /** GetResult implicit for fetching RecordRow objects using plain SQL queries */
  implicit def GetResultRecordRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[Int]], e3: GR[Option[scala.math.BigDecimal]], e4: GR[Option[java.sql.Date]], e5: GR[Option[String]]): GR[RecordRow] = GR {
    prs =>
      import prs._
      RecordRow.tupled((<<[Int], <<[String], <<?[Int], <<?[Int], <<?[scala.math.BigDecimal], <<?[java.sql.Date], <<?[String]))
  }

  /** Table description of table record. Objects of this class serve as prototypes for rows in queries. */
  class Record(_tableTag: Tag) extends profile.api.Table[RecordRow](_tableTag, "record") {
    def * = (id, title, artistId, genreId, price, releasedDate, path) <> (RecordRow.tupled, RecordRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(title), artistId, genreId, price, releasedDate, path).shaped.<>({ r => import r._; _1.map(_ => RecordRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column title SqlType(text) */
    val title: Rep[String] = column[String]("title")
    /** Database column artist_id SqlType(int4), Default(None) */
    val artistId: Rep[Option[Int]] = column[Option[Int]]("artist_id", O.Default(None))
    /** Database column genre_id SqlType(int4), Default(None) */
    val genreId: Rep[Option[Int]] = column[Option[Int]]("genre_id", O.Default(None))
    /** Database column price SqlType(numeric), Default(Some(0)) */
    val price: Rep[Option[scala.math.BigDecimal]] = column[Option[scala.math.BigDecimal]]("price", O.Default(Some(scala.math.BigDecimal("0"))))
    /** Database column released_date SqlType(date) */
    val releasedDate: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("released_date")
    /** Database column path SqlType(text), Default(None) */
    val path: Rep[Option[String]] = column[Option[String]]("path", O.Default(None))

    /** Foreign key referencing Artist (database name record_artist_id_fkey) */
    lazy val artistFk = foreignKey("record_artist_id_fkey", artistId, Artist)(r => Rep.Some(r.id), onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
    /** Foreign key referencing Genre (database name record_genre_id_fkey) */
    lazy val genreFk = foreignKey("record_genre_id_fkey", genreId, Genre)(r => Rep.Some(r.id), onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
  }

  /** Collection-like TableQuery object for table Record */
  lazy val Record = new TableQuery(tag => new Record(tag))
}
