package control

import model.{AnswerCard, Player}
import utils.Command

class AddPlayersCommand(name: String, controller: Controller) extends Command{

  override def doStep: Unit = {

    var playerTmp = controller.gameManager.player
    playerTmp = playerTmp :+ Player(name, true, List[AnswerCard]())
    controller.gameManager.player = playerTmp
  }

  override def undoStep: Unit = {

    var playerTmp = controller.gameManager.player;
    playerTmp = playerTmp.filterNot(_==playerTmp.last)
    controller.gameManager.player = playerTmp
  }

  override def redoStep: Unit = doStep
}
