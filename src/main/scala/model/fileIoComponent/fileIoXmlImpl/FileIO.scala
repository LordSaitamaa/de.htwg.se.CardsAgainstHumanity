package model.fileIoComponent.fileIoXmlImpl

package de.htwg.se.sudoku.model.fileIoComponent.fileIoJsonImpl

import com.google.inject.Guice
import com.google.inject.name.Names
import model.ModelInterface
import net.codingwell.scalaguice.InjectorExtensions._
import model.fileIoComponent.FileIOInterface
import model.fileIoComponent.FileIOInterface
import play.api.libs.json._

import scala.io.Source

class FileIO extends FileIOInterface {

  override def load: ModelInterface = {
    var game: ModelInterface = null
     val source: String = Source.fromFile("cards.json").getLines.mkString
    val json:JsValue = Json.parse(source)
    game
  }

  override def save(game: ModelInterface): Unit = {
    var modelInterface

  }

  implicit val cellWrites = new Writes[ModelInterface] {

  }

  def gridToJson(game: ModelInterface) = {

  }



}
