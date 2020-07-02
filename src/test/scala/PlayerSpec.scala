import model.BaseImpl.AnswerCard
import model.{BaseImpl, _}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PlayerSpec extends AnyWordSpec with Matchers {

  "It´s a player" should {

    val a = List[AnswerCard]()
    var p1 = BaseImpl.Player("Hugo", false, a)
    val p2 = BaseImpl.Player("Heinz", true, a)

    "have a name" in {
      p1.getName shouldBe "Hugo"
      p2.getName shouldBe "Heinz"
    }
    "are able to get a string-representation" in {
      p1.toString shouldBe "Player: Hugo // State: false"
    }
    "get a card added to hand" in {

      p1.getCards.length shouldBe 0
      p1 = p1.addCard(AnswerCard("niemals"))
      p1.getCards.length shouldBe 1
    }
    "change and return their status" in {

      p1.getStatus shouldBe true
      p2.getStatus shouldBe true

      p1 = p1.changeState

      p1.getStatus shouldBe false

    }
  }
}

