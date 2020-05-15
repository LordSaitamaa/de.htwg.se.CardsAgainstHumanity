package model


trait Card {
  def printCard
  def addNewCard(card:Card) : KompositumCard
  def removeCard(card:Card) : KompositumCard
  def getAllAddedCards: List[Card]
}

case class KompositumCard(userAddedCard:List[Card]) extends Card {
  override def printCard = {
    userAddedCard.foreach((c:Card)=>{
      c.printCard
    })
    print(")\n")
  }
  override def addNewCard(card: Card): KompositumCard = {copy(userAddedCard.appended(card))}

  override def removeCard(card:Card): KompositumCard = {copy(userAddedCard.filterNot(_ == card))}

  override def getAllAddedCards(): List[Card] ={userAddedCard}
}

