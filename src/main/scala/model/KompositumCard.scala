package model

import scala.collection.mutable.ListBuffer


trait  Card {
  def printCard
  def addNewCard(card:Card) : KompositumCard
  def removeCard(card:Card) : KompositumCard
}

case class KompositumCard(userAddedCard:List[Card]) extends Card {
  override def printCard = {
    userAddedCard.foreach((c:Card)=>{
      c.printCard
    })
    print(")\n")
  }
  override def addNewCard(card: Card): KompositumCard = {
    val mutableList = ListBuffer[Card]()
    mutableList.addAll(userAddedCard)
    mutableList += card
    val immutableList = List.empty ++ mutableList
    copy(immutableList)
  }

  override def removeCard(card:Card): KompositumCard = {copy(userAddedCard.filterNot(_ == card))}

}

