package model.BaseImpl

import scala.xml.Node


trait  Card {
  def printCard
  def addNewCard(card:Card) : KompositumCard
  def removeCard(card:Card) : KompositumCard
  def toXML(): Node
}

case class KompositumCard(cardList:List[Card]) extends Card {

  override def printCard = {
    cardList.foreach((c:Card)=>{
      c.printCard
    })
    print(")\n")
  }

  def addNewCards(cards : List[String]) : KompositumCard = {

    var list: List[Card] = this.cardList
    for(x <- cards){

      if(x.contains("_")) {
        list = list :+ QuestionCard(x)
      } else {
        list = list :+ AnswerCard(x)
      }
    }
      copy(cardList = list)
  }

  override def addNewCard(card: Card): KompositumCard = {
    val immutableList = cardList :+ card
    copy(immutableList)
  }

  override def removeCard(card:Card): KompositumCard = {copy(cardList.filterNot(_ == card))}

  override def toXML(): Node = {<ERROR></ERROR>}
}

