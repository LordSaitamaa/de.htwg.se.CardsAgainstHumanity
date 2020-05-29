import model._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class SetupGameSpec extends AnyWordSpec  with Matchers{

  var answerCards = List[AnswerCard]()
  var questionCards = List[QuestionCard]()
  var answerList = List[String]("nein", "doch", "ohhh")
  var questionList = List[String]("a?", "b?", "c?")
  var cardList = List[Card](AnswerCard("nein"), AnswerCard("nö"), QuestionCard("Wie?"), QuestionCard("Was?"))

  val standardCards: StandardCards = StandardCards(questionList, answerList)

  var kompositumCard = KompositumCard(cardList)

  var playerOne = Player("Hugo", true, answerCards);
  var playerTwo = Player("Heinz", true, answerCards);

  var playerVec = Vector(playerOne, playerTwo)

  var setupGame =  SetupGame(standardCards, playerVec, answerCards, questionCards, null, "Was wollt ihr dann? _")

  "return a modified copy of SetupGame" in {
    //setupGame = setupGame.copy(standardCards, playerVec, answerCards, questionCards, null, "Was wollt ihr dann? _")

    setupGame.createCardDeck(kompositumCard)

    setupGame.standardCards shouldNot be(null)
    setupGame.player shouldNot be(null)
    setupGame.answerList shouldNot be(null)
    setupGame.questionList shouldNot be(null)
    setupGame.roundAnswerCards should be(null)
    setupGame.roundQuestion should be("Was wollt ihr dann? _")
  }
  "return a SetupGame with a new CardDeck" in {

    setupGame.answerList shouldNot be(null)
  }
}
