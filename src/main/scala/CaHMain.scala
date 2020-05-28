import control.Controller
import model.{SetupGame}
import view.Tui
import scala.io.StdIn.readLine

object CaHMain {
  val controller = new Controller(SetupGame(null,null,null, null, null, null, null))
  val tui = new Tui(controller)
  def main(args: Array[String]): Unit = {
    var input: String = ""
    do{
      input = readLine()
      tui.processInputLine(input)
    }while(input != "q")

  }
}
