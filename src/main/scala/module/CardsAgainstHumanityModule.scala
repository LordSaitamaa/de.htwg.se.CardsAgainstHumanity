package module


import com.google.inject.AbstractModule
import model.BaseImpl.GameManager
import model.ModelInterface
import model.fileIoComponent.FileIOInterface
import model.fileIoComponent.fileIoJsonImpl.FileIO
import net.codingwell.scalaguice.ScalaModule

class CardsAgainstHumanityModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[ModelInterface].toInstance(GameManager())
    bind[FileIOInterface].to[FileIO]
  }

}
