package model

import scala.collection.mutable.ListBuffer

case class SetupGame(standardCards: StandardCards, player: Vector[Player]
                     , answerList: List[AnswerCard], questionList: List[QuestionCard]
                     , roundAnswerCards: Map[Player, String], roundQuestion: String) {
3
  def createCardDeck(kompositumCard: KompositumCard): SetupGame = {
    val tmpAnswerList = ListBuffer[AnswerCard]()
    val tmpQuestionList = ListBuffer[QuestionCard]()
    standardCards.standardAnswer.foreach(answer => tmpAnswerList += (AnswerCard(answer)))
    standardCards.standardQuestions.foreach(question => tmpQuestionList += (QuestionCard(question)))
    for (x <- kompositumCard.cardList) {
      x match {
        case _: AnswerCard => tmpAnswerList.addOne(x.asInstanceOf[AnswerCard])
        case _: QuestionCard => tmpQuestionList.addOne(x.asInstanceOf[QuestionCard])
        case _ => println("Keine Zulässige Karte")
      }
    }
    val tmpImutA = List.empty ++ tmpAnswerList
    println(tmpImutA)
    val tmpImutB = List.empty ++ tmpQuestionList
    println(tmpImutB)
    copy(standardCards, player, tmpImutA, tmpImutB)
  }

  def handOutCards(): SetupGame = {
    val maxCardOnHand = 7
    var immutable = List[AnswerCard]()
    immutable = immutable ++ answerList
    var tmpPlayerVecList = Vector[Player]()
    //var cardcount = immutable.length -1
    var cardcount = 0
    var index = immutable.length - 1

    for (x <- player) {
      var tmpList = List[AnswerCard]()
      while (cardcount < maxCardOnHand && immutable.nonEmpty) {
        tmpList = tmpList :+ immutable(index)
        immutable = immutable.filterNot(_ == immutable(index))
        index = immutable.length - 1
        cardcount = cardcount + 1
        //cardcount = immutable.length -1
      }
      println(tmpList)
      tmpPlayerVecList = tmpPlayerVecList :+ Player(x.name, true, tmpList)
      //cardcount = immutable.length -1
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