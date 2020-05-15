package model

class AnswerCard(antwort:String,isActive:Boolean) extends KompositumCard {
  override def printCard = {
    println("Meine Antwort ist: " + antwort + " ; Noch im spiel vorhanden: " + isActive)
  }
}
