import model._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

  class PlayerSpec  extends AnyWordSpec  with Matchers {

    "It´s a player" when {
      val playerOne =  Player("Hugo", false)
      val playerTwo =  Player("Heinz", true)
      "A player" should{

        "return their status" in {
          playerOne.getStatus shouldBe(false)
          playerTwo.getStatus shouldBe(true)
        }

        "print their names" in {
          playerOne.getName shouldBe("Hugo")
          playerTwo.getName shouldBe("Heinz")

        }
        "show their text" in {
          playerOne.toString
          playerTwo.toString
        }
      }
    }
  }


