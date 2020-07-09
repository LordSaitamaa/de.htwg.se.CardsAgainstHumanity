package control.BaseImpl

import control.ControllerInterface
import model.BaseImpl.{AnswerCard, Card, KompositumCard, QuestionCard}
import utils.Command

class AddCardsCommand(cardText: String, controller: ControllerInterface) extends Command{

  var undoKompositum = List[Card]()

  override def doStep: Unit = {

      var tempList = controller.getGameManager().kompositumCard.cardList
      var doubleCheck : Boolean = true;
      undoKompositum = tempList

    for(x <- tempList) {
      if(x.toString.equals(cardText)) {
        doubleCheck = false
      }
    }

    if(doubleCheck) {

      if(cardText.contains("_")) {
        tempList = tempList :+ QuestionCard(cardText)
        println("Question hinzugefügt" + tempList)
      } else {
        tempList = tempList :+ AnswerCard(cardText)
        println("Answer hinzugefügt" + tempList)
      }
    }

    var kompCard = KompositumCard(tempList)
    println("TempList: " + tempList)

    controller.getGameManager().setKompositum(kompCard)

    println("Fehelerhaft ? " + controller.getGameManager().getKompositum().cardList)
  }

  override def undoStep: Unit = {

      controller.getGameManager.setKompositum(KompositumCard(undoKompositum))
  }

  override def redoStep: Unit = doStep
}
