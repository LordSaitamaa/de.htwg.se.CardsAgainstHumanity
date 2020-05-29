import model._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class SetupGameSpec extends AnyWordSpec  with Matchers{

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

  var setupGame =  SetupGame(standardCards, playerVec, answerCards, questionCards, null, null)

  "A SetupGame should" should {
    "be filled and created by createCardDeck" in {

      setupGame = setupGame.createCardDeck(kompositumCard)

      setupGame.standardCards shouldNot be(null)
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
