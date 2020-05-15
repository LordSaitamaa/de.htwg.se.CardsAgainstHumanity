package model

class QuestionCard(question:String, isActive:Boolean) extends KompositumCard {
  override def printCard: Unit = {
    println("Meine Frage ist: " + question + " ; Noch im spiel vorhanden: " + isActive)
  }
}
