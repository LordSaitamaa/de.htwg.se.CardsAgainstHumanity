import control.BaseImpl.{AddCardsCommand, Controller}
import model._
import model.BaseImpl.GameManager
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class AddCardsCommandSpec extends AnyWordSpec with Matchers {

  "AddCardsCommandSpec works" when {
    val gm = new GameManager()
    val ctr = new Controller(gm)
    val x = new AddCardsCommand("funktioniert_sicher", ctr)

    "do step works if" in {
      ctr.gameManager.gameManagerG().getKompositum().cardList.length shouldBe 0
      x.doStep
      ctr.gameManager.gameManagerG().getKompositum().cardList.length shouldBe 1
    }
    "undo step works if" in {
      x.undoStep
      ctr.gameManager.gameManagerG().getKompositum().cardList.length shouldBe 0
    }
    "redo step works if" in {
      x.redoStep

      ctr.gameManager.gameManagerG().getKompositum().cardList.length shouldBe 1
    }

  }

}
