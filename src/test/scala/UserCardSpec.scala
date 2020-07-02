import model.BaseImpl.{AnswerCard, KompositumCard, QuestionCard}
import model._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class UserCardSpec extends AnyWordSpec  with Matchers {
  "A Kompositum" when {
    "set a new Card" should{
      val userAnswerOne =  AnswerCard("David Bowie on a flying Unicorn")
      val userQuestion =  QuestionCard("When i was young i was scared because of")
      val list = List[KompositumCard]()
      var kompCard =  KompositumCard(list)
      kompCard = kompCard.addNewCard(userAnswerOne)
      kompCard = kompCard.addNewCard(userQuestion)

      "Answer cards print" in {
        userAnswerOne.toString
      }

      "Question cards print" in {
        userQuestion.toString
      }

      "Answer Card add should be null" in {
        userAnswerOne.addNewCard(AnswerCard("a")) shouldBe(null)
      }
      "QuestionCard add should be null" in {
        userQuestion.addNewCard(QuestionCard("a")) shouldBe(null)
      }
      "Answer Card remove should be null" in {
        userAnswerOne.removeCard(AnswerCard("a")) shouldBe(null)
      }
      "QuestionCard remove should be null" in {
        userQuestion.removeCard(QuestionCard("a")) shouldBe(null)
      }

      "Have 2 Cards" in {
        kompCard.cardList.length shouldBe(2)
      }
      "print its text" in {
        kompCard.printCard
      }
      "Should have 1 left after remove" in {
      kompCard = kompCard.removeCard(userAnswerOne)
        kompCard.cardList.length shouldBe(1)
      }
      "Remaining Element of KompositumCard List " in {
        kompCard.cardList(0).isInstanceOf[QuestionCard] shouldBe(true)
      }
    }
  }
}
