package model

final case class StandardCards() {
  final private var standardAnswers = List("Ich finde _ , toll", "Der Held meiner Oma ist _")
  final private var standardQuestions = List("Hugh Hefner", "Donald Trump")

  def getStandardAnswer: List[String] ={ standardAnswers }
  def getQuestionCards: List[String] = { standardQuestions }


}
