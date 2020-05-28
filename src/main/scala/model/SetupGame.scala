package model
import scala.util.control.Breaks._

case class SetupGame(standardCards: StandardCards,var kompositumCard: KompositumCard,var player:Vector[Player]) {

  def createCardDeck(): SetupGame ={
    for(answer <- standardCards.standardAnswer){ kompositumCard = kompositumCard.addNewCard(AnswerCard(answer))}
    for(questions <- standardCards.standardQuestions){ kompositumCard = kompositumCard.addNewCard(QuestionCard(questions))}
    copy(standardCards,kompositumCard,player)
  }

  def handOutCards(): SetupGame ={
    var count = 0
    var cardcount = 0
    val maxCardOnHand = 7
    var answerList = List[Card]()
    for(answer <- kompositumCard.cardList) {
      answer match {
        case _: AnswerCard =>
          answerList = answerList :+ answer
        case _ =>
      }
    }
    while(count < maxCardOnHand) {
      for(x <- player){
        if(cardcount >= answerList.length){
          break
        }
        x.playerCards = x.playerCards :+ answerList(cardcount).asInstanceOf[AnswerCard]
        cardcount = cardcount + 1
      }
      count = count + 1
    }
    player.foreach(x => x.playerCards.foreach(y => kompositumCard = kompositumCard.removeCard(y)))
    copy(standardCards,kompositumCard,player)
  }

  def placeQuestionCard(): String = {
    var questionList = List[QuestionCard]()
    for(question <- kompositumCard.cardList) {
      question match {
        case _: QuestionCard =>
          questionList = questionList :+ question.asInstanceOf[QuestionCard]
          kompositumCard = kompositumCard.removeCard(question)
        case _ =>
      }
    }
    val max = questionList.length
    val min = 0;
    val rnd = scala.util.Random
    val quest = questionList(rnd.nextInt(max - min))
    val returnQuest = quest.question
    returnQuest
  }

  def placeCard(card:AnswerCard): String = {
    kompositumCard.removeCard(card)
    card.getCard()
  }

}
