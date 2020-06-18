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
      case _: InGameState => "InGame"
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
      if(input.toInt == 0 || input.toInt > 4 || input.toInt < 2) getCurrentStateAsString
      else{
        controller.gameManager = controller.gameManager.setPlayersAndRounds(input.toInt)
        controller.nextState()
      }
    }

    override def getCurrentStateAsString: String = "Willkommen bei Cards Against Humanity \n Bitte eine Spielerzahl zwischen 2 und 4 eingeben"
    override def nextState: ControllerState = AddCardsQuest(controller)
  }

  case class AddCardsQuest(controller: Controller) extends ControllerState {
    override def evaluate(input: String): Unit = {

      if(input.equals("Weiter") ||input.equals("weiter")) {
        controller.nextState()
      } else {
        controller.gameManager = controller.gameManager.addCardToStack(input)
        AddCardsQuest(controller)
      }
    }

    override def getCurrentStateAsString: String = "Wollen Sie Karten hinzufügen? \n" +
      "Wenn Sie fertig sind, tippen Sie [Weiter]"

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

    override def nextState: ControllerState = InGameState(controller)
  }

  case class InGameState(controller: Controller) extends ControllerState {

    override def evaluate(input: String): Unit = {
      if(input.toInt > 0 && input.toInt < 7) {
        val activePlayer = controller.gameManager.activePlayer
        controller.gameManager = controller.gameManager.placeCard(activePlayer,controller.gameManager.player(activePlayer).playerCards(input.toInt))
      }

    }
    override def getCurrentStateAsString: String = "InGame!"



    override def nextState: ControllerState = SetupState(controller)
  }
}