import model._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

  class PlayerSpec  extends AnyWordSpec  with Matchers {

    "It´s a player" when {
      val a = List[AnswerCard]()
      var playerOne = Player("Hugo", false,a)
      val playerTwo = Player("Heinz", true,a )

      "A player" should {

        "a player" should {

          "return their state" in {
            playerOne.getStatus shouldBe false
            playerTwo.getStatus shouldBe true
          }
          "is able to change his state" in {
            playerOne = playerOne.changeState
            playerOne.getStatus shouldBe true
          }

          "have a name" in {
            playerOne.getName shouldBe "Hugo"
            playerTwo.getName shouldBe "Heinz"
          }
          "show their text" in {
            playerOne.toString
            playerTwo.toString
          }
        }
      }
    }
  }

