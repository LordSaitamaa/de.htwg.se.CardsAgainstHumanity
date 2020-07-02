package model

import model.BaseImpl.{AnswerCard, GameManager, KompositumCard, Player, QuestionCard, RoundStrategy}


trait ModellInterface {

  def setPlayersAndRounds(numberPlayer: Int): ModellInterface

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

  def addCard(card: String): GameManager

  def numberOfPlayer() : Int

  def numberOfRound() : Int

  def activePlayerG() : Int

  def kompositumCardG() : KompositumCard

  def playerG(currentPlayer: Int) : Player

  def allPlayerG() : Vector[Player]

  def answerListG(): List[AnswerCard]

  def questionListG(): List[QuestionCard]

  def roundAnswerCardG(): Map[Player,String]

  def roundQuestionG() : String

  def numberOfPlayableRound(): Int

}