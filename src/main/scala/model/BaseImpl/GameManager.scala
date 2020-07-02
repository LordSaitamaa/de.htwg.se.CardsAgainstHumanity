package model.BaseImpl

import model._
import model.ModellInterface
import model.BaseImpl.Card

import scala.util.Random

case class GameManager(numberOfPlayers: Int = 0,
                       numberOfPlayableRounds: Int = 0,
                       numberOfRounds: Int = 0,
                       activePlayer: Int = 0,
                       kompositumCard: KompositumCard = CardStack.initialize,
                       var player: Vector[Player] = Vector[Player](),
                       var answerList: List[AnswerCard] = List[AnswerCard](),
                       var questionList: List[QuestionCard] = List[QuestionCard](),
                       roundAnswerCards: Map[Player, String] = Map[Player,String](),
                       roundQuestion: String = "") extends ModellInterface {

  override def numberOfPlayer() : Int = {numberOfPlayers}
  override def numberOfRound() : Int = numberOfRounds
  override def activePlayerG() : Int = activePlayer
  override def kompositumCardG() : KompositumCard = kompositumCard
  override def playerG(currentPlayer: Int) : Player = player(currentPlayer)
  def allPlayerG() : Vector[Player] = player
  override def answerListG(): List[AnswerCard] = answerList
  override def questionListG(): List[QuestionCard] = questionList
  override def roundAnswerCardG(): Map[Player,String] = roundAnswerCards
  override def roundQuestionG() : String = roundQuestion
  override def numberOfPlayableRound(): Int = numberOfPlayableRounds

  override def setPlayersAndRounds(numberPlayer: Int): GameManager = RoundStrategy.execute(numberPlayer)

  override def addPlayer(name: String): GameManager = {
    var playerTmp = player
    playerTmp = playerTmp :+ Player(name, true, List[AnswerCard]())
    copy(player = playerTmp)
  }

  override def addCard(card: String): GameManager = {
    if(card.contains("_")){
      var tmpQuestionList = questionList
      tmpQuestionList = tmpQuestionList :+ QuestionCard(card)
      copy(questionList = tmpQuestionList)
    } else {
      var tmpAnswerList = answerList
      tmpAnswerList = tmpAnswerList :+ AnswerCard(card)
      copy(answerList = tmpAnswerList)
    }
  }

  override def createCardDeck(): GameManager = {
    var tmpAnswerList = answerList
    var tmpQuestionList = questionList
    for (x <- kompositumCard.cardList) {
      x match {
        case _: QuestionCard => tmpQuestionList = tmpQuestionList :+ (x.asInstanceOf[QuestionCard])
        case _: AnswerCard => tmpAnswerList = tmpAnswerList :+ (x.asInstanceOf[AnswerCard])
      }

    }
    copy(answerList = tmpAnswerList, questionList = tmpQuestionList)
  }

  override def handOutCards(): GameManager = {
    val playerCard = choosePlayerStartCards(numberOfPlayers)
    var remainingCards = answerList
    playerCard.foreach(remove => remainingCards = remainingCards.filterNot(_ == remove))
    val tmpPlayerVecList = givePlayerCards(playerCard)
    copy(player = tmpPlayerVecList, answerList = remainingCards)
  }

  override def givePlayerCards(listOfPlayerCards: List[AnswerCard]): Vector[Player] = {
    var tmpPlayerCards = listOfPlayerCards
    var tmpPlayer = Vector[Player]()
    for(x <- player; if tmpPlayerCards.nonEmpty){
      val playersHand = playerHand(tmpPlayerCards)
      for(removeCards <- playersHand.indices)yield tmpPlayerCards = tmpPlayerCards.filterNot(_ == playersHand(removeCards))
      tmpPlayer = tmpPlayer :+ Player(x.name,x.isAnswering,playersHand)
    }
    tmpPlayer
  }

  override def playerHand(value: List[AnswerCard]): List[AnswerCard] = {
    var cardcount = 0
    var remCard = List[AnswerCard]()
    for(x <- value;if cardcount < 7)yield {remCard = remCard :+ x;cardcount += 1}
    remCard
  }
  override def choosePlayerStartCards(playerCount:Int): List[AnswerCard] = {
    val tmpAnswerList = Random.shuffle(answerList)
    var givenCards = List[AnswerCard]()
    var count = 0
    for (answer <- tmpAnswerList if tmpAnswerList.nonEmpty; if count < 7 * playerCount) {
      givenCards = givenCards :+ answer
      count += 1}
    givenCards
  }

  override def placeQuestionCard(): GameManager = {
    var removedQuestList = questionList
    removedQuestList = Random.shuffle(removedQuestList)
    val quest = removedQuestList.head
    var tmpRounds = numberOfRounds
    tmpRounds = tmpRounds +1
    copy(questionList = removedQuestList.filterNot(_ == quest), roundQuestion = quest.question, numberOfRounds = tmpRounds)
  }

  override def placeCard(activePlayer: Int, card: AnswerCard): GameManager = {
    var tmpPlacedCardMap = Map[Player, String]()

    if (roundAnswerCards != null)
      tmpPlacedCardMap = roundAnswerCards

    var newPlayerHand = player(activePlayer).playerCards

    val questTmp = roundQuestion
    tmpPlacedCardMap += (player(activePlayer) -> questTmp.replace("_",card.antwort))
    newPlayerHand = newPlayerHand.filterNot(_ == card)

    var tmpPlayerVecList = player
    tmpPlayerVecList = tmpPlayerVecList.updated(activePlayer, Player(player(activePlayer).name, true, newPlayerHand))

    copy(player = tmpPlayerVecList,roundAnswerCards = tmpPlacedCardMap)
  }

  override def getActivePlayer():Int = activePlayer

  override def pickNextPlayer(): GameManager = copy(activePlayer = (activePlayer + 1) % player.length)

  override def drawCard(): GameManager = {
    var answerTmp = answerList
    answerTmp = Random.shuffle(answerTmp)
    var tmpPlayerVec = Vector[Player]()

    for(x <- player) {
      val r = Random.nextInt(answerTmp.length)
      val result = answerTmp(r)
      answerTmp = answerTmp.filterNot(_ == result)
      var playerHand = x.playerCards
      playerHand = playerHand :+ result
      tmpPlayerVec = tmpPlayerVec :+ Player(x.name,x.isAnswering,playerHand )

    }
    copy(answerList=answerTmp,player = tmpPlayerVec)
  }

  override def clearRoundAnswers(): GameManager = copy(roundAnswerCards = Map[Player,String]())

  override def toString: String = {
    var sb = new StringBuilder
      sb ++= "Aktive Antwort Karten: " + answerList.toString() + "\n"
      sb ++= "Aktive Frage Karten: " + questionList.toString() + "\n"
      sb ++= "Aktuelle Frage Karte: " + roundQuestion + "\n"
      sb ++= "Gelegten Antwort Karten: " + roundAnswerCards.toString() + "\n"
      for (i <- player.indices) {
        sb ++= "Die karten des Spielers: "+ player(i).name + "  Seine Karten:"+ player(i).playerCards + "\n"
      }

    sb.toString()
  }
}

object GameManager{

  case class Builder(){
    var numberOfPlayer: Int = 0
    var numberOfPlayableRounds: Int = 0

    def withNumberOfPlayer(players: Int): Builder = {
      numberOfPlayer = players
      this
    }

    def withNumberOfRounds(rounds: Int): Builder = {
      numberOfPlayableRounds = rounds
      this
    }

    def build(): GameManager = {
      GameManager(numberOfPlayer,numberOfPlayableRounds)
    }
  }
}