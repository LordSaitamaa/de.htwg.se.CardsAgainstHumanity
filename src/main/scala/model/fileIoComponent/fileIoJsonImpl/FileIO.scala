package model.fileIoComponent.fileIoJsonImpl

import com.google.inject.Guice
import control.BaseImpl.AnswerState
import control.ControllerInterface
import model.BaseImpl.{AnswerCard, Card, KompositumCard, QuestionCard}
import model.ModelInterface
import model.fileIoComponent.FileIOInterface
import play.api.libs.json._

import scala.io.Source

class FileIO extends FileIOInterface {

  def load(gameMan: ModelInterface): ModelInterface  = {
    val source: String = Source.fromFile("CardStack.json").getLines().mkString
    val json: JsValue = Json.parse(source)
    val cards = (json \ "cardList").get.productIterator
    var tempList = List[Card]()
    for(x <- cards){
      if(x.toString.contains("_"))
        tempList = tempList :+ QuestionCard(x.toString)
      else
        tempList = tempList :+ AnswerCard(x.toString)
    }
    var kompCards = KompositumCard(tempList)
    gameMan.setKompositum(KompositumCard(tempList))
  }

  override def save(game: ModelInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("cards.json"))
    pw.write(Json.prettyPrint(cardsStackToJson(game)))
    pw.close()
  }

  def cardsStackToJson(game: ModelInterface): JsObject = {
    Json.obj(
      "cardList" -> Json.toJson(for{x <- game.getKompositum().cardList} yield {
        Json.obj("card" -> JsString(x.toString))})
    )
  }

}
