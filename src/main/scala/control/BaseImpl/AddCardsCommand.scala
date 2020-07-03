package control.BaseImpl

import control.ControllerInterface
import model.BaseImpl.{AnswerCard, QuestionCard}
import utils.Command

class AddCardsCommand(cardText: String, controller: ControllerInterface) extends Command{

  var undoListQ = List[QuestionCard]();
  var undoListA = List[AnswerCard]();

  override def doStep: Unit = {
    if(cardText.contains("_")) {
      var tempList = controller.getGameManager.questionList
      undoListQ = tempList
      tempList = tempList :+ QuestionCard(cardText)
      controller.getGameManager.questionList = tempList
    } else {

      var tempList = controller.getGameManager.answerList
      undoListA = tempList
      tempList = tempList :+ AnswerCard(cardText)
      controller.getGameManager.answerList = tempList
    }
  }

  override def undoStep: Unit = {
    if(cardText.contains("_"))
      controller.getGameManager.questionList = undoListQ;
    else
      controller.getGameManager.answerList = undoListA;
  }

  override def redoStep: Unit = doStep
}
