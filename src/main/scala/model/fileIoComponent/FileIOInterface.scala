package model.fileIoComponent

import control.ControllerInterface
import model.ModelInterface

trait FileIOInterface {
  def load: ModelInterface

  def save(game: ModelInterface): Unit
}
