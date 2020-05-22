package control
import model.{SetupGame, Player}
import utils.Observable

class Controller(var gameCards:SetupGame) extends Observable{
  val players = Vector(
     Player("Horst",false),
     Player("Hans",false)
  )
  val activePlayer = 0

  def getCurrentPlayer: Player = players(activePlayer)

  def initCardDeck(): Unit ={

  }

}
