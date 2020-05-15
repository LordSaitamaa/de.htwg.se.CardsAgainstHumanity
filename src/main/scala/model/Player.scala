package model

case class Player(name:String,isAnswering:Boolean) {
  override def toString: String = {"Ist " + name + " am beantworten? : " + isAnswering}
  def getName : String = {name}
  def getStatus: Boolean = {isAnswering}
}
