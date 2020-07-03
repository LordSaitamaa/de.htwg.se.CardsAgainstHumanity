package model.fileIoComponent.fileIoXmlImpl

package de.htwg.se.CardsAgainstHumanity.model.fileIoComponent.fileIoJsonImpl

import java.io.{File, PrintWriter}

import model.BaseImpl.{AnswerCard, Card, GameManager, KompositumCard, QuestionCard}
import model.ModelInterface
import model.fileIoComponent.FileIOInterface

import scala.io.Source
import scala.xml.{Elem, Node}

class FileIO extends FileIOInterface {

  override def load(gameManager: ModelInterface): ModelInterface = {
    val file = scala.xml.XML.loadFile("cards.xml")
    val nodeSeq = file \ "text"
    var list = List[Card]()

    for(x <- nodeSeq){

      if(x.isInstanceOf[AnswerCard]) {
        list = list :+ AnswerCard(x.text)
      } else if (x.isInstanceOf[QuestionCard]) {
        list = list :+ QuestionCard(x.text)
      }
    }

    var gameMan: ModelInterface = gameManager;
    var kompCards = KompositumCard(list)
    gameMan.setKompositum(kompCards)
  }

  override def save(gameMan: ModelInterface): Unit = {
    val pw = new PrintWriter(new File("CardStack"))
    val cards = <CardStack>{gameMan.getKompositum().cardList.map(p => p.toXML())}</CardStack>
    pw.write(cards.toString())

  }
}