package view.GUI.Pages

import control.{ControllerInterface, ThirdPageEvent, UndoEvent, UpdateGuiEvent, UpdateTuiEvent}
import view.GUI.InfoBar
import javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE
import model.BaseImpl.{AnswerCard, Card}

import scala.swing.{BoxPanel, Button, Dialog, Dimension, FlowPanel, ListView, Orientation, ScrollPane, TextField}
import scala.swing.event.ButtonClicked

class cardsDialog(controller: ControllerInterface, infobar: InfoBar) extends Dialog {

  preferredSize = new Dimension(350, 300)
  title = "Wollen Sie Karten hinzufügen?"
  peer.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE)
  resizable = false

  val kartenNameTf = new TextField("Kartenname")


  val addKarteBtn = new Button("Add")
  val weiterBtn = new Button("Weiter")
  //val addedCardsTf =  new TextField()//new FlowPanel(new ScrollPane(new ListView(controller.gameManager.answerList){}))
  val undoButton = new Button("undo")
  var tmpList = List[String]()
  controller.getGameManager.kompositumCard.cardList.foreach(x => tmpList = tmpList :+ x.toString)
  println("Test: ", tmpList)
  var karten = new ScrollPane(new ListView[String](tmpList))
  karten.preferredSize = new Dimension(350,200)

  val flowPanel = new FlowPanel() {
    contents += addKarteBtn
    contents += weiterBtn
  }

  this.contents = new BoxPanel(Orientation.Vertical) {
    this.contents += kartenNameTf
    this.contents += flowPanel
    this.contents += karten
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
        var tmpList = List[String]()
        controller.getGameManager.kompositumCard.cardList.foreach(x => tmpList = tmpList :+ x.toString)
        println("Test 2: ", tmpList)

        //karten = new ScrollPane(new ListView[String](tmpList))
        //addedCardsTf.text = controller.getGameManager.kompositumCard.toString()
        karten.revalidate()
        karten.repaint
      }
    }
    case ButtonClicked(b) if b==weiterBtn=> {
      controller.eval("weiter")
      this.close()
    }
    //case event: UndoEvent => addedCardsTf.text= controller.getGameManager.kompositumCard.toString()
    case event: UpdateGuiEvent => {
      var tmpList = List[String]()
      controller.getGameManager().kompositumCard.cardList.foreach(x => tmpList = tmpList :+ x.toString)
      karten = new ScrollPane(new ListView[String](tmpList))
      contents.updated(0,karten)
      karten.revalidate()
      karten.repaint()
    }
    case event: ThirdPageEvent => this.close()
  }
}
