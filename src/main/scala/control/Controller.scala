package control

import model.{AnswerCard, KompositumCard, Player, QuestionCard, SetupGame, StandardCards}
import utils.Observable

class Controller(var setupGame: SetupGame) extends Observable {

  var activePlayer = 0
  var first = List[AnswerCard]()
  var second = List[AnswerCard]()
  var round = 0;
  // var roundlimit = 4;


  // Init Card Deck with all Standard Cards and User Added Cards
  def initCardDeck(standardCards: StandardCards, kompositumCard: KompositumCard, player: Vector[Player]): Unit = {
    val answerList = List[AnswerCard]()
    val questionList = List[QuestionCard]()
    setupGame = SetupGame(standardCards, player, answerList, questionList, null, null) // Alle Antwortkarten nicht bekannt, die aktuelle Frage auch nicht
    setupGame = setupGame.createCardDeck(kompositumCard)
    notifyObservers
  }

  def handOutCards(): Unit = {
    setupGame = setupGame.handOutCards()
    notifyObservers
  }

  def question(): Unit = {
    setupGame = setupGame.placeQuestionCard()
    notifyObservers
  }

  def put(cardIndex: Int): Unit = {
    val answerCard = setupGame.player(activePlayer).playerCards(cardIndex)
    setupGame = setupGame.placeCard(activePlayer, answerCard)
    activePlayer = (activePlayer + 1) % setupGame.player.length
    notifyObservers
  }

}