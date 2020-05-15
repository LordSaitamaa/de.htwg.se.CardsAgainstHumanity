package model

case class AnswerCard(antwort:String,isActive:Boolean) extends Card {
  override def printCard = {
    println("Meine Antwort ist: " + antwort + " ; Noch im spiel vorhanden: " + isActive)
  }

  override def addNewCard(card: Card): KompositumCard = {throw new UnsupportedOperationException}

  override def removeCard(card: Card): KompositumCard = {throw new UnsupportedOperationException}

}
