package control

import model.BaseImpl.GameManager
import model.ModellInterface

import scala.swing.Publisher

trait ControllerInterface extends Publisher{

  def changePage(page: Int): Unit
  def eval(input: String)
  def stateAsString: String
  def getCurrentStateAsString: String
  def gameManagerG(): GameManager
  def undo: Unit
  def redo: Unit

}

import scala.swing.event.Event

class StartPageEvent extends Event
class SecondPageEvent extends Event
class ThirdPageEvent extends Event
class UpdateGuiEvent extends Event
class UpdateTuiEvent extends Event
class UndoEvent extends Event
class NextStateEvent extends Event
class UpdateInfoBarEvent extends Event
