import javax.smartcardio.Card
import model._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class UserCardSpec extends AnyWordSpec  with Matchers {
  "A Kompositum" when {
    "set a new Card" should{
      val userAnswerOne =  AnswerCard("a",true)
      val userQuestion =  QuestionCard("a?", true)
      val list = List[KompositumCard]()
      val kompCard =  KompositumCard(list)
      kompCard.addNewCard(userAnswerOne)
      kompCard.addNewCard(userQuestion)
      "Have 2 Cards" in {
        kompCard.getAllAddedCards().length shouldBe(2)
      }
      "print its text" in {
        kompCard.printCard
      }
      "Have a list of both Card Types" in {
        kompCard.getAllAddedCards()
      }
      "Should have 1 left after remove" in {
        kompCard.removeCard(userAnswerOne)
        kompCard.getAllAddedCards().length shouldBe(1)
      }
    }
  }


}
