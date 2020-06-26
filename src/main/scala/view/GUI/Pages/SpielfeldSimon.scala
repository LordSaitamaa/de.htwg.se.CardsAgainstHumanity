package view.GUI.Pages

import java.awt.Color

import control._
import model.AnswerCard
import view.GUI.InfoBar

import scala.swing.event.{ButtonClicked, MouseClicked, SelectionChanged}
import scala.swing.{BorderPanel, BoxPanel, Button, Dimension, Label, ListView, Orientation, TextField}

class SpielfeldSimon(controller: Controller, infoBar: InfoBar) extends BorderPanel{

  preferredSize = new Dimension(790, 500)

  val submitBtn = new Button("Submit")
  val playerInfoLbl = new Label("")

  var antworten = new ListView[AnswerCard]()
  var beantwortete = new ListView[String]()

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
    contents += beantwortete
  }
  val panelKartenauswahl = new BoxPanel(Orientation.Vertical){
    background = Color.LIGHT_GRAY
    contents += antworten
  }

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

      antworten = new ListView(controller.gameManager.player(controller.gameManager.activePlayer).playerCards.toSeq)
      panelKartenauswahl.contents.update(0, antworten)
      panelKartenauswahl.revalidate()
      panelKartenauswahl.repaint()

      playerInfoLbl.text = "Aktiver Spieler: " + controller.gameManager.player(controller.gameManager.activePlayer).name

      panelRechts.contents.update(0, beantwortete)

    }
    case ButtonClicked(b) if b == submitBtn => {
      val index = antworten.selection.anchorIndex
      val antwort = controller.gameManager.roundAnswerCards.get(controller.gameManager.player(controller.gameManager.getActivePlayer()))

      var tmpList = List[String]()
      val a = controller.gameManager.roundAnswerCards.foreach(x => tmpList = tmpList :+ x._2)



      controller.eval(index.toString)
    }
  }
}
