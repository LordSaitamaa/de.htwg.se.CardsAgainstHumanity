package model

case class SetupGame(var standardCards: StandardCards, var kompositumCard: KompositumCard, var playerCount:Int) {

  def createCardDeck(): Unit ={
    for(answer <- standardCards.standardAnswer) kompositumCard.addNewCard(AnswerCard(answer,true))
    for(questions <- standardCards.standardAnswer) kompositumCard.addNewCard(QuestionCard(questions,true))
  }

}
