package control.BaseImpl

import control.ControllerInterface
import model.BaseImpl.{AnswerCard, Player}
import utils.Command

class AddPlayersCommand(name: String, controller: ControllerInterface) extends Command{

  override def doStep: Unit = {

    var playerTmp = controller.getGameManager.player
    playerTmp = playerTmp :+ Player(name, true, List[AnswerCard]())
    controller.getGameManager.player = playerTmp
  }

  override def undoStep: Unit = {

    var playerTmp = controller.getGameManager.player;
    playerTmp = playerTmp.filterNot(_==playerTmp.last)
    controller.getGameManager.player = playerTmp
  }

  override def redoStep: Unit = doStep
}
