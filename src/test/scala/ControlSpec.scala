import control.{AddCardsQuest, AnswerState, Controller, FinishState, PreSetupState, QuestionState, SetupState}
import model.GameManager
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
        controller.gameManager.numberOfPlayers.shouldBe(2)
        controller.gameManager.numberOfPlayableRounds.shouldBe(8)
      }

    "notify the observer after evaluation" in {
      controller.eval("3" )
      observer.updated shouldBe true
      controller.state = PreSetupState(controller)
      controller.eval("4")
      observer.updated shouldBe true
      controller.state = PreSetupState(controller)
      controller.eval("2")
      observer.updated shouldBe true
    }

    "get the according State String" in {
      controller.state = PreSetupState(controller)
      controller.getCurrentStateAsString() shouldBe ("Willkommen bei Cards Against Humanity \n Bitte eine Spielerzahl zwischen 2 und 4 eingeben")

      controller.state = SetupState(controller)
      controller.getCurrentStateAsString() shouldBe "Spieleranzahl: " + controller.gameManager.numberOfPlayers + " Übrige Karten: " + controller.gameManager.answerList.toString()

      controller.state = QuestionState(controller)
      controller.getCurrentStateAsString() shouldBe "Frage wird gestellt"

      controller.state = AnswerState(controller)
      controller.getCurrentStateAsString() shouldBe  "Antworten legen um weiter zu machen"

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
      controller.state shouldBe (QuestionState(controller))

      controller.state = QuestionState(controller)
      controller.gameManager = controller.gameManager.copy(numberOfRounds = 3)
      controller.nextState()
      controller.state shouldBe (AnswerState(controller))

      controller.state = QuestionState(controller)
      controller.gameManager = controller.gameManager.copy(numberOfRounds = 9)
      controller.nextState()
      controller.state shouldBe FinishState(controller)

      controller.state = AnswerState(controller)
      controller.nextState()
      controller.state shouldBe QuestionState(controller)
    }

    "return the correct String for every State" in {
      controller.state = PreSetupState(controller)
      controller.stateAsString() shouldBe("PreSetupGame")

      controller.state = SetupState(controller)
      controller.stateAsString() shouldBe "SetupGame"

      controller.state = QuestionState(controller)
      controller.stateAsString() shouldBe "QuestionState"

      controller.state = AnswerState(controller)
      controller.stateAsString() shouldBe "AnswerState"

      controller.state = FinishState(controller)
      controller.stateAsString() shouldBe "FinishState"
    }

    "handout cards correctly "in{
      controller.state = SetupState(controller)
      controller.eval("Hans")
      controller.gameManager.player(0).name shouldBe "Hans"
      controller.eval("Dirk")
      controller.gameManager.player(1).name shouldBe "Dirk"
      controller.gameManager.player.length shouldBe 2

      controller.gameManager.answerList.length shouldBe(7)
      controller.gameManager.player(0).getCards.length shouldBe(7)
      controller.gameManager.player(1).getCards.length shouldBe(7)

      controller.state shouldBe QuestionState(controller)
    }

    "place a question card "in {
      controller.gameManager = controller.gameManager.copy(numberOfRounds = 0)
      controller.state = QuestionState(controller)
      controller.state shouldBe QuestionState(controller)
      controller.eval("2")
      controller.gameManager.roundQuestion shouldBe a [String]
      controller.state shouldBe(AnswerState)(controller)
    }

    "Should place 2 Cards in a Turn" in {
      controller.state = AnswerState(controller)
      controller.eval("2")
      controller.state = AnswerState(controller)
      controller.eval("3")
      controller.state = QuestionState(controller)
      controller.gameManager.roundAnswerCards.size shouldBe(2)
      controller.gameManager.activePlayer shouldBe(0)
      controller.gameManager = controller.gameManager.copy(numberOfRounds = 10)
      controller.eval("2")
      controller.eval("3")
      controller.state shouldBe FinishState(controller)
    }
  }
}

