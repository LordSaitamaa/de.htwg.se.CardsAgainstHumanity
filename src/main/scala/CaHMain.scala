import module.CardsAgainstHumanityModule
import com.google.inject.Guice
import control.BaseImpl.Controller
import view.GUI.SwingGui
import view.Tui

import scala.io.StdIn.readLine

object CaHMain {
  val injector = Guice.createInjector(new CardsAgainstHumanityModule)
  val controller: Controller = injector.getInstance(classOf[Controller])
  val gui = new SwingGui(controller)
  val tui = new Tui(controller)

  def main(args: Array[String]): Unit = {
    var input: String = "0"

    gui.open()
    //tui.processInputLine(input)

    if (args.length>0) input=args(0)
    else do{
      input = readLine()
      tui.processInputLine(input)
    }while(input != "q")

  }
}
