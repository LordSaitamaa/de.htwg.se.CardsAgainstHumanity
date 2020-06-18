import control.Controller
import model.{AnswerCard, Card, KompositumCard, Player, GameManager, StandardCards}
import org.scalatest._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import view.Tui


class ControlSpec extends AnyWordSpec with Matchers with GivenWhenThen {
  "A Tui " when {
    "do its work" should {
      "have a player with cards " in {
        val player = Vector(
          Player("Spieler 1", true, List[AnswerCard]()),
        )
        val controller = new Controller(GameManager())

        val standardQ = List[String]("a?", "b?")
        val standardA = List[String]("c", "d")
        val userCards = List[Card](AnswerCard("Ich bin der aller beste"))
        val kompositumCard = KompositumCard(userCards)
        val standardCards = StandardCards(standardQ, standardA)


        Given("Our Player, standard List and a Kompositum ")
        player.length shouldBe 1
        kompositumCard.cardList.length shouldBe 1
        standardCards.standardAnswer.length shouldBe 2
        standardCards.standardQuestions.length shouldBe 2

//        When("We create a new Player")
//        controller.initCardDeck(standardCards,kompositumCard,player)
//        controller.handOutCards()
//        Then("A Player should get Cards")
//        controller.gameManager.player(0).playerCards.length shouldBe (3)

        Given("The Question Cards")
        standardQ.length shouldBe 2
        When("We throw a question")
        controller.question()
        Then("We have a round question")
        controller.gameManager.roundQuestion.isInstanceOf[String]

      }

    }
  }
}
