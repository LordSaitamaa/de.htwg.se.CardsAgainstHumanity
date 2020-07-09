
case class Player(cards:List[String],isActive:Boolean, throwNum:Int){
  def numberToThrow = throwNum
  def activeTurn = isActive
  def cardsList = cards.toString()
}
val list: List[String] = List("A","B","c")
val list3 = ("a","b","c")
val list2 = "A" :: "B" :: "C" :: Nil
val test = Player(list,true,1)
test.cardsList
test.isActive
test.numberToThrow