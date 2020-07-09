package model.fileIoComponent.fileIoJsonImpl

import com.google.inject.Guice
import control.BaseImpl.AnswerState
import control.ControllerInterface
import model.BaseImpl.{AnswerCard, Card, KompositumCard}
import model.ModelInterface
import model.fileIoComponent.FileIOInterface
import play.api.libs.json.{JsArray, JsNumber, JsObject, JsValue, Json, Writes}

import scala.io.Source

class FileIO extends FileIOInterface {
  override def load: ModelInterface = {
  null
  }

  override def save(game: ModelInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("cards.json"))
    pw.write(Json.prettyPrint(modelToJson(game)))
    pw.close()
  }

  def modelToJson(game: ModelInterface): JsObject = {
    Json.obj(
      "cards" -> cardsToJson(game.getKomp().cardList)
    )
  }

  def cardsToJson(card: List[Card]): JsArray = {
    var jSonArray = Json.arr()
    for(x <- card){
      jSonArray = jSonArray :+ Json.obj("card" -> Json.toJson(cardToJson(x)))
    }
    jSonArray
  }
  def  cardToJson(card: Card): JsObject = Json.obj("text" -> card.toString)
}
