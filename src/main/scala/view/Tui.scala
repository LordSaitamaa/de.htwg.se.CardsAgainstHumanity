package view
import control.BaseImpl.Controller
import utils.Observer
import control.{SecondPageEvent, StartPageEvent, ThirdPageEvent, UpdateTuiEvent}
import model.BaseImpl.Player

import scala.swing.Publisher


class Tui(controller:Controller) extends Publisher{

  def processInputLine(input:String):Unit = {
    input match{
      case "quit" =>
      case "undo" => controller.undo
      case "redo" => controller.redo
      case _ => controller.eval(input)
    }
  }

  def update(): Unit = {
    println(controller.getCurrentStateAsString)
    println(controller.gameManager.toString)
  }

  listenTo(controller)
  reactions += {
    case event: UpdateTuiEvent => update()

  }

}