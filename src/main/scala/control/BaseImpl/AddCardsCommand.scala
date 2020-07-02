package control.BaseImpl

import model.BaseImpl.{AnswerCard, QuestionCard}
import utils.Command

class AddCardsCommand(cardText: String, controller: Controller) extends Command{

  var undoListQ = List[QuestionCard]();
  var undoListA = List[AnswerCard]();

  override def doStep: Unit = {
    if(cardText.contains("_")) {
      var tempList = controller.gameManager.questionList
      undoListQ = tempList
      tempList = tempList :+ QuestionCard(cardText)
      controller.gameManager.questionList = tempList
    } else {

      var tempList = controller.gameManager.answerList
      undoListA = tempList
      tempList = tempList :+ AnswerCard(cardText)
      controller.gameManager.answerList = tempList
    }
  }

  override def undoStep: Unit = {
    if(cardText.contains("_"))
      controller.gameManager.questionList = undoListQ;
    else
      controller.gameManager.answerList = undoListA;
  }

  override def redoStep: Unit = doStep
}
