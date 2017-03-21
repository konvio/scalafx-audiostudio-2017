CREATE TABLE artist (
  id              SERIAL PRIMARY KEY,
  name            TEXT NOT NULL,
  registered_date DATE DEFAULT CURRENT_DATE
);

CREATE TABLE genre (
  id          SERIAL PRIMARY KEY,
  name        TEXT NOT NULL,
  description TEXT
);

CREATE TABLE record (
  id            SERIAL PRIMARY KEY,
  title         TEXT NOT NULL,
  artist_id     INTEGER REFERENCES artist ON DELETE CASCADE,
  genre_id      INTEGER REFERENCES genre ON DELETE SET NULL,
  price         NUMERIC DEFAULT 0,
  released_date DATE    DEFAULT CURRENT_DATE,
  path          TEXT
);

CREATE TABLE album (
  id            SERIAL PRIMARY KEY,
  title         TEXT NOT NULL,
  price         NUMERIC DEFAULT 0,
  released_date DATE    DEFAULT CURRENT_DATE
);

CREATE TABLE album_record (
  id           SERIAL PRIMARY KEY,
  record_id    INTEGER REFERENCES record ON DELETE CASCADE ,
  album_id     INTEGER REFERENCES album ON DELETE CASCADE ,
  track_number INTEGER DEFAULT 0,
  UNIQUE (record_id, album_id)
);




