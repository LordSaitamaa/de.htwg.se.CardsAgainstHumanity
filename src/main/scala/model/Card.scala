package model

import scala.collection.mutable.ListBuffer

trait Card {
  def printCard
  def addNewCard(card:Card)
  def removeCard(card:Card)
  def getAllAddedCards: ListBuffer[Card]
}
