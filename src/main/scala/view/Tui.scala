package view
import utils.{Observer}
import control.Controller
import model.{AnswerCard, Card, KompositumCard, Player, StandardCards}


class Tui(controller:Controller) extends Observer{
  controller.add(this)


  def processInputLine(input:String):Unit = {
    input match{
      case "quit" =>
      case _ => controller.eval(input)
    }
  }


  override def update(): Unit = {
    println(controller.getCurrentStateAsString)
    println(controller.gameManager.toString)
  }

}