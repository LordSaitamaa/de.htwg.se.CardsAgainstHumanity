package control.BaseImpl

import model.BaseImpl.{AnswerCard, Player}
import model.ModellInterface
import utils.Command

class AddPlayersCommand(controller: Controller) extends Command{
  var memento: (ModellInterface, ControllerState) = (controller.gameManager, controller.state)
  override def doStep: Unit = {memento = (controller.gameManager, controller.state)

   /* var playerTmp = controller.gameManager.playerG
    playerTmp = playerTmp :+ Player(name, true, List[AnswerCard]())
    controller.gameManager.playerG = playerTmp*/
  }

  override def undoStep: Unit = {
    val newMemento = (controller.gameManager, controller.state)
    controller.gameManager = memento._1
    controller.state = memento._2
    memento = newMemento
    /*var playerTmp = controller.gameManager.playerG;
    playerTmp = playerTmp.filterNot(_==playerTmp.last)
    controller.gameManager.player = playerTmp*/
  }

  override def redoStep: Unit = doStep
}
