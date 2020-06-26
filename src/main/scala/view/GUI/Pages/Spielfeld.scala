package view.GUI.Pages

import java.awt.{Button, Color, FlowLayout}

import control._
import view.GUI.InfoBar
import scala.swing.event.MouseClicked
import scala.swing.{BoxPanel, Dimension, GridPanel, Label, ListView, Orientation, Swing}

class Spielfeld(controller: Controller, infoBar: InfoBar) extends BoxPanel(Orientation.Vertical) {

  preferredSize = new Dimension(790, 500)
  background = Color.BLACK

  val submitBtn = new Button("Submit")

  var listString = List[String]()


  listenTo(controller)

  for (x <- controller.gameManager.player(controller.gameManager.activePlayer).playerCards) {
    listString = listString :+ x.antwort
  }

  var playedCard = 0

  contents += new BoxPanel(Orientation.Horizontal) {
    background = Color.GREEN
    contents += new Label("Player: " + controller.gameManager.activePlayer + 1)
    contents += Swing.HGlue
    contents += new Label("Round " + controller.gameManager.numberOfRounds)
  }

  contents += Swing.HGlue

  contents += new ListView[String](listString) {
    listenTo(mouse.clicks)

    reactions += {
      case b: MouseClicked if b.clicks == 2 =>
        playedCard = selection.anchorIndex
        controller.eval(playedCard.toString)           // Hier verstehe ich nicht genau, wie der AnswerState funktioniert
        // infoBar.text =        an dieser Stelle muss die beantwortete Frage mit dem jeweiligen Spieler in die infoBar unten kommen.
    }
  }

  contents += Swing.HGlue

  contents += new GridPanel(controller.gameManager.player.length, 1) {

    background = Color.BLUE


    /*for (i <- controller.gameManager.roundAnswerCards) {
      contents += new Label("Spieler " + i._1.name + " hat " + i._2 + " gespielt")
    }

     */
  }

  contents += Swing.HGlue

  reactions += {
    case event: UpdateInfoBarEvent => infoBar.text = controller.getCurrentStateAsString()
  }

  /*val panelCenter = new BoxPanel(Orientation.Vertical) {
    background = Color.PINK
    contents += Swing.HStrut(150)
    contents += Swing.VStrut(75)
    contents += startGameBtn
    contents += Swing.HGlue
  }
  val panelNorth = new BoxPanel(Orientation.Vertical) {
    background = Color.RED
    contents += new ListView[String](ListOfListViews.head){
      Orientation.Vertical
    }
    contents += new Button("Test")
  }
  val panelSouth = new BoxPanel(Orientation.Vertical) {
    background = Color.GREEN
      contents += new ListView[String](ListOfListViews(1)){
        Orientation.Horizontal
      }
    contents += new Button("Test")
  }
  val panelWest = new BoxPanel(Orientation.Vertical) {
    background = Color.BLUE
    if(controller.gameManager.player.size < 3) {
      ListOfListViews :+ new ListView[String](List[String]("Nicht im Spiel"))
    } else
      contents +=new ListView[String](ListOfListViews(2)){
      Orientation.Vertical
    }
    contents += new Button("Test")
  }
  val panelEast = new BoxPanel(Orientation.Vertical) {
    background = Color.YELLOW
    if(controller.gameManager.player.size < 4)
      ListOfListViews :+ new ListView[String](List[String]("Nicht im Spiel"))
    else
      contents += new ListView[String](ListOfListViews(3)){
        Orientation.Vertical
      }
    contents += new Button("Test")
  }*/

  //add(panelNorth, BorderPanel.Position.North)
  //add(panelSouth, BorderPanel.Position.South)
  // add(panelWest, BorderPanel.Position.West)
  //add(panelEast, BorderPanel.Position.East)
  // add(panelCenter, BorderPanel.Position.Center)
}
