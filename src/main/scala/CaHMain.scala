import control.Controller
import model.{GameManager}
import view.Tui
import scala.io.StdIn.readLine

object CaHMain {
  val controller = new Controller(GameManager())
  val tui = new Tui(controller)

  def main(args: Array[String]): Unit = {
    var input: String = ""
    if (args.length>0) input=args(0)
    else do{
      input = readLine()
      tui.processInputLine(input)
    }while(input != "q")

  }
}
