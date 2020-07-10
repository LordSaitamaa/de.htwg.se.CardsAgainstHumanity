package view.GUI

import java.awt.{Color, Toolkit}

import view.GUI.Pages._

import scala.swing._
import control._
import javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE
import javax.swing.plaf.ColorUIResource
import javax.swing.plaf.metal.DefaultMetalTheme

import scala.swing.Swing.EmptyBorder
import scala.swing.event.Key

class SwingGui(controller: ControllerInterface) extends Frame {

  val infoBar = new InfoBar() {
    background = Color.BLACK
    foreground = Color.WHITE
    font = Font("System", Font.Bold, 18)
  }
  val startPage = new StartPage(controller, infoBar)
 // val secondPage = new cardsDialog(controller, infoBar)

  val playerDialog = new playerDialog(controller, infoBar)
  val screenSize = Toolkit.getDefaultToolkit.getScreenSize

  val mainPanel = new BoxPanel(Orientation.Vertical) {
    contents += startPage
    contents += infoBar
  }

  title = "HTWG - Cards against Humanity"
  preferredSize = new Dimension( screenSize.width, screenSize.height)
  resizable = false
  background = Color.BLACK
  peer.setUndecorated(true)

  peer.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE)

  this.contents = mainPanel

  def nextPage(page: Int): Unit = {

      page match{
        case 1 => {
          mainPanel.contents.update(0, startPage)
          this.validate()
        }
        case 2 => {
          val cardsDialog = new cardsDialog(controller, infoBar)
          cardsDialog.open()
        }
        case 3 => {

          playerDialog.open()
        }
        case 4 => {
          val spielfeld = new Spielfeld(controller, infoBar)
          mainPanel.contents.update(0, spielfeld)
          this.validate()
        }
      }
  }
  listenTo(controller)
  reactions += {
    case event: StartPageEvent => nextPage(1)
    case event: SecondPageEvent => nextPage(2)
    case event: ThirdPageEvent => nextPage(3)
    case event: NextStateEvent => nextPage(4)
  }

  menuBar = new MenuBar {
    background = Color.BLACK
    border = EmptyBorder
    contents += new Menu("Spiel") {
      mnemonic = Key.S

      contents += new MenuItem(Action("Quit") {System.exit(0)})
    }
  }
}

