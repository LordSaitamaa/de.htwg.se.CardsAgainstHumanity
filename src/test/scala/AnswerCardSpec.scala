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
        println(assert(kompositumCard.getAllAddedCards().length == 2))
      }
      "print its text" in {
        kompositumCard.printCard
      }
      "Have a list of both Card Types" in {
       println(kompositumCard.getAllAddedCards())
      }
      "Should have 1 left after remove" in {
        kompositumCard.removeCard(userAnswerOne)
        println(assert(kompositumCard.getAllAddedCards().length == 1))
      }
    }
  }


}
