package model

case class StandardCards(var standardQuestions:List[String], var standardAnswer:List[String]) {

  def printStandardQuestion: String = {standardQuestions.toString()}
  def printStandardAnswer: String = {standardAnswer.toString()}
  def getStandardAnswer: List[String] ={ standardAnswer }
  def getQuestionCards: List[String] = { standardQuestions }
}
