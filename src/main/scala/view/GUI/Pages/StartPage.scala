package view.GUI.Pages

import java.awt.{Color, Toolkit}

import scala.swing._
import scala.swing.{Action, BorderPanel, Button, ComboBox, Dimension, FlowPanel, Label, Menu, MenuItem, TextField}
import control.ControllerInterface
import javax.swing.ImageIcon
import scala.swing.Swing.EmptyBorder
import view.GUI.InfoBar

import scala.swing.event._

class StartPage(controller: ControllerInterface, infobar: InfoBar) extends BorderPanel {

  val startBtn = new Button("Spiel starten"){
    background = Color.BLACK
    foreground = Color.WHITE
    preferredSize = new Dimension(200, 50)
    font = Font("System", Font.Bold, 18)
    border = EmptyBorder
  }
  val screenSize = Toolkit.getDefaultToolkit.getScreenSize
  /*val titleLbl = new Label("CARDS AGAINST THE HUMANITY") {
    font = new Font("Arial-Black", 3, 30)
  }
   */
  val anzahlSpielerCb = new ComboBox(List(2, 3, 4))
  listenTo(controller)

  def mainPanel = new FlowPanel {


    background = Color.BLACK
    private val pic1 = new ImageIcon("src/main/images/CaH.png").getImage
    private val resize = pic1.getScaledInstance(900, 400, java.awt.Image.SCALE_SMOOTH)
    private val pic2 = new ImageIcon("src/main/images/aGame.png").getImage
    private val resize2 = pic2.getScaledInstance(900, 250, java.awt.Image.SCALE_SMOOTH)
    contents += new Label{
      icon = new ImageIcon(pic1)
    }
    contents += new FlowPanel{

      background = Color.BLACK
      foreground = Color.WHITE
      font = Font("System", Font.Bold, 18)
      preferredSize = new Dimension(screenSize.width, 50)
      maximumSize = new Dimension(screenSize.width, 50)
      contents += startBtn
      contents += anzahlSpielerCb
    }
    contents += new Label{
      icon = new ImageIcon(pic2)
    }

  }

  //add(titleLbl, BorderPanel.Position.North)
  add(mainPanel, BorderPanel.Position.Center)

  infobar.text = "Spielerzahl auswählen!"
  preferredSize = new Dimension(790, 500)

  listenTo(startBtn)

  reactions += {
    case ButtonClicked(startBtn) => {

      if(anzahlSpielerCb.item == 0) {
        infobar.background = Color.RED;
        infobar.foreground = Color.WHITE;
        infobar.text = "Spielerzahl darf nicht 0 sein!"
      } else {
        infobar.background = Color.BLACK;
        infobar.foreground = Color.WHITE;
        infobar.font = Font("System", Font.Bold, 18)
        controller.eval(anzahlSpielerCb.item.toString)
        infobar.text = controller.getCurrentStateAsString()
      }
    }
  }
}