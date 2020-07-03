package model.BaseImpl

trait  Card {
  def printCard
  def addNewCard(card:Card) : KompositumCard
  def removeCard(card:Card) : KompositumCard
  def toXML()
}

case class KompositumCard(cardList:List[Card]) extends Card {

  override def printCard = {
    cardList.foreach((c:Card)=>{
      c.printCard
    })
    print(")\n")
  }

  override def addNewCard(card: Card): KompositumCard = {
    val immutableList = cardList :+ card
    copy(immutableList)
  }

  override def removeCard(card:Card): KompositumCard = {copy(cardList.filterNot(_ == card))}

  override def toXML(): Unit = {}
}

