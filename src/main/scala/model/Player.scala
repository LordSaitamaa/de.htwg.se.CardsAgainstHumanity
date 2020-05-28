package model

case class Player(name:String,isAnswering:Boolean) {
  override def toString: String = {"Player: " + this.name + " // State: " + this.isAnswering}
  def getName : String = {name}
  def getStatus: Boolean = {isAnswering}
  def changeState: Player = {

    copy(this.name, !this.isAnswering)
  }

  // Controller fertig machen
  //
}
