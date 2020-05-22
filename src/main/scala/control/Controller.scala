package control
import model.{InitCardDecks, Player}
import utils.Observable

class Controller(var gameCards:InitCardDecks) extends Observable{
  val players = Vector(
     Player("Horst",false),
     Player("Hans",false)
  )
  val activePlayer = 0

  def getCurrentPlayer: Player = players(activePlayer)

  def initCardDeck(): Unit ={

  }

}
