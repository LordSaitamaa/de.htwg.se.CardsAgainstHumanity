package view
import utils.{Observable, Observer}
import control.Controller
import model.{AnswerCard, Card, KompositumCard, Player, StandardCards}


class Tui(controller:Controller) extends Observer{
   controller.add(this)
  val player = Vector(
     Player("1",true, List[AnswerCard]()),
     Player("2",true, List[AnswerCard]())
  )
  val standardQ = List[String]("Meine Oma mag _", "Ich bin schockiert wenn ich _ sehe","b","c","d","e","f","g","h","das?")
  val standardA = List[String]("Kartoffeln", "Schwarze Einhörner","ada","ads","adsd","afs","fasfa","afsaf","fasfas","fasfa","ha","fawe","adsads"
                              ,"adfs","gfdagads","fasfsa","fsafsa","fasfasfas","afsfsaafs")
  val standardCards = StandardCards(standardQ,standardA)
  var userCards = List[Card]()
  var kompositumCard = KompositumCard(userCards)
  controller.notifyObservers

   def processInputLine(input:String):Unit = {
      input match{
         case "q" =>
         case "n" => controller.initCardDeck(standardCards,kompositumCard,player)
         case "h" => controller.handOutCards()
         case "t" => val question = controller.question()
                     println(question)
         case _ =>
          try{
             input.toList.filter(c => c!= ' ').map(c => c.toString.toInt)match{
                case x :: Nil =>
                   if(x > 6 || x < 0)
                      println("Kein Gütlige Karte")
                 else{
                      controller.put(x)
                   }
             }
          }catch {
             case _: NumberFormatException =>
          }
      }
   }


   override def update(): Unit = println("hello")
}
