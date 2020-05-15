import model._
import org.scalatest._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class StandardCardSpec extends AnyWordSpec with Matchers with GivenWhenThen{

  "Standard Card" should {
    "never have lists without elements" in{
      val a = new StandardCards
      assert(a.getStandardAnswer.length > 0)
      assert(a.getQuestionCards.length > 0)

      Given ("The two final Lists")
      a.printStandardAnswer
      a.printStandardQuestion

      When("we want the list")
      val  b = a.getQuestionCards

      Then("b should have same Size like QuestionCards")
      assert(b.length == a.getQuestionCards.length)

    }
  }

}
