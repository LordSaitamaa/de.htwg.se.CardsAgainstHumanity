package view
import control.{ControllerInterface, SecondPageEvent, StartPageEvent, ThirdPageEvent, UpdateTuiEvent}

import scala.swing.Publisher


class Tui(controller:ControllerInterface) extends Publisher{

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
    println(controller.getGameManager.toString)
  }

  listenTo(controller)
  reactions += {
    case event: UpdateTuiEvent => update()

  }

}