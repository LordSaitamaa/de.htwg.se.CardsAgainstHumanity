package model.BaseImpl

import scala.xml.Node


case class QuestionCard(question:String) extends Card {

  override def printCard: Unit = {println("Meine Frage ist: " + question)}
  override def toString: String = question
  override def addNewCard(card: Card): KompositumCard = {null}
  override def removeCard(card: Card): KompositumCard = {null}
  override def toXML(): Node = <card><text>{question}</text></card>
}
