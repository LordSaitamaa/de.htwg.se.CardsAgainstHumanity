import jdk.internal.joptsimple.internal.Strings

case class Player(cards:List[String],isActive:Boolean, throwNum:Int){
  def numberToThrow = throwNum
  def activeTurn = isActive
  def cardsList = cards.toString()
}
val list: List[String] = List("A","B","c")
val test = Player(list,true,1)
test.cardsList
test.isActive
test.numberToThrow