import model._

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class AnswerCardSpec extends AnyWordSpec  with Matchers {
  "A Kompositum" when {
    "set a new Card" should{
      val userAnswerOne = new AnswerCard("a",true)
      val userQuestion = new QuestionCard("a?", true)
      val kompositumCard = new KompositumCard()
      kompositumCard.addNewCard(userAnswerOne)
      kompositumCard.addNewCard(userQuestion)
      "Have 2 Cards" in {
        assert(kompositumCard.getAllAddedCards().length == 2)
      }
      "print its text" in {
        kompositumCard.printCard
      }
    }
  }


}
