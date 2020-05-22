import model._
import org.scalatest._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class StandardCardSpec extends AnyWordSpec with Matchers with GivenWhenThen{

  "A standard Card List" when {
    "Standard Card" should {
      "never have lists without elements" in {
        val l1 = List("Ich bin _ toll", "Du bist _ toll")
        val l2 = List("nicht", "auf gar keinen fall")
        val a = StandardCards(l1, l2)
        a.getStandardAnswer.length shouldBe (2)
        a.getQuestionCards.length shouldBe (2)

        Given("The two final Lists")
        a.printStandardAnswer
        a.printStandardQuestion

        When("we want the list")
        val b = a.getQuestionCards

        Then("b should have same Size like QuestionCards")
        b.length shouldBe (a.getQuestionCards.length)

      }
    }
  }

}
