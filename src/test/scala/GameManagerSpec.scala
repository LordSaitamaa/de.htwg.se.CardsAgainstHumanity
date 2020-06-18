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
      gm.getActivePlayer() should (be(0) or be(1))
    }
    "should pick the next player" in {
      gm.pickNextPlayer(0) shouldBe 1
      gm.pickNextPlayer(1) shouldBe 0
    }
    "should give string-representation" in {
      var gx = GameManager()
      gx.toString shouldBe  ""
      gm.toString shouldNot be("")
    }
  }
}

  /*

  var answerCards = List[AnswerCard]()
  var questionCards = List[QuestionCard]()
  var answerList = List[String]("nein", "doch", "ohhh")
  var questionList = List[String]("a?", "b?", "c?")
  var cardList = List[Card](AnswerCard("nein"), AnswerCard("nö"), QuestionCard("Wie?"), QuestionCard("Was?"))
  val standardCards  = StandardCards(questionList, answerList)
  var kompositumCard = KompositumCard(cardList)
  var playerOne = Player("Hugo", true, answerCards);
  var playerTwo = Player("Heinz", true, answerCards);
  var playerVec = Vector(playerOne, playerTwo)

  var setupGame =  GameManager()

  "A SetupGame should" should {
    "be filled and created by createCardDeck" in {

      setupGame = setupGame.createCardDeck()

      //setupGame.standardCards shouldNot be(null)
      setupGame.player shouldNot be(null)
      setupGame.answerList shouldNot be(null)
      setupGame.questionList shouldNot be(null)
      setupGame.roundAnswerCards should be(null)
      setupGame.roundQuestion should be(null)
    }
  }
  "handout cards for players" in {

    setupGame.player.apply(0).playerCards should be(empty)
    setupGame = setupGame.handOutCards()

    setupGame.player.apply(0).getCards shouldNot be(empty)
  }
  "place a question card" in {

    setupGame = setupGame.placeQuestionCard()
    setupGame.roundQuestion shouldNot be(null)
  }
  "answer a question card" in {

    setupGame = setupGame.placeCard(0, AnswerCard(""))
    setupGame.roundAnswerCards shouldNot be(null)
  }
}
*/