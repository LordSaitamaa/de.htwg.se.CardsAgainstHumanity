import control.Controller
import model.GameManager
import view.GUI.SwingGui
import view.Tui

import scala.io.StdIn.readLine

object CaHMain {
  val controller = new Controller(GameManager())
  val gui = new SwingGui(controller)

  def main(args: Array[String]): Unit = {
    var input: String = ""

    gui.open()

   /*
    if (args.length>0) input=args(0)
    else do{
      //input = readLine()
     // tui.processInputLine(input)
    }while(input != "q")

    */

  }
}
