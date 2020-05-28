package control
import model.{AnswerCard, KompositumCard, Player, SetupGame, StandardCards}
import utils.Observable

class Controller(var setupGame:SetupGame) extends Observable{

  var activePlayer = 0
  var first = List[AnswerCard]()
  var second = List[AnswerCard]()


  // Init Card Deck with all Standard Cards and User Added Cards
  def initCardDeck(standardCards: StandardCards, kompositumCard: KompositumCard,player:Vector[Player]): Unit ={
    setupGame = SetupGame(standardCards, kompositumCard, player)
    setupGame = setupGame.createCardDeck()
    notifyObservers
  }

  def handOutCards(): Unit ={
    setupGame = setupGame.handOutCards()
    println("Player 1 Cards" + setupGame.player(0).playerCards)
    println("Player 2 Cards" + setupGame.player(1).playerCards)
    notifyObservers
  }

  def question(): String = {
    val quest = setupGame.placeQuestionCard()
    notifyObservers
    quest
  }

  def put(cardIndex: Int):String = {
    val answerCard = setupGame.player(activePlayer).playerCards(cardIndex)
    val ret = setupGame.placeCard(answerCard)
    ret
  }
}
