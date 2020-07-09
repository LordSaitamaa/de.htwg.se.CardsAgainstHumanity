package model.fileIoComponent

import control.ControllerInterface
import model.ModelInterface

trait FileIOInterface {
  def load(gameMan: ModelInterface): ModelInterface
  def save(game: ModelInterface): Unit
}
