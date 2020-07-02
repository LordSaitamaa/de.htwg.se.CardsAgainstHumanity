package view.GUI.Pages

import java.awt.Color

import control.BaseImpl.Controller

import scala.swing._
import scala.swing.{Action, BorderPanel, Button, ComboBox, Dimension, FlowPanel, Label, Menu, MenuItem, TextField}
import control.SecondPageEvent
import javax.swing.WindowConstants.{DO_NOTHING_ON_CLOSE, EXIT_ON_CLOSE}
import view.GUI.InfoBar

import scala.swing.event.{ButtonClicked, Key}

class StartPage(controller: Controller, infobar: InfoBar) extends BorderPanel {

  val startBtn = new Button("Spiel starten")
  val titleLbl = new Label("CARDS AGAINST THE HUMANITY") {
    font = new Font("Arial-Black", 3, 30)
  }
  val anzahlSpielerCb = new ComboBox(List(2, 3, 4))
  listenTo(controller)

  def mainPanel = new FlowPanel {

    background = Color.LIGHT_GRAY

    contents += startBtn
    contents += anzahlSpielerCb
  }

  add(titleLbl, BorderPanel.Position.North)
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

          infobar.background = Color.WHITE;
          infobar.foreground = Color.BLACK;
          controller.eval(anzahlSpielerCb.item.toString)
          infobar.text = controller.getCurrentStateAsString()
        }

    }
  }
}
