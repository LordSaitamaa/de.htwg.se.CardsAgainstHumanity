package model.fileIoComponent

import model.ModelInterface

trait FileIOInterface {
  def load: ModelInterface

  def save(game: ModelInterface): Unit
}
