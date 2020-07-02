package view.GUI.Pages

import java.awt.Color

import control.BaseImpl.Controller
import control.{ControllerInterface, UpdateGuiEvent,UpdateInfoBarEvent}
import model.BaseImpl.AnswerCard
import view.GUI.InfoBar
import scala.swing.event._

import scala.swing.{BorderPanel, BoxPanel, Button, Dimension, Label, ListView, Orientation, TextField}


class Spielfeld(controller: ControllerInterface, infoBar: InfoBar) extends BorderPanel{

  listenTo(controller)

  preferredSize = new Dimension(790, 500)
  background = Color.GREEN


  val submitBtn = new Button("Submit / Next Question")
  val nextQuestBtn = new Button("Next Question")
  val playerInfoLbl = new Label("")
  val endString = new Label("Spiel zu Ende")
  endString.foreground = Color.WHITE
  endString.visible = false

  var antworten = new ListView[AnswerCard]()
  var beantwortete = new ListView[String]()

  val panelRundenInfo = new BoxPanel(Orientation.Vertical){
    background = Color.LIGHT_GRAY
    contents += playerInfoLbl
  }
  val panelControllers = new BoxPanel(Orientation.Vertical){
    background = Color.LIGHT_GRAY

    contents += submitBtn
  }
  val panelLinks = new BoxPanel(Orientation.Vertical){
    background = Color.LIGHT_GRAY
    contents += antworten
  }
  val panelRechts = new BoxPanel(Orientation.Vertical){
    background = Color.BLACK
    contents += beantwortete
  }
  val panelKartenauswahl = new BoxPanel(Orientation.Vertical){
      contents += endString
    background = Color.BLACK
  }

  add(panelRundenInfo, BorderPanel.Position.North)
  add(panelControllers, BorderPanel.Position.South)
  add(panelLinks, BorderPanel.Position.West)
  add(panelRechts, BorderPanel.Position.East)
  add(panelKartenauswahl, BorderPanel.Position.Center)

  listenTo(submitBtn)
  listenTo(nextQuestBtn)

  reactions += {
    case event: UpdateInfoBarEvent => infoBar.text = controller.getCurrentStateAsString

    case event: UpdateGuiEvent => {
      var tmpList = List[String]()
      controller.gameManagerG().roundAnswerCardG().foreach(x => tmpList = tmpList :+ "Spieler " + x._1.name + " hat " + x._2)
      beantwortete = new ListView[String](tmpList)
      panelRechts.revalidate()
      panelRechts.repaint()

      antworten = new ListView(controller.gameManagerG().playerG(controller.gameManagerG().activePlayerG()).playerCards.toSeq)
      panelLinks.contents.update(0, antworten)
      panelLinks.revalidate()
      panelLinks.repaint()

      playerInfoLbl.text = "Aktiver Spieler: " + controller.gameManagerG().playerG(controller.gameManagerG().activePlayerG()).name

      panelRechts.contents.update(0, beantwortete)

    }
    case ButtonClicked(b) if b == submitBtn => {
      if(controller.gameManagerG().numberOfRound > controller.gameManagerG().numberOfPlayableRound)
        endString.visible = true

        val index = antworten.selection.anchorIndex
        controller.eval(index.toString)

    }
    case ButtonClicked(b) if b == nextQuestBtn => {
      controller.eval("")
    }
  }
}
