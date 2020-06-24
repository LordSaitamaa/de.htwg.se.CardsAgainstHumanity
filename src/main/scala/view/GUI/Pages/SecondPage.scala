package view.GUI.Pages

import java.awt.Color

import control.{Controller, UndoEvent}
import javax.naming.spi.DirectoryManager
import view.GUI.InfoBar

import scala.swing.event.ButtonClicked
import scala.swing.{BoxPanel, Button, Dimension, FlowPanel, Orientation, TextField}

class SecondPage(controller: Controller, infobar: InfoBar) extends BoxPanel(Orientation.Vertical) {

  val kartenNameTf = new TextField("Kartenname") {
    preferredSize = new Dimension(200, 50)
  }
  val addKarteBtn = new Button("Add") {
    preferredSize = new Dimension(200, 50)
  }

  val weiterBtn = new Button("Weiter") {
    preferredSize = new Dimension(200, 50)
  }

  val addedCardsTf = new TextField(controller.gameManager.answerList.toString())

  val flowPanel = new FlowPanel() {
    contents += addKarteBtn
    contents += weiterBtn
  }

  this.contents += kartenNameTf
  this.contents += flowPanel
  this.contents += addedCardsTf

  preferredSize = new Dimension(790, 500)

  listenTo(controller)
  listenTo(addKarteBtn)
  listenTo(weiterBtn)

  reactions += {
    case ButtonClicked(b) if b==addKarteBtn => {
      if (kartenNameTf.text.equals("") || kartenNameTf.text.equals("Kartenname")) {

        infobar.text = "Bitte gib zuerst eine Karte ein."
      } else {
        controller.eval(kartenNameTf.text)
        kartenNameTf.text = ""
        addedCardsTf.text = controller.gameManager.answerList.toString()

      }
    }
    case ButtonClicked(b) if b==weiterBtn=> {
      controller.eval("weiter")
    }
    case event: UndoEvent => addedCardsTf.text= controller.gameManager.answerList.toString()
  }
}
