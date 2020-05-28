package model

import scala.collection.mutable.ListBuffer


trait  Card {
  def printCard
  def addNewCard(card:Card) : KompositumCard
  def removeCard(card:Card) : KompositumCard
}

case class KompositumCard(cardList:List[Card]) extends Card {
  override def printCard = {
    cardList.foreach((c:Card)=>{
      c.printCard
    })
    print(")\n")
  }

  override def addNewCard(card: Card): KompositumCard = {
    val mutableList = ListBuffer[Card]()
    mutableList.addAll(cardList)
    mutableList += card
    val immutableList = List.empty ++ mutableList
    copy(immutableList)
  }

  override def removeCard(card:Card): KompositumCard = {copy(cardList.filterNot(_ == card))}



}

