package view.GUI.Pages

import java.awt.Color
import control.{ControllerInterface, NextStateEvent, StartPageEvent, UpdateGuiEvent, UpdateInfoBarEvent}
import javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE
import view.GUI.InfoBar

import scala.swing.event.ButtonClicked
import scala.swing.{BoxPanel, Button, Dialog, Dimension, FlowPanel, Orientation, TextField}

class playerDialog(controller: ControllerInterface, infobar: InfoBar) extends Dialog {

  title = "Spielernamen eingeben"
  resizable = false
  preferredSize = new Dimension(250, 150)
  peer.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE)

  val playerTf = new TextField("Spieler")
  val weiterButton = new Button("-->")
  val undoButton = new Button("undo")
  val infoTf = new TextField("") {
    editable = false
  }

  contents = new BoxPanel(Orientation.Vertical) {

    contents += playerTf
    contents += new FlowPanel() {
      contents += undoButton
      contents += weiterButton
    }
    contents += infoTf
  }
  listenTo(undoButton)
  listenTo(weiterButton)
  listenTo(controller)

  reactions += {
    case ButtonClicked(b) if b == undoButton => {
      controller.undo
      infoTf.text = controller.getGameManager.player.toString()
    }
    case ButtonClicked(b) if b == weiterButton => {
      if(playerTf.text.equals("Spieler")) {
        playerTf.background = Color.RED
        infoTf.text = controller.getGameManager.player.toString()
        infobar.text = controller.getGameManager.player.toString()
        playerTf.validate()
      } else {
        controller.eval(playerTf.text)
        playerTf.background = Color.WHITE
        infoTf.text = controller.getGameManager.player.toString()
        playerTf.text = "Spieler"
      }
    }
    case event: NextStateEvent => {
      controller.eval("")
      this.close()
    }
    case event : UpdateInfoBarEvent => {
      infoTf.text = controller.getGameManager.player.toString()
    }
  }
}