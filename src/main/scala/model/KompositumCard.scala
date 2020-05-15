package model
import scala.collection.mutable.ListBuffer

trait Card {
  def printCard
  def addNewCard(card:Card)
  def removeCard(card:Card)
  def getAllAddedCards: ListBuffer[Card]
}

class KompositumCard() extends Card {
  private var userAddedCards = ListBuffer[Card]()
  override def printCard = {
    userAddedCards.foreach((c:Card)=>{
      c.printCard
    })
    print(")\n")
  }
  override def addNewCard(card: Card): Unit ={userAddedCards.addOne(card)}

  override def removeCard(card:Card): Unit ={userAddedCards -= card}

  override def getAllAddedCards(): ListBuffer[Card] ={userAddedCards}
}

