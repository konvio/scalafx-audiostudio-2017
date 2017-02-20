name := "scala-audiostudio"

version := "1.0"

scalaVersion := "2.12.1"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "8.0.102-R11",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.3",
  "org.postgresql" % "postgresql" % "42.0.0.jre7",
  "com.typesafe.slick" %% "slick" % "3.2.0-RC1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0-RC1",
  "com.typesafe" % "config" % "1.3.1",
  "com.typesafe.slick" %% "slick-codegen" % "3.2.0-RC1",
  "org.slf4j" % "slf4j-nop" % "1.6.4"
)

