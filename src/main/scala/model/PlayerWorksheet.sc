import model.Cards

case class Player(isActive:Boolean, throwNum:Int){
  def numberToThrow = throwNum
  def activeTurn = isActive
}
val test = Player(true,1)
test.isActive
test.numberToThrow

val karte1 = new Cards("test1")
val karte2 = new Cards("test2")
val karte3 = new Cards("test3")

val cards = List(karte1, karte2, karte3)