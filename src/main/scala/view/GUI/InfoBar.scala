package view.GUI

import scala.swing.{Alignment, Dimension, FlowPanel, Orientation, TextField}

class InfoBar extends TextField{

  preferredSize = new Dimension(790, 100)
  editable = false
  horizontalAlignment = Alignment.Center
}