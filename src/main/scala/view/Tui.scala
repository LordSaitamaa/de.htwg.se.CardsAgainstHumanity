package view

import java.util.{Observable, Observer}

import control.Controller


class Tui(var contoller:Controller) extends Observer{
  override def update(o: Observable, arg: Any): Unit = ???
}
