package model

case class AnswerCard(antwort:String) extends Card {
  override def printCard = {
    println("Meine Antwort ist: " + antwort)
  }

  def getCard(): String = {antwort}
  override def addNewCard(card: Card): KompositumCard = {throw new UnsupportedOperationException}
  override def removeCard(card: Card): KompositumCard = {throw new UnsupportedOperationException}

}
