import model.Player
case class Player(isActive:Boolean, throwNum:Int){
  def numberToThrow = throwNum
  def activeTurn = isActive
}
val test = Player(true,1)
test.isActive
test.numberToThrow