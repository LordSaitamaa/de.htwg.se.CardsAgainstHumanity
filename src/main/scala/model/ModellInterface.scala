package model

import model.BaseImpl.{AnswerCard, GameManager, KompositumCard, Player, QuestionCard, RoundStrategy}

trait ModellInterface {

  def setPlayersAndRounds(numberPlayer: Int): GameManager = RoundStrategy.execute(numberPlayer)

  def addPlayer(name: String): ModellInterface

  def createCardDeck(): ModellInterface

  def handOutCards(): ModellInterface

  def givePlayerCards(listOfPlayerCards: List[AnswerCard]): Vector[Player]

  def playerHand(value: List[AnswerCard]): List[AnswerCard]

  def choosePlayerStartCards(playerCount:Int): List[AnswerCard]

  def placeQuestionCard(): ModellInterface

  def placeCard(activePlayer: Int, card: AnswerCard): ModellInterface

  def getActivePlayer():Int

  def pickNextPlayer(): ModellInterface

  def drawCard(): ModellInterface

  def clearRoundAnswers(): ModellInterface

  def toString: String
}