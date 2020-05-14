package model

class QuestionCard(question:String, isActive:Boolean) extends KompositumCard {
  override def printCard: Unit = {
    print(question + "Is active: " + isActive)
  }
}
