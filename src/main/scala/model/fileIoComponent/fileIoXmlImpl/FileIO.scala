package model.fileIoComponent.fileIoXmlImpl

package de.htwg.se.CardsAgainstHumanity.model.fileIoComponent.fileIoJsonImpl

import com.google.inject.Guice
import com.google.inject.name.Names
import control.ControllerInterface
import javax.swing.event.TableColumnModelListener
import model.BaseImpl.{GameManager, KompositumCard}
import model.ModelInterface
import net.codingwell.scalaguice.InjectorExtensions._
import model.fileIoComponent.FileIOInterface
import model.fileIoComponent.FileIOInterface
import play.api.libs.json._

import scala.io.Source
import scala.xml.Elem

class FileIO extends FileIOInterface {

  override def load(gameManager: ModelInterface): ModelInterface = {
    val file = scala.xml.XML.loadFile("cards.xml")
    var gameMan: ModelInterface = gameManager;
    var kompCards = KompositumCard(List("Hugi", "haga", "higi"))
    gameMan.setKompositum(kompCards)
  }

  override def save(gameMan: ModelInterface): Unit = {

    for(x <- gameMan.getKompositum.cardList) {
      toXml()
    }
    def toXml = {
      <stock>
          <card></card>
      </stock>
    }
    if(kompCards.isEmpty)
    val karten = kompCards
  }

  def controllerFromXml(file: Elem): ModelInterface = {
    val cards = KompositumCard
  }
  def controllerToXml()
}