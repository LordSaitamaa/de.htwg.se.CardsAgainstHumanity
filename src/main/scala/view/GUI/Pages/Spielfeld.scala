package view.GUI.Pages

import java.awt.Color
import java.net.NoRouteToHostException

import scala.swing.BorderPanel._
import control.Controller
import view.GUI.InfoBar

import scala.swing.{BorderPanel, BoxPanel, Button, Dimension, FlowPanel, Orientation}

class Spielfeld(controller: Controller, infoBar: InfoBar) extends BorderPanel {

  preferredSize = new Dimension(750, 590)

  val panelNorth = new BoxPanel(Orientation.Vertical) {
    background = Color.RED
    contents += new Button("Test")
  }
  val panelSouth = new BoxPanel(Orientation.Vertical) {
    background = Color.GREEN
    contents += new Button("Test")
  }
  val panelWest = new BoxPanel(Orientation.Vertical) {
    background = Color.BLUE
    contents += new Button("Test")
  }
  val panelEast = new BoxPanel(Orientation.Vertical) {
    background = Color.YELLOW
    contents += new Button("Test")
  }
  val panelCenter = new BoxPanel(Orientation.Vertical) {
    background = Color.PINK
    contents += new Button("Test")
  }

  add(panelNorth, BorderPanel.Position.North)
  add(panelSouth, BorderPanel.Position.South)
  add(panelWest, BorderPanel.Position.West)
  add(panelEast, BorderPanel.Position.East)
  add(panelCenter, BorderPanel.Position.Center)
}
