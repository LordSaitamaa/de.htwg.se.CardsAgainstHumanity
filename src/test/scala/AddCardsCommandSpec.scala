import control.BaseImpl.{AddCardsCommand, Controller}
import model._
import model.BaseImpl.GameManager
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class AddCardsCommandSpec extends AnyWordSpec with Matchers {

  "AddCardsCommandSpec works" when {
    var gm = new GameManager()
    var ctr = new Controller(gm)
    var x = new AddCardsCommand("funktioniert_sicher", ctr)



    "do step works if" in {

      ctr.getGameManager.questionList.length shouldBe 0
      x.doStep
      ctr.getGameManager.questionList.length shouldBe 1
    }
    "undo step works if" in {
      x.undoStep
      ctr.getGameManager.questionList.length shouldBe 0
    }
    "redo step works if" in {
      x.redoStep

      ctr.getGameManager.questionList.length shouldBe 1
    }

  }

}
