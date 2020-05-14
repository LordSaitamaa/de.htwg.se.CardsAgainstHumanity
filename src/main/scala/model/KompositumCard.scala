package model
import scala.collection.mutable.ListBuffer

class KompositumCard extends Card {
  private var userAddedCards = ListBuffer[Card]()
  override def printCard = {
    userAddedCards.foreach((c:Card)=>{
      c.printCard
    })
    print(")")
  }

  override def addNewCard(card: Card): Unit ={
    userAddedCards.addOne(card)
  }

  override def removeCard(card:Card): Unit ={
    userAddedCards -= card
  }

  override def getAllAddedCards(): ListBuffer[Card] ={ userAddedCards}
}

