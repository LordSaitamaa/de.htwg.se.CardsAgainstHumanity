package view.GUI.Pages

import java.awt.{Color}
import scala.swing._

import scala.swing.{Action, BorderPanel, Button, ComboBox, Dimension, FlowPanel, Label, Menu, MenuItem, TextField}
import control.{Controller, SecondPageEvent}
import view.GUI.InfoBar

import scala.swing.event.{ButtonClicked, Key}

class StartPage(controller: Controller, infobar: InfoBar) extends BorderPanel {

  val startBtn = new Button("Spiel starten")
  val anzahlSpielerCb = new ComboBox(List(0, 2, 3, 4))

  listenTo(controller)

  def mainPanel = new FlowPanel {
    contents += startBtn
    contents += anzahlSpielerCb
  }

  add(new Label("Cards against the humanity"), BorderPanel.Position.North)
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
