package view.GUI.Pages

import control.BaseImpl.Controller
import control.{ControllerInterface, ThirdPageEvent, UndoEvent, UpdateGuiEvent, UpdateTuiEvent}
import view.GUI.InfoBar
import javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE

import scala.swing.ListView.Renderer
import scala.swing.{BoxPanel, Button, Dialog, Dimension, FlowPanel, ListView, Orientation, ScrollPane, TextField}
import scala.swing.event.ButtonClicked

class cardsDialog(controller: ControllerInterface, infobar: InfoBar) extends Dialog {

  preferredSize = new Dimension(350, 150)
  title = "Wollen Sie Karten hinzufügen?"
  peer.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE)
  resizable = false


  val kartenNameTf = new TextField("Kartenname")
  val addKarteBtn = new Button("Add")
  val weiterBtn = new Button("Weiter")
  val addedCardsTf =  new TextField()//new FlowPanel(new ScrollPane(new ListView(controller.gameManager.answerList){}))
  val undoButton = new Button("undo")

  val flowPanel = new FlowPanel() {
    contents += addKarteBtn
    contents += weiterBtn
  }

  this.contents = new BoxPanel(Orientation.Vertical) {
    this.contents += kartenNameTf
    this.contents += flowPanel
    this.contents += addedCardsTf
  }

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
        addedCardsTf.text = controller.getGameManager.answerList.toString()

      }
    }
    case ButtonClicked(b) if b==weiterBtn=> {
      controller.eval("weiter")
      this.close()
    }
    case event: UndoEvent => addedCardsTf.text= controller.getGameManager.answerList.toString()
    case event: UpdateGuiEvent => {
      this.validate()
      addedCardsTf.text = controller.getGameManager.answerList.toString()
    }
    case event: ThirdPageEvent => this.close()
  }
}
