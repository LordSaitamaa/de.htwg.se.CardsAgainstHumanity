package model

trait  Card {
  def printCard
  def addNewCard(card:Card) : KompositumCard
  def removeCard(card:Card) : KompositumCard
}

case class KompositumCard(var cardList:List[Card]) extends Card {
  override def printCard = {
    cardList.foreach((c:Card)=>{
      c.printCard
    })
    print(")\n")
  }

  override def addNewCard(card: Card): KompositumCard = {
   cardList = cardList :+ card
    copy(cardList)
  }

  override def removeCard(card:Card): KompositumCard = {copy(cardList.filterNot(_ == card))}



}

