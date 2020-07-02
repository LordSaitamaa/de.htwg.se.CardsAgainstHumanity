package model

object RoundStrategy {

  abstract class RoundStrategy(){
    def strategy(numberOfPlayer: Int)
    def strategy2Player(): GameManager
    def stategy3Player(): GameManager
    def strategy4Player(): GameManager
    def execute(numberOfPlayers: Int, gameManager: GameManager): GameManager
  }

  def execute(numberOfPlayers: Int): GameManager = strategy(numberOfPlayers)

  def strategy(numberOfPlayer: Int): GameManager= numberOfPlayer match{
    case 2 => strategy2Players()
    case 3 => strategy3Players()
    case 4 => strategy4Players()
  }
  def strategy2Players(): GameManager = {
    GameManager.Builder()
      .withNumberOfPlayer(2)
      .withNumberOfRounds(2)
      .build()
  }

  def strategy3Players(): GameManager = {
    GameManager.Builder().
      withNumberOfPlayer(3).
      withNumberOfRounds(6).
      build()
  }

  def strategy4Players(): GameManager = {
    GameManager.Builder()
      .withNumberOfPlayer(4)
      .withNumberOfRounds(4)
      .build()
  }
}
