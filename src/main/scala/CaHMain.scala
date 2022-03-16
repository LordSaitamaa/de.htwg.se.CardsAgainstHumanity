import module.CardsAgainstHumanityModule
import com.google.inject.{Guice, Injector}
import control.BaseImpl.Controller
import view.GUI.SwingGui
import view.Tui

import scala.io.StdIn.readLine

object CaHMain {
  val injector: Injector = Guice.createInjector(new CardsAgainstHumanityModule)
  val controller: Controller = injector.getInstance(classOf[Controller])
  val gui = new SwingGui(controller)
  val tui = new Tui(controller)

  def main(args: Array[String]): Unit = {
    var input: String = "0"

    gui.open()

    if (args.length>0) input=args(0)
    else do{
      input = readLine()
      tui.processInputLine(input)
    }while(input != "q")

  }
}
