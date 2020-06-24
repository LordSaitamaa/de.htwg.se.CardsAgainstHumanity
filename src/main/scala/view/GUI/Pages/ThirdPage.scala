package view.GUI.Pages

import view.GUI.InfoBar
import control._

import scala.swing.event.ButtonClicked
import scala.swing.{BorderPanel, BoxPanel, Button, Dimension, FlowPanel, Orientation, TextField}

class ThirdPage(controller: Controller, infobar: InfoBar) extends BoxPanel(Orientation.Vertical) {

  preferredSize = new Dimension(790, 500)
  infobar.text = controller.getCurrentStateAsString()

  val weiterBtn = new Button("Weiter")

  for( a <- 1 to controller.gameManager.numberOfPlayers) {

    contents += new TextField("Spieler" + a)
  }

  listenTo(controller)
  listenTo(weiterBtn)

  contents += weiterBtn

  reactions += {
    case ButtonClicked(b) if b == weiterBtn => {

    }
  }



}
