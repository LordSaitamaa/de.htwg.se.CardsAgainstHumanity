package model
import scala.util.control.Breaks._

case class SetupGame(standardCards: StandardCards,var kompositumCard: KompositumCard,var player:Vector[Player]
                     , var answerList:List[AnswerCard], var questionList:List[QuestionCard]
                     , var roundAnswerCards: Map[Player, String], var roundQuestion: String) {

  def createCardDeck(): SetupGame ={
    for(answer <- standardCards.standardAnswer){ kompositumCard = kompositumCard.addNewCard(AnswerCard(answer))}
    for(questions <- standardCards.standardQuestions){ kompositumCard = kompositumCard.addNewCard(QuestionCard(questions))}

    for(x <- kompositumCard.cardList ){
      x match {
        case card: AnswerCard => answerList = answerList :+ card
        case card: QuestionCard => questionList = questionList :+ card
        case _ => println("Keine Zulässige Karte")
      }
    }
    copy(standardCards,kompositumCard,player)
  }

  def handOutCards(): SetupGame ={
    var count = 0
    var cardcount = 0
    val maxCardOnHand = 7

    while(count < maxCardOnHand) {
      for(x <- player){
        if(cardcount >= answerList.length){
          break
        }
        x.playerCards = x.playerCards :+ answerList(cardcount)
        cardcount = cardcount + 1
      }
      count = count + 1
    }
    player.foreach(x => x.playerCards.foreach(y => kompositumCard = kompositumCard.removeCard(y)))
    copy(standardCards,kompositumCard,player)
  }

  def placeQuestionCard(): String = {
    val max = questionList.length
    val min = 0
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
