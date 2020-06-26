package control

import model.GameManager
import utils.{Observable, UndoManager}
import control._

import scala.swing.Publisher

class Controller(var gameManager: GameManager) extends Publisher {

  var state: ControllerState = PreSetupState(this)
  val undoManager = new UndoManager

  def nextState(): Unit = state = state.nextState

  def changePage(page: Int): Unit = {
    page match{
      case 1 => publish(new StartPageEvent)
      case 2 => publish(new SecondPageEvent)
      case 3 => publish(new ThirdPageEvent)
    }
  }
  /*
  def updateTui(): Unit = {
    publish(new UpdateTuiEvent)
  }
  */

  def eval(input: String): Unit = {
    state.evaluate(input)
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


  def undo: Unit = {
    undoManager.undoStep
    publish(new UndoEvent)
  }

  def redo: Unit = {
    undoManager.redoStep
    publish(new UndoEvent)
  }
}

trait ControllerState {

  def evaluate(input: String): Unit

  def getCurrentStateAsString: String

  def nextState: ControllerState
}

case class PreSetupState(controller: Controller) extends ControllerState {
  override def evaluate(input: String): Unit = {
    println("PreSetupState")
    if (input.toInt > 4 || input.toInt < 2) getCurrentStateAsString
    else {
      controller.gameManager = controller.gameManager.setPlayersAndRounds(input.toInt)
      controller.changePage(2)
      controller.nextState()
    }
  }

  override def getCurrentStateAsString: String = "Willkommen bei Cards Against Humanity \n"

  override def nextState: ControllerState = AddCardsQuest(controller)
}

case class AddCardsQuest(controller: Controller) extends ControllerState {
  override def evaluate(input: String): Unit = {
    println("AddCardsQuest")
    if (input.equals("Weiter") || input.equals("weiter")) {
      controller.nextState()
      controller.publish(new ThirdPageEvent)
    } else {
      controller.undoManager.doStep(new AddCardsCommand(input, this.controller))
      controller.publish(new UpdateInfoBarEvent)
    }
  }

  override def getCurrentStateAsString: String = "Wollen Sie Karten hinzufügen? \n" +
    "Wenn Sie fertig sind, klicken Sie auf [Weiter]"

  override def nextState: ControllerState = SetupState(controller)
}

case class SetupState(controller: Controller) extends ControllerState {
  override def evaluate(input: String): Unit = {

    if (input.isEmpty) return

    controller.undoManager.doStep(new AddPlayersCommand(input, controller))
    controller.publish(new UpdateGuiEvent)

    //controller.gameManager = controller.gameManager.addPlayer(input)
    if (controller.gameManager.player.length == controller.gameManager.numberOfPlayers) {
      controller.gameManager = controller.gameManager.createCardDeck()
      controller.gameManager = controller.gameManager.handOutCards()
      controller.nextState()
      controller.publish(new NextStateEvent)

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
    controller.gameManager = controller.gameManager.placeQuestionCard()
    controller.publish(new UpdateInfoBarEvent)
    controller.publish(new UpdateGuiEvent)
    controller.nextState()
  }

  override def getCurrentStateAsString: String = controller.gameManager.roundQuestion

  override def nextState: ControllerState = {
    if (controller.gameManager.numberOfRounds > controller.gameManager.numberOfPlayableRounds) {
      //controller.updateTui()
      FinishState(controller)
    }
    else {
      //controller.updateTui()
      AnswerState(controller)
    }
  }
}

case class AnswerState(controller: Controller) extends ControllerState {

  print("Hi")

  override def evaluate(input: String): Unit = {

    val activePlayer = controller.gameManager.getActivePlayer()
    if (input.toInt > 0 && input.toInt < controller.gameManager.player(activePlayer).playerCards.length) {
      controller.gameManager = controller.gameManager.placeCard(activePlayer, controller.gameManager.player(activePlayer).playerCards(input.toInt))
      controller.gameManager = controller.gameManager.pickNextPlayer()
      controller.publish(new UpdateGuiEvent)
    } else {
      val failure = "Kein Gültiger Index"
      print(failure)
    }

    if (controller.gameManager.roundAnswerCards.size == controller.gameManager.player.size) {
      controller.gameManager = controller.gameManager.drawCard()
      controller.publish(new UpdateGuiEvent)
      controller.nextState()
    }
  }

  override def getCurrentStateAsString: String = "Antworten legen um weiter zu machen"

  override def nextState: ControllerState = QuestionState(controller)
}

case class FinishState(controller: Controller) extends ControllerState {
  override def evaluate(input: String): Unit = {println("FinishState")}

  override def getCurrentStateAsString: String = "Please write quit to exit the game"

  override def nextState: ControllerState = this
}

