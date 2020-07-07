package model.fileIoComponent

import model.ModelInterface

trait FileIOInterface {
  def load(gameMan: ModelInterface): ModelInterface
  def save(game: ModelInterface): Unit
}
