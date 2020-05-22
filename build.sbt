import sbt.Keys.libraryDependencies

enablePlugins(SbtPlugin)
name := "de.htwg.se.CardsAgainstHumanity"
version := "0.1.0"
scalaVersion := "2.13.2"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")
addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.2.7")


