package model

class AnswerCard(antwort:String,isActive:Boolean) extends KompositumCard {
  override def printCard = {
    println(antwort + "Noch Aktiv: " + isActive)
  }
}
