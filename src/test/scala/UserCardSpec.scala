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
      var kompCard =  KompositumCard(list)
      kompCard = kompCard.addNewCard(userAnswerOne)
      kompCard = kompCard.addNewCard(userQuestion)

      "Have 2 Cards" in {
        kompCard.userAddedCard.length shouldBe(2)
      }
      "print its text" in {
        kompCard.printCard
      }
      "Should have 1 left after remove" in {
       kompCard = kompCard.removeCard(userAnswerOne)
        kompCard.userAddedCard.length shouldBe(1)
      }
    }
  }


}
