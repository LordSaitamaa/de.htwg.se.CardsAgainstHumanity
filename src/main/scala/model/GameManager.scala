package model

import scala.util.Random

case class GameManager(numberOfPlayers: Int = 0,
                       numberOfPlayableRounds: Int = 0,
                       numberOfRounds: Int = 0,
                       activePlayer: Int = 0,
                       kompositumCard: KompositumCard = CardStack.initialize,
                       player: Vector[Player] = null,
                       answerList: List[AnswerCard] = Nil,
                       questionList: List[QuestionCard] = Nil,
                       roundAnswerCards: Map[Player, String] = null,
                       roundQuestion: String = "") {


  def setPlayersAndRounds(numberPlayer : Int): GameManager = RoundStrategy.execute(numberPlayer)

  def addPlayer(name:String): GameManager ={
    var playerTmp = Vector[Player]()
    if(player != null) playerTmp = player
    playerTmp = playerTmp :+ Player(name, true,List[AnswerCard]())
    print(playerTmp)
    copy(player = playerTmp)
  }

  def createCardDeck(): GameManager = {
    var tmpAnswerList = List[AnswerCard]()
    var tmpQuestionList = List[QuestionCard]()
    for (x <- kompositumCard.cardList) {
      x match {
        case _: QuestionCard => tmpQuestionList = tmpQuestionList :+ (x.asInstanceOf[QuestionCard])
        case _: AnswerCard => tmpAnswerList = tmpAnswerList :+ (x.asInstanceOf[AnswerCard])
        case _ => println("Keine Zulässige Karte")
     }
    }
    copy(answerList = tmpAnswerList, questionList = tmpQuestionList)
  }

  def handOutCards(): GameManager = {
    val playerCard = choosePlayerStartCards(numberOfPlayers)
    var remainingCards = answerList
    playerCard.foreach(remove => remainingCards = remainingCards.filterNot(_ == remove) )
    val tmpPlayerVecList = givePlayerCards(playerCard)
    copy(player = tmpPlayerVecList,  answerList= remainingCards)
  }

  def givePlayerCards(listOfPlayerCards: List[AnswerCard]): Vector[Player] = {
    var tmpPlayerCards = listOfPlayerCards
    var tmpPlayer = Vector[Player]()
    for(x <- player; if tmpPlayerCards.nonEmpty){
      val playersHand = playerHand(tmpPlayerCards)
      for(removeCards <- playersHand.indices)yield tmpPlayerCards = tmpPlayerCards.filterNot(_ == playersHand(removeCards))
      tmpPlayer = tmpPlayer :+ Player(x.name,x.isAnswering,playersHand)
    }
    tmpPlayer
  }

  def playerHand(value: List[AnswerCard]): List[AnswerCard] = {
    var cardcount = 0
    var remCard = List[AnswerCard]()
    for(x <- value;if cardcount < 7)yield {remCard = remCard :+ x;cardcount += 1}
    remCard
  }
  def choosePlayerStartCards(playerCount:Int): List[AnswerCard] = {
    val tmpAnswerList = Random.shuffle(answerList)
    var givenCards = List[AnswerCard]()
    var count = 0
    for (answer <- tmpAnswerList if tmpAnswerList.nonEmpty; if count < 7 * playerCount) {
      givenCards = givenCards :+ answer
      count += 1}
    givenCards
  }

  def placeQuestionCard(): GameManager = {
    var removedQuestList= questionList
    removedQuestList = Random.shuffle(removedQuestList)
    val quest = removedQuestList.head
    copy(questionList = removedQuestList.filterNot(_ == quest), roundQuestion = quest.question)
  }

  def placeCard(activePlayer: Int, card: AnswerCard ): GameManager = {
    var tmpPlacedCardMap = Map[Player, String]()

    if (roundAnswerCards != null)
      tmpPlacedCardMap = roundAnswerCards

    var newPlayerHand = player(activePlayer).playerCards
    tmpPlacedCardMap += (player(activePlayer) -> card.antwort)
    newPlayerHand = newPlayerHand.filterNot(_ == card)

    var tmpPlayerVecList = player
    tmpPlayerVecList = tmpPlayerVecList.updated(activePlayer, Player(player(activePlayer).name, true, newPlayerHand))
    copy(player = tmpPlayerVecList,roundAnswerCards = tmpPlacedCardMap,numberOfRounds = numberOfRounds + 1)
  }

  def getActivePlayer():Int = activePlayer

  def pickNextPlayer(): GameManager = copy(activePlayer = (activePlayer + 1) % player.length)

  def drawCard(): GameManager = {
    var answerTmp = answerList
    answerTmp = Random.shuffle(answerTmp)
    var tmpPlayerVec = Vector[Player]()

    for(x <- player) {
      val r = Random.nextInt(answerTmp.length)
      val result = answerTmp(r)
      var playerHand = x.playerCards
      playerHand = playerHand :+ result
      tmpPlayerVec = tmpPlayerVec :+ Player(x.name,x.isAnswering,playerHand )
    }
    copy(player = tmpPlayerVec)
  }

  def clearRoundAnswers(): GameManager = copy(roundAnswerCards = Map[Player,String]())

  override def toString: String = {
    var sb = new StringBuilder
    if (answerList != null && questionList != null && roundQuestion != null && roundAnswerCards != null && player.nonEmpty) {
      sb ++= "Aktive Antwort Kartem: " + answerList.toString() + "\n"
      sb ++= "Aktive Frage Karten: " + questionList.toString() + "\n"
      sb ++= "Aktuelle Frage Karte: " + roundQuestion + "\n"
      sb ++= "Gelegten Antwort Karten: " + roundAnswerCards.toString() + "\n"
      for (i <- player.indices) {
        sb ++= "Die karten der Spieler: " + player(i).playerCards + "\n"
      }
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