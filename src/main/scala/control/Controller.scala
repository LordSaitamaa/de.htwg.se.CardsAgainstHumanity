package control

import model.GameManager
import utils.{Observable, UndoManager}

class Controller(var gameManager: GameManager) extends Observable {

  var state: ControllerState = PreSetupState(this)
  val undoManager = new UndoManager

  def nextState(): Unit = state = state.nextState

  def eval(input: String): Unit = {
    state.evaluate(input)
    notifyObservers
  }

  def stateAsString(): String = {
    state match {
      case _: PreSetupState => "PreSetupGame"
      case _: SetupState => "SetupGame"
      case _: AnswerState => "AnswerState"
      case _: QuestionState => "QuestionState"
      case _: FinishState => "FinishState"
    }
  }

  def getCurrentStateAsString(): String = state.getCurrentStateAsString


  def undo: Unit = undoManager.undoStep

  def redo: Unit = undoManager.redoStep
}

trait ControllerState {
  def evaluate(input: String): Unit

  def getCurrentStateAsString: String

  def nextState: ControllerState
}

case class PreSetupState(controller: Controller) extends ControllerState {
  override def evaluate(input: String): Unit = {
    if (input.toInt > 4 || input.toInt < 2) getCurrentStateAsString
    else {
      controller.gameManager = controller.gameManager.setPlayersAndRounds(input.toInt)
      controller.nextState()
    }
  }

  override def getCurrentStateAsString: String = "Willkommen bei Cards Against Humanity \n Bitte eine Spielerzahl zwischen 2 und 4 eingeben"

  override def nextState: ControllerState = AddCardsQuest(controller)
}

case class AddCardsQuest(controller: Controller) extends ControllerState {
  override def evaluate(input: String): Unit = {

    if (input.equals("Weiter") || input.equals("weiter")) {
      controller.nextState()
    } else {
      controller.undoManager.doStep(new AddCardsCommand(input, this.controller))
      AddCardsQuest(controller)
    }
  }

  override def getCurrentStateAsString: String = "Wollen Sie Karten hinzufügen? \n" +
    "Wenn Sie fertig sind, tippen Sie [Weiter]"

  override def nextState: ControllerState = SetupState(controller)
}

case class SetupState(controller: Controller) extends ControllerState {
  override def evaluate(input: String): Unit = {
    if (input.isEmpty) return
    controller.gameManager = controller.gameManager.addPlayer(input)
    if (controller.gameManager.player.length == controller.gameManager.numberOfPlayers) {
      controller.gameManager = controller.gameManager.createCardDeck()
      controller.gameManager = controller.gameManager.handOutCards()
      controller.nextState()
    }
  }

  override def getCurrentStateAsString: String = "Spieleranzahl: " + controller.gameManager.numberOfPlayers + " Übrige Karten: " + controller.gameManager.answerList.toString()

  override def nextState: ControllerState = QuestionState(controller)
}

case class QuestionState(controller: Controller) extends ControllerState {
  override def evaluate(input: String): Unit = {
    if (controller.gameManager.numberOfRounds > controller.gameManager.numberOfPlayableRounds)
      nextState

    controller.gameManager = controller.gameManager.clearRoundAnswers()
    println("Question: " + controller.gameManager.questionList.toString())
    controller.gameManager = controller.gameManager.placeQuestionCard()
    controller.nextState()
  }

  override def getCurrentStateAsString: String = "Frage wird gestellt"

  override def nextState: ControllerState = {
    if (controller.gameManager.numberOfRounds > controller.gameManager.numberOfPlayableRounds)
      FinishState(controller)
    else
      AnswerState(controller)
  }
}

case class AnswerState(controller: Controller) extends ControllerState {
  override def evaluate(input: String): Unit = {
    val activePlayer = controller.gameManager.getActivePlayer()
    if (input.toInt > 0 && input.toInt < controller.gameManager.player(activePlayer).playerCards.length) {
      controller.gameManager = controller.gameManager.placeCard(activePlayer, controller.gameManager.player(activePlayer).playerCards(input.toInt))
      controller.gameManager = controller.gameManager.pickNextPlayer()
    } else {
      val failure = "Kein Gültiger Index"
      print(failure)
    }

    if (controller.gameManager.roundAnswerCards.size == controller.gameManager.player.size) {
      controller.gameManager = controller.gameManager.drawCard()
      controller.nextState()
    }
  }

  override def getCurrentStateAsString: String = "Antworten legen um weiter zu machen"

  override def nextState: ControllerState = QuestionState(controller)
}

case class FinishState(controller: Controller) extends ControllerState {
  override def evaluate(input: String): Unit = {}

  override def getCurrentStateAsString: String = "Please write quit to exit the game"

  override def nextState: ControllerState = this
}

