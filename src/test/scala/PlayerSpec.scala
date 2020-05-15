import model._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

  class PlayerSpec  extends AnyWordSpec  with Matchers {

    "It´s a player" when {
      "Set new Players" should{
        val playerOne = new Player("Hugo", false)
        val playerTwo = new Player("Heinz", true)

        "return their status" in {
          println(assert(!playerOne.getStatus))
          println(assert(playerTwo.getStatus))
        }
        "print their names" in {

          println(playerOne.getName)
          println(playerTwo.getName)

        }
        "show their text" in {
          println(playerOne.toString)
          println(playerTwo.toString)
        }
      }
    }
  }


