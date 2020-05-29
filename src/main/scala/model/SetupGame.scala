package model

import scala.collection.mutable.ListBuffer

case class SetupGame(standardCards: StandardCards, player: Vector[Player]
                     , answerList: List[AnswerCard], questionList: List[QuestionCard]
                     , roundAnswerCards: Map[Player, String], var roundQuestion: String) {

  def createCardDeck(kompositumCard: KompositumCard): SetupGame = {
    val tmpAList = ListBuffer[AnswerCard]()
    val tmpQList = ListBuffer[QuestionCard]()
    for (answer <- standardCards.standardAnswer) tmpAList.addOne(AnswerCard(answer))
    for (questions <- standardCards.standardQuestions) tmpQList.addOne(QuestionCard(questions))
    for (x <- kompositumCard.cardList) {
      x match {
        case _: AnswerCard => tmpAList.addOne(x.asInstanceOf[AnswerCard])
        case _: QuestionCard => tmpQList.addOne(x.asInstanceOf[QuestionCard])
        case _ => println("Keine Zulässige Karte")
      }
    }
    val tmpImutA = List.empty ++ tmpAList
    val tmpImutB = List.empty ++ tmpQList
    copy(standardCards, player, tmpImutA, tmpImutB)
  }

  def handOutCards(): SetupGame = {
    var cardcount = 0
    val maxCardOnHand = 7
    var immutable = List[AnswerCard]()
    immutable = immutable ++ answerList
    var tmpPlayerVecList = Vector[Player]()

    for (x <- player) {
      var tmpList = List[AnswerCard]()
      while (cardcount < maxCardOnHand) {
        tmpList = tmpList :+ immutable(cardcount)
        immutable = immutable.filterNot(_ == immutable(cardcount))
        cardcount = cardcount + 1
      }
      tmpPlayerVecList = tmpPlayerVecList :+ Player(x.name, true, tmpList)
      cardcount = 0
    }
    copy(standardCards, tmpPlayerVecList, immutable, questionList, roundAnswerCards, roundQuestion)
  }

  def placeQuestionCard(): SetupGame = {
    var removedQuestList = List[QuestionCard]()
    removedQuestList = removedQuestList ++ questionList
    val max = questionList.length
    val rnd = scala.util.Random
    val quest = questionList(rnd.nextInt(max))
    removedQuestList = removedQuestList.filterNot(_ == quest)
    copy(standardCards, player, answerList, removedQuestList, roundAnswerCards, quest.question)
  }

  def placeCard(activePlayer: Int, card: AnswerCard ): SetupGame = {
    // Hilfsvar für die aktuell gelegten Karten und der neuen Spieler Hand
    var tmpPlacedCardMap = Map[Player, String]()

    if (roundAnswerCards != null)
      tmpPlacedCardMap ++= roundAnswerCards

    var newPlayerHand = List[AnswerCard]()
    newPlayerHand ++= player(activePlayer).playerCards
    // Füge der der Map der aktuell aktiven karten die Übergebene Karte hinzu
    tmpPlacedCardMap += (player(activePlayer) -> card.antwort)

    // Aktualisiere die Spielerhand
    newPlayerHand = newPlayerHand.filterNot(_ == card)

    // Aktualisieren des Spielers
    var tmpPlayerVecList: Vector[Player] = Vector()
    tmpPlayerVecList = tmpPlayerVecList ++ player
    tmpPlayerVecList = tmpPlayerVecList.updated(activePlayer, Player(player(activePlayer).name, true, newPlayerHand))
    copy(standardCards, tmpPlayerVecList, answerList, questionList, tmpPlacedCardMap, roundQuestion)
  }

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
    } else {
      sb ++= "nix"
    }
    sb.toString()
  }
}