package model

case class StandardCards(standardQuestions:List[String], standardAnswer:List[String]) {
 // final private val standardQuestions = List("Ich finde _ , toll", "Der Held meiner Oma ist _")
 // final private val standardAnswers = List("Hugh Hefner", "Donald Trump")

  def printStandardQuestion: String = {standardQuestions.toString()}
  def printStandardAnswer: String = {standardAnswer.toString()}
  def getStandardAnswer: List[String] ={ standardAnswer }
  def getQuestionCards: List[String] = { standardQuestions }
}
