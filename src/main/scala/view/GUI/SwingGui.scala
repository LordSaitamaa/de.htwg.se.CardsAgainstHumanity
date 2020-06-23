package view.GUI

import view.GUI.Pages._
import scala.swing.{BoxPanel, Dimension, Frame, Orientation}
import control._

class SwingGui(controller: Controller) extends Frame {

  val infoBar = new InfoBar()
  val startPage = new StartPage(controller, infoBar)
  val secondPage = new SecondPage(controller, infoBar)
  val thirdPage = new ThirdPage(controller, infoBar)
  var mainPanel = new BoxPanel(Orientation.Vertical) {
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
          mainPanel.contents.update(0, secondPage)
          this.validate()
        }
        case 3 => {
          mainPanel.contents.update(0, thirdPage)
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
}
