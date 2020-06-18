import model._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameManagerSpec extends AnyWordSpec  with Matchers{

  var gm = GameManager()

  "A Gamemanager" should {

    "contain following values after creation" in {
      gm.numberOfPlayers shouldBe 0
      gm.numberOfRounds shouldBe 0
      gm.kompositumCard shouldNot be(null)
      gm.player shouldBe null
      gm.answerList shouldBe Nil
      gm.questionList shouldBe Nil
      gm.roundAnswerCards shouldBe null
      gm.roundQuestion shouldBe ""
    }
    "have players" in {
      gm = gm.setPlayersAndRounds(2)
      gm = gm.addPlayer("Hugo")
      gm = gm.addPlayer("Heinz")

      gm.numberOfPlayers shouldBe 2
      gm.player(0).toString shouldBe "Player: Hugo // State: true"
      gm.player(1).toString shouldBe "Player: Heinz // State: true"
    }
    "create a Carddeck" in {
      gm = gm.createCardDeck()

      gm.answerList shouldNot be(Nil)
      gm.questionList shouldNot be(Nil)
    }
    "handout cards to players" in {
      gm = gm.handOutCards()

      //gm.player(0).getCards.toString.contains("Bombenanschläge") shouldBe true
      gm.player(0).getCards shouldNot be(empty)
      gm.player(1).getCards shouldNot be(empty)
    }
    "should place a question card" in {
      gm = gm.placeQuestionCard()

      gm.roundQuestion shouldNot be("")
    }
    "should place a answer card" in {
      gm = gm.placeCard(1, new AnswerCard("blah"))
      gm.roundAnswerCards shouldNot be(null)

      gm = gm.placeCard(1, new AnswerCard("hihihi"))
      gm.roundAnswerCards shouldNot be(null)
    }
    "should return the active player" in {
      gm.getActivePlayer() shouldBe 0
    }
    "should pick the next player" in {
      gm = gm.pickNextPlayer()
      gm.activePlayer shouldBe 1
    }
    "should give string-representation" in {
      var gx = GameManager()
      gx.toString shouldBe  ""
      gm.toString shouldNot be("")
    }

    "should refill cards" in {

      var x = gm.player(0).getCards
      gm = gm.placeCard(0, x(2))
      gm.player(0).getCards.length shouldBe 6
      gm = gm.drawCard()
      gm.player(0).getCards.length shouldBe 7
    }
  }
}