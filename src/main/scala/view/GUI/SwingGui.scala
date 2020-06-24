package view.GUI

import view.GUI.Pages._

import scala.swing.{Action, BoxPanel, Dimension, Frame, Menu, MenuBar, MenuItem, Orientation}
import control._

import scala.swing.event.Key

class SwingGui(controller: Controller) extends Frame {

  val infoBar = new InfoBar()
  val startPage = new StartPage(controller, infoBar)
  val secondPage = new SecondPage(controller, infoBar)

  val mainPanel = new BoxPanel(Orientation.Vertical) {
    contents += startPage
    contents += infoBar
  }

  title = "HTWG - Cards against Humanity"
  preferredSize = new Dimension(800, 600)

  this.contents = mainPanel

  def nextPage(page: Int): Unit = {

      page match{
        case 1 => {
          mainPanel.contents.update(0, startPage)
          this.validate()
        }
        case 2 => {
          mainPanel.contents.update(0, new SecondPage(controller, infoBar))
          this.validate()
        }
        case 3 => {
          mainPanel.contents.update(0, new ThirdPage(controller, infoBar))
          mainPanel.contents.head.requestFocus()
          this.validate()
        }
      }
  }
  listenTo(controller)
  reactions += {
    case event: StartPageEvent => nextPage(1)
    case event: SecondPageEvent => nextPage(2)
    case event: ThirdPageEvent => nextPage(3)
  }

  menuBar = new MenuBar {
    contents += new Menu("Spiel") {
      mnemonic = Key.F
      contents += new MenuItem(Action("Undo") {controller.undo})
      contents += new MenuItem(Action("Redo") {controller.redo})
      contents += new MenuItem(Action("Quit") {System.exit(0)})
    }
  }
}
