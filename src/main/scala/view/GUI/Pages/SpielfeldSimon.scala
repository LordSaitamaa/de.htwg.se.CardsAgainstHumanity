package view.GUI.Pages

import java.awt.{Color}

import control._
import view.GUI.InfoBar

import scala.swing.event.{ButtonClicked, MouseClicked, SelectionChanged}
import scala.swing.{BorderPanel, BoxPanel, Button, Dimension, ListView, Orientation, TextField, Label}

class SpielfeldSimon(controller: Controller, infoBar: InfoBar) extends BorderPanel{

  preferredSize = new Dimension(790, 500)

  val submitBtn = new Button("Submit")
  val playerInfoLbl = new Label("")

  val panelRundenInfo = new BoxPanel(Orientation.Vertical){
    background = Color.GREEN

    contents += playerInfoLbl
  }
  val panelControllers = new BoxPanel(Orientation.Vertical){
    background = Color.BLUE

    contents += submitBtn
  }
  val panelLinks = new BoxPanel(Orientation.Vertical){
    background = Color.RED
  }
  val panelRechts = new BoxPanel(Orientation.Vertical){
    background = Color.YELLOW
  }
  val panelKartenauswahl = new BoxPanel(Orientation.Vertical){
    background = Color.LIGHT_GRAY
  }

  var viewList = new ListView[Any]()


  add(panelRundenInfo, BorderPanel.Position.North)
  add(panelControllers, BorderPanel.Position.South)
  add(panelLinks, BorderPanel.Position.West)
  add(panelRechts, BorderPanel.Position.East)
  add(panelKartenauswahl, BorderPanel.Position.Center)

  listenTo(controller)
  listenTo(submitBtn)

  reactions += {
    case event: UpdateInfoBarEvent => infoBar.text = controller.getCurrentStateAsString()
    case event: UpdateGuiEvent => {

      viewList = new ListView(controller.gameManager.player(controller.gameManager.activePlayer).playerCards.toSeq)
      panelKartenauswahl.contents += viewList
      panelKartenauswahl.revalidate()

      playerInfoLbl.text = "Aktiver Spieler: " + controller.gameManager.player(controller.gameManager.activePlayer).name
    }
    case ButtonClicked(b) if b == submitBtn => {
      controller.eval(viewList.selection.items(0).toString)
    }
  }

}
