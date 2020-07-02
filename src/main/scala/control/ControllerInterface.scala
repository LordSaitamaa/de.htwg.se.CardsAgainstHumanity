package control
import model.BaseImpl.GameManager

import scala.swing.Publisher

trait ControllerInterface extends Publisher{

  def nextState()
  def changePage(page: Int)
  def eval(input: String)
  def stateAsString(): String
  def getCurrentStateAsString() : String
  def undo
  def redo
  def getGameManager() : GameManager
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

