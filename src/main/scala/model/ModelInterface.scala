package model

import model.BaseImpl.{AnswerCard, GameManager, KompositumCard, Player, QuestionCard, RoundStrategy}
import play.api.libs.json.JsValue

trait ModelInterface {

  def setPlayersAndRounds(numberPlayer: Int): GameManager = RoundStrategy.execute(numberPlayer)

  def addPlayer(name: String): ModelInterface

  def createCardDeck(): ModelInterface

  def handOutCards(): ModelInterface

  def givePlayerCards(listOfPlayerCards: List[AnswerCard]): Vector[Player]

  def playerHand(value: List[AnswerCard]): List[AnswerCard]

  def choosePlayerStartCards(playerCount:Int): List[AnswerCard]

  def placeQuestionCard(): ModelInterface

  def placeCard(activePlayer: Int, card: AnswerCard): ModelInterface

  def getActivePlayer():Int

  def pickNextPlayer(): ModelInterface

  def drawCard(): ModelInterface

  def clearRoundAnswers(): ModelInterface

  def gameManagerG() : GameManager

  def getKomp(): KompositumCard

  def toString: String
}