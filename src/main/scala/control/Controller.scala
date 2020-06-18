package control

import model.{GameManager}
import utils.Observable

class Controller(var gameManager: GameManager) extends Observable {

  var state: ControllerState = PreSetupState(this)

  def setUpPlayer(playerCount: String): Unit = state.evaluate(playerCount)
  def nextState(): Unit = state = state.nextState

  def eval(input: String): Unit = {
    state.evaluate(input)
    notifyObservers
  }
  def stateAsString(): String = {
    state match{
      case _: PreSetupState => "PreSetupGame"
      case _: SetupState => "SetupGame"
      case _: AnswerState => "AnswerState"
      case _: QuestionState => "QuestionState"
    }
  }
  def getCurrentStateAsString(): String = state.getCurrentStateAsString

  def handOutCards(): Unit = {
    gameManager = gameManager.createCardDeck()
    gameManager = gameManager.handOutCards()
    notifyObservers
  }

  def question(): Unit = {
    gameManager = gameManager.placeQuestionCard()
    notifyObservers
  }


  trait ControllerState {
    def evaluate(input: String): Unit

    def getCurrentStateAsString: String

    def nextState: ControllerState
  }

  case class PreSetupState(controller: Controller) extends ControllerState {
    override def evaluate(input: String): Unit = {
      if(input.toInt > 4 || input.toInt < 2) getCurrentStateAsString
      else{
        controller.gameManager = controller.gameManager.setPlayersAndRounds(input.toInt)
        controller.nextState()
      }
    }

    override def getCurrentStateAsString: String = "Willkommen bei Cards Against Humanity \n Bitte eine Spielerzahl zwischen 2 und 4 eingeben"
    override def nextState: ControllerState = SetupState(controller)
  }

  case class SetupState(controller: Controller) extends ControllerState {
    override def evaluate(input: String): Unit = {
      if(input.isEmpty) return
      controller.gameManager = controller.gameManager.addPlayer(input)
      if(controller.gameManager.player.length == controller.gameManager.numberOfPlayers){
          controller.gameManager = controller.gameManager.createCardDeck()
          controller.gameManager = controller.gameManager.handOutCards()
        controller.nextState()
      }
    }

    override def getCurrentStateAsString: String = "Spieleranzahl: " + gameManager.numberOfPlayers + "Übrige Karten: " +gameManager.answerList.toString()

    override def nextState: ControllerState = QuestionState(controller)
  }
  case class QuestionState(controller: Controller) extends ControllerState {
    override def evaluate(input: String): Unit = {
      println("Question: "  + controller.gameManager.questionList.toString())
      controller.gameManager = controller.gameManager.placeQuestionCard()
      controller.nextState()
    }
    override def getCurrentStateAsString: String = "Die Frage: " + controller.gameManager.roundQuestion + " In Runde: " +controller.gameManager.numberOfRounds

    override def nextState: ControllerState = AnswerState(controller)
  }

  case class AnswerState(controller: Controller) extends ControllerState {

    override def evaluate(input: String): Unit = {
      val activePlayer = controller.gameManager.activePlayer
      if(input.toInt > 0 && input.toInt < gameManager.player(activePlayer).playerCards.length) {
        controller.gameManager = controller.gameManager.placeCard(activePlayer,controller.gameManager.player(activePlayer).playerCards(input.toInt))
        controller.gameManager = controller.gameManager.pickNextPlayer()
      } else print("Index zwischen 0 und 6 auswählen")

      if(controller.gameManager.roundAnswerCards.size == controller.gameManager.player.size){
        controller.gameManager = controller.gameManager.pickNextPlayer()
        controller.gameManager = controller.gameManager.drawCard()
        println("Die gelegten Karten sind: " + controller.gameManager.roundAnswerCards.toString())
        controller.gameManager = controller.gameManager.clearRoundAnswers()
        controller.nextState()
      }
    }
    override def getCurrentStateAsString: String = "Index zwischen 0 und 6 um eine Karte zu legen"

    override def nextState: ControllerState = {
      if(controller.gameManager.numberOfRounds <= controller.gameManager.numberOfPlayableRounds)
        QuestionState(controller)
      else
        FinishState(controller)
    }
  }
  case class FinishState(controller: Controller) extends ControllerState {
    override def evaluate(input: String): Unit = {}
    override def getCurrentStateAsString: String = "Please press q to quit"

    override def nextState: ControllerState = this
  }
}