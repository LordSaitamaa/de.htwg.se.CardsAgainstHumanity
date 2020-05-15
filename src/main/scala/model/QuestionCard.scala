package model

case class QuestionCard(question:String, isActive:Boolean) extends Card {
  override def printCard: Unit = {
    println("Meine Frage ist: " + question + " ; Noch im spiel vorhanden: " + isActive)
  }

  override def addNewCard(card: Card): KompositumCard = {throw new UnsupportedOperationException}

  override def removeCard(card: Card): KompositumCard = {throw new UnsupportedOperationException}
}
