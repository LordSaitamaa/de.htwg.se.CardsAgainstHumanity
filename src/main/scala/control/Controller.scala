package control
import model.{AnswerCard, KompositumCard, Player, QuestionCard, SetupGame, StandardCards}
import utils.Observable

class Controller(var setupGame:SetupGame) extends Observable{

  var activePlayer = 0
  var first = List[AnswerCard]()
  var second = List[AnswerCard]()


  // Init Card Deck with all Standard Cards and User Added Cards
  def initCardDeck(standardCards: StandardCards, kompositumCard: KompositumCard,player:Vector[Player]): Unit ={
    val answerList = List[AnswerCard]()
    val questionList = List[QuestionCard]()
    setupGame = SetupGame(standardCards, kompositumCard, player, answerList, questionList, null, null) // Alle Antwortkarten nicht bekannt, die aktuelle Frage auch nicht
    setupGame = setupGame.createCardDeck()
    notifyObservers
  }

  def handOutCards():Unit ={
    setupGame = setupGame.handOutCards()
    //println("Player 1 Cards" + setupGame.player(0).playerCards)
    //println("Player 2 Cards" + setupGame.player(1).playerCards)
    notifyObservers

    var s = "Player 1 Cards" + setupGame.player(0).playerCards + "\n" + "Player 2 Cards " +
      setupGame.player(1).playerCards + "\n"
    s
  }

  def question():Unit = {
    val quest = setupGame.placeQuestionCard()
    setupGame = setupGame.copy(setupGame.standardCards, setupGame.kompositumCard, setupGame.player, setupGame.answerList, setupGame.questionList, setupGame.roundAnswerCards, quest)
    notifyObservers
    quest
  }

  def put(cardIndex: Int):Unit = {

    val answerCard = setupGame.player(activePlayer).playerCards(cardIndex)
    val ret = setupGame.placeCard(answerCard)
    //setupGame.roundQuestion.replace("_", ret)
    var s = setupGame.player(activePlayer).getName + " hat folgende Karte gelegt: " + ret + "\n" + "Spieler " + (((activePlayer + 1)% setupGame.player.length)+1) + " ist am Zug."
    s
  }

  def showAnswers():Unit = {

  }

}