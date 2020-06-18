package model

case class Player(name:String, isAnswering:Boolean, playerCards:List[AnswerCard]) {
  override def toString: String = {"Player: " + this.name + " // State: " + this.isAnswering}
  def getName : String = {name}

  def addCard(answerCard: AnswerCard) : Player = {
    val immutableAnswerList = playerCards :+ answerCard
    copy(name,true,immutableAnswerList)
  }
  def getStatus: Boolean = {isAnswering}
  def changeState: Player = {
    copy(this.name, !this.isAnswering)
  }
  def getCards: List[AnswerCard] = {playerCards}
}
