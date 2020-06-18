package model

case class StandardCards(standardQuestions:List[String], standardAnswer:List[String]) {

  def printStandardQuestion: String = {standardQuestions.toString()}
  def printStandardAnswer: String = {standardAnswer.toString()}
  def getStandardAnswer: List[String] ={ standardAnswer }
  def getQuestionCards: List[String] = { standardQuestions }
}
