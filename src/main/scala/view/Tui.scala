package view
import utils.{Observer}
import control.Controller
import model.{AnswerCard, Card, KompositumCard, Player, StandardCards}


class Tui(controller:Controller) extends Observer{
  controller.add(this)
  val player = Vector(
    Player("Spieler 1",true, List[AnswerCard]()),
    Player("Spieler 2",true, List[AnswerCard]())
  )


  def processInputLine(input:String):Unit = {
    input match{
      case "quit" =>
      case "undo" => controller.undo
      case "redo" => controller.redo
      case _ => controller.eval(input)
    }
  }


  override def update(): Unit = {
    println("Aktueller Spiel Status")
    println(controller.getCurrentStateAsString)
    println(controller.gameManager.toString)
  }

}