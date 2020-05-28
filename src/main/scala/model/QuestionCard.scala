package model

case class QuestionCard(question:String) extends Card {
  override def printCard: Unit = {
    println("Meine Frage ist: " + question)
  }

  def getCard(): String = {question}
  override def addNewCard(card: Card): KompositumCard = {throw new UnsupportedOperationException}
  override def removeCard(card: Card): KompositumCard = {throw new UnsupportedOperationException}
}
