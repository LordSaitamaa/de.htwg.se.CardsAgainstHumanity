import model._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

  class PlayerSpec  extends AnyWordSpec  with Matchers {

    "It´s a player" when {
      var playerOne = new Player("Hugo", false)
      val playerTwo = new Player("Heinz", true)

      "a player" should{

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


