package view.GUI

import java.awt.Color

import scala.swing.Swing.EmptyBorder
import scala.swing.{Alignment, Dimension, Font, TextField}

class InfoBar extends TextField{

  foreground = Color.GREEN;
  preferredSize = new Dimension(790, 75)
  editable = false
  background = Color.BLACK
  border = EmptyBorder
  font = Font("System", Font.Bold, 18)
  horizontalAlignment = Alignment.Center
}
