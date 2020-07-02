package control

trait ControllerInterface {

  def changePage(page: Int)
  def eval(input: String)
  def stateAsString
  def getCurrentStateAsString
  def undo
  def redo
}
