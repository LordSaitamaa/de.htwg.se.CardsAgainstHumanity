import model.BaseImpl.{AnswerCard, Card, GameManager, KompositumCard, QuestionCard}
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
      gm.player shouldNot be(null)
      gm.answerList shouldBe Nil
      gm.questionList shouldBe Nil
      gm.roundAnswerCards shouldNot be(null)
      gm.roundQuestion shouldBe ""
    }
    "have players" in {
      gm = gm.setPlayersAndRounds(2)
      gm = gm.addPlayer("Hugo")
      gm = gm.addPlayer("Heinz")

      gm.numberOfPlayers shouldBe 2
      gm.player.length shouldBe 2
      gm.player(1).toString shouldBe "Player: Heinz // State: true"
    }
    "create a Carddeck" in {
      gm.kompositumCard = KompositumCard(List[Card](AnswerCard("hahah"), AnswerCard("Hihihihi"), QuestionCard("Wie bitte _ ?")))
      gm = gm.createCardDeck()
      gm.player.length shouldBe 2
      gm.answerList shouldNot be(Nil)
      gm.questionList shouldNot be(Nil)
    }
    "handout cards to players" in {
      gm = gm.handOutCards()
      gm.player(0).getCards shouldNot be(empty)
    }
    "should place a question card" in {
      gm = gm.placeQuestionCard()
      gm.roundQuestion shouldNot be(Nil)
    }
    "should place a answer card" in {
      gm = gm.placeCard(0, AnswerCard("blah"))
      gm.roundAnswerCards shouldNot be (null)

      gm = gm.placeCard(0, AnswerCard("hihihi"))
      gm.roundAnswerCards shouldNot be (null)
    }
    "should return the active player" in {
      gm.getActivePlayer() shouldBe 0
    }
    "should pick the next player" in {
      gm = gm.pickNextPlayer()
      gm.activePlayer shouldBe 0
    }
    "should give string-representation" in {
      gm.toString shouldNot be("")
    }

   /* "should refill cards" in {
      gm.answerList = List[AnswerCard](AnswerCard("a"),AnswerCard("ab"),AnswerCard("abb"),AnswerCard("abbc"),AnswerCard("ac"),AnswerCard("acc"),AnswerCard("add"))
      var x = gm.player(0).getCards
      gm = gm.placeCard(0, x(1))
      gm.player(0).getCards.length shouldBe 1
      gm = gm.drawCard()
      gm.player(0).getCards.length shouldBe 2
      gm.toString shouldNot be (null)
    }*/
  }
}