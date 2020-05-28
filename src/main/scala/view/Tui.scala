package view
import utils.{Observable, Observer}
import control.Controller
import model.{AnswerCard, Card, KompositumCard, Player, StandardCards}


class Tui(controller:Controller) extends Observer{
  controller.add(this)
  val player = Vector(
    Player("Spieler 1",true, List[AnswerCard]()),
    Player("Spieler 2",true, List[AnswerCard]())
  )
  val standardQ = List[String]("Meine Oma mag _", "Ich bin schockiert wenn ich _ sehe","Ich nutze Seife nur um _","Die Demokratie ist _"
    ,"Studium bringt mir _","Russland macht alles _","Ich bin so weil _","Dieses Spiel ist _","Mario Barth kann _","Angela Merkel will _")
  val standardA = List[String]("Kartoffeln", "Schwarze Einhörner","Hitler töten","Menschen zu töten","Baum","unheimlich sinnlos","garnichts"
    ,"super","Bombenanschläge","Wasser zu verschwenden","hugo","fawe","adsads"
    ,"adfs","gfdagads","fasfsa","fsafsa","fasfasfas","afsfsaafs")
  val standardCards = StandardCards(standardQ,standardA)
  var userCards = List[Card]()
  var kompositumCard = KompositumCard(userCards)
  controller.notifyObservers

  def processInputLine(input:String):Unit = {
    input match{
      case "quit" =>
      case "new" => controller.initCardDeck(standardCards,kompositumCard,player)
      case "handout" => println(controller.handOutCards());
      case "throw" => println(controller.question())
      case _ =>
        try{
          input.toList.filter(c => c!= ' ').map(c => c.toString.toInt)match{
            case x :: Nil =>
              if(x > 6 || x < 0)
                println("Kein Gütlige Karte")
              else{
                println(controller.put(x))
              }
          }
        }catch {
          case _: NumberFormatException =>
        }
    }
  }


  override def update(): Unit = println("hello")
}