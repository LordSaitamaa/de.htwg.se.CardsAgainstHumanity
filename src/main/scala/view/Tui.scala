package view
import utils.{Observable, Observer}
import control.Controller


class Tui(var contoller:Controller) extends Observer{
   override def update(): Unit = ()
}
