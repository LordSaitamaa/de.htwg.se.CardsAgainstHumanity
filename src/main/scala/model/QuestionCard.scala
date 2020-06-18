package model

case class QuestionCard(question:String) extends Card {

  override def printCard: Unit = {println("Meine Frage ist: " + question)}
  override def toString: String = question
  override def addNewCard(card: Card): KompositumCard = {null}
  override def removeCard(card: Card): KompositumCard = {null}
}
