import sbt.Keys.libraryDependencies

name := "de.htwg.se.CardsAgainstHumanity"
version := "0.1.0"
scalaVersion := "2.13.2"


lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "de.htwg.se.CardsAgainstHumanity",
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.1",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"
  )


