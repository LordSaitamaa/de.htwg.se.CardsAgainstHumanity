package control

import model.BaseImpl.{AnswerCard, KompositumCard, Player, QuestionCard}

import scala.swing.Publisher

trait ControllerInterface extends Publisher{

  def changePage(page: Int)
  def eval(input: String)
  def stateAsString: String
  def getCurrentStateAsString: String
  def undo
  def redo
}
