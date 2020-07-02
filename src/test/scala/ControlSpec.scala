import control.BaseImpl.{AddCardsQuest, AnswerState, Controller, FinishState, PreSetupState, SetupState}
import model.BaseImpl
import model.BaseImpl.GameManager
import org.scalatest._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import utils.Observer


class ControlSpec extends AnyWordSpec with Matchers with GivenWhenThen {
  "A Controller " when {
    val gameManager = GameManager()
    val controller = new Controller(gameManager)
    val observer = new Observer {
      var updated: Boolean = false
      def isUpdated: Boolean = updated
      override def update: Unit = updated = true
    }
   // controller.add(observer)
      "test Strategy"in{
        controller.gameManager = controller.gameManager.setPlayersAndRounds(2)
        controller.gameManager.numberOfPlayer.shouldBe(2)
        controller.gameManager.numberOfPlayableRound.shouldBe(8)
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
      controller.gameManager = controller.gameManager.asInstanceOf[GameManager].copy(numberOfRounds = 3)
      controller.state shouldBe (AnswerState(controller))

      controller.state = SetupState(controller)
      controller.nextState()
      controller.gameManager = controller.gameManager.asInstanceOf[GameManager].copy(numberOfRounds = 9)
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
      controller.gameManager.allPlayerG()(0).name shouldBe "Hans"
      controller.eval("Dirk")
      controller.gameManager.allPlayerG()(1).name shouldBe "Dirk"
      controller.gameManager.allPlayerG().length shouldBe 2

      controller.gameManager.allPlayerG()(0).getCards.length shouldBe(7)
      controller.gameManager.allPlayerG()(1).getCards.length shouldBe(7)

    }

    "place a question card "in {
      controller.gameManager = controller.gameManager.asInstanceOf[GameManager].copy(numberOfRounds = 0)
      controller.eval("2")
      controller.gameManager.roundQuestionG shouldBe a [String]
      controller.state shouldBe(AnswerState)(controller)
    }

    "Should place 2 Cards in a Turn" in {
      controller.state = SetupState(controller)
      controller.nextState()
      controller.state shouldBe AnswerState(controller)
      controller.eval("2")
      controller.gameManager.roundAnswerCardG.size shouldBe(2)
      controller.gameManager.activePlayerG shouldBe(0)
      controller.gameManager = controller.gameManager.asInstanceOf[GameManager].copy(numberOfRounds = 10)
      controller.nextState()
      controller.state shouldBe FinishState(controller)
    }

    "Should change State to finish" in {
      controller.state = AnswerState(controller)
      controller.eval("")
      //controller.gameManager.roundAnswerCardsG shouldBe empty
      controller.gameManager.roundQuestionG shouldNot be (null)
      controller.gameManager = controller.gameManager.asInstanceOf[GameManager].copy(numberOfPlayableRounds = 6)
      controller.gameManager = controller.gameManager.asInstanceOf[GameManager].copy(numberOfRounds = 7)

    }
  }
}

