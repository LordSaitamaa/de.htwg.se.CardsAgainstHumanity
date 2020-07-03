import control.BaseImpl.{AddCardsQuest, AnswerState, Controller, FinishState, PreSetupState, SetupState}
import model.BaseImpl
import model.BaseImpl.GameManager
import org.scalatest._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import utils.Observer


class ControlSpec extends AnyWordSpec with Matchers with GivenWhenThen {
  "A Controller " when {
    val gameManager = BaseImpl.GameManager()
    val controller = new Controller(gameManager)
    val observer = new Observer {
      var updated: Boolean = false
      def isUpdated: Boolean = updated
      override def update: Unit = updated = true
    }
   // controller.add(observer)
      "test Strategy"in{
        controller.gameManager = controller.gameManager.setPlayersAndRounds(2)
        controller.getGameManager.numberOfPlayers.shouldBe(2)
        controller.getGameManager.numberOfPlayableRounds.shouldBe(8)
      }

    "notify the observer after evaluation" in {
      controller.eval("3" )
      controller.state = PreSetupState(controller)
      controller.eval("4")
      controller.state = PreSetupState(controller)
      controller.eval("2")
    }

    "get the according State String" in {
      controller.state = PreSetupState(controller)
      controller.getCurrentStateAsString() shouldBe ("Willkommen bei Cards Against Humanity \n")

      controller.state = SetupState(controller)
      controller.getCurrentStateAsString() shouldBe "SetupState"

      controller.state = AddCardsQuest(controller)
      controller.getCurrentStateAsString() shouldBe "AddCardState"

      controller.state = AnswerState(controller)
      controller.getCurrentStateAsString() shouldBe  "AnswerState"

      controller.state = FinishState(controller)
      controller.getCurrentStateAsString() shouldBe "Please write quit to exit the game"
    }

    "switches the states correctly" in {
      controller.state = PreSetupState(controller)
      controller.nextState()
      controller.state shouldBe AddCardsQuest(controller)

      controller.state = AddCardsQuest(controller)
      controller.nextState()
      controller.state shouldBe SetupState(controller)

      controller.state = SetupState(controller)
      controller.nextState()
      controller.gameManager = controller.getGameManager.copy(numberOfRounds = 3)
      controller.state shouldBe (AnswerState(controller))

      controller.state = SetupState(controller)
      controller.nextState()
      controller.gameManager = controller.getGameManager.copy(numberOfRounds = 9)
      controller.state shouldBe (AnswerState(controller))
      controller.nextState()
      controller.state shouldBe FinishState(controller)

    }

    "return the correct String for every State" in {
      controller.state = PreSetupState(controller)
      controller.stateAsString() shouldBe("PreSetupGame")

      controller.state = SetupState(controller)
      controller.stateAsString() shouldBe "SetupGame"

      controller.state = AnswerState(controller)
      controller.stateAsString() shouldBe "AnswerState"

      controller.state = FinishState(controller)
      controller.stateAsString() shouldBe "FinishState"
    }

    "handout cards correctly "in{
      controller.state = SetupState(controller)
      controller.eval("Hans")
      controller.getGameManager.player(0).name shouldBe "Hans"
      controller.eval("Dirk")
      controller.getGameManager.player(1).name shouldBe "Dirk"
      controller.getGameManager.player.length shouldBe 2

      controller.getGameManager.player(0).getCards.length shouldBe(7)
      controller.getGameManager.player(1).getCards.length shouldBe(7)

    }

    "place a question card "in {
      controller.gameManager = controller.getGameManager.copy(numberOfRounds = 0)
      controller.eval("2")
      controller.getGameManager.roundQuestion shouldBe a [String]
      controller.state shouldBe(AnswerState)(controller)
    }

    "Should place 2 Cards in a Turn" in {
      controller.state = SetupState(controller)
      controller.nextState()
      controller.state shouldBe AnswerState(controller)
      controller.eval("2")
      controller.getGameManager.roundAnswerCards.size shouldBe(2)
      controller.getGameManager.activePlayer shouldBe(0)
      controller.gameManager = controller.getGameManager.copy(numberOfRounds = 10)
      controller.nextState()
      controller.state shouldBe FinishState(controller)
    }

    "Should change State to finish" in {
      controller.state = AnswerState(controller)
      controller.eval("")
      controller.getGameManager.roundAnswerCards shouldBe empty
      controller.getGameManager.roundQuestion shouldNot be (null)
      controller.gameManager = controller.getGameManager.copy(numberOfPlayableRounds = 6)
      controller.gameManager = controller.getGameManager.copy(numberOfRounds = 7)

    }
  }
}

