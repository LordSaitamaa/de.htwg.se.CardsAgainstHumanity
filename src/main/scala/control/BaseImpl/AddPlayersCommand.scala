package control.BaseImpl

import model.BaseImpl.{AnswerCard, Player}
import utils.Command

class AddPlayersCommand(name: String, controller: Controller) extends Command{

  override def doStep: Unit = {

    var playerTmp = controller.gameManager.playerG
    playerTmp = playerTmp :+ Player(name, true, List[AnswerCard]())
    controller.gameManager.playerG = playerTmp
  }

  override def undoStep: Unit = {

    var playerTmp = controller.gameManager.playerG;
    playerTmp = playerTmp.filterNot(_==playerTmp.last)
    controller.gameManager.player = playerTmp
  }

  override def redoStep: Unit = doStep
}
