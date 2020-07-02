package control.BaseImpl

import model.BaseImpl.{AnswerCard, QuestionCard}
import model.ModellInterface
import utils.Command

class AddCardsCommand(controller: Controller) extends Command{

  var memento: (ModellInterface, ControllerState) = (controller.gameManager, controller.state)

  var undoListQ = List[QuestionCard]();
  var undoListA = List[AnswerCard]();

  override def doStep: Unit = {memento=(controller.gameManager,controller.state)

    //if(cardText.contains("_")) {
      /*var tempList = controller.gameManager.questionListG
      undoListQ = tempList
      tempList = tempList :+ QuestionCard(cardText)
      controller.gameManager.questionList = tempList
      val newMemento = (controller.gameManager, controller.state)
      controller.ga
    } else {

      var tempList = controller.gameManager.answerList
      undoListA = tempList
      tempList = tempList :+ AnswerCard(cardText)
      controller.gameManager.answerList = tempList*/
   // }
  }

  override def undoStep: Unit = {
    val newMemento = (controller.gameManager, controller.state)
    controller.gameManager = memento._1
    controller.state = memento._2
    memento = newMemento
   /* if(cardText.contains("_"))
      controller.gameManager.questionList = undoListQ;
    else
      controller.gameManager.answerList = undoListA;*/
  }

  override def redoStep: Unit = doStep
}
