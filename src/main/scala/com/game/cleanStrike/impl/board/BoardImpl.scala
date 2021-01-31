package com.game.cleanStrike.impl.board

import com.game.cleanStrike.api.board._
import org.slf4j.LoggerFactory


class BoardImpl[T](private val players: TurnIterator[T],
                   private val playersHistory: TurnHistory[T],
                   private val pointTable: PointsTable[T],
                   private val coinsTable: CoinsTable) extends CleanStrikeBoard[T] {

  private val log = LoggerFactory.getLogger(classOf[BoardImpl[T]])

  override def points(player: T): Int = pointTable.points(player)

  override def history(player: T): Seq[Action] = playersHistory.getAllActions(player)

  override def currentTurn: T = players.current

  override def update(action: Action): Unit = {
    if (isValidAction(action)) {
      playersHistory.recordAction(currentTurn, action)
      pointTable.update(currentTurn, action)
      coinsTable.update(action)
      players.next()
    } else {
      log.error(s"Invalid [$action] for $currentTurn")
    }
  }

  override def isWon: Boolean = pointTable.isWinning

  override def winner: Option[T] = pointTable.getWinner

  override def currentResult: String = {
    val summary = pointTable.getSummary
    val winner = pointTable.getWinner
    winner match {
      case Some(player) =>
        log.info(s"Player-$player won the game")
        s"Player-$player won the game. Final score: $summary"
      case None =>
        log.info(s"There was no winner")
        s"No winner. Score: ${summary}"
    }
  }

  override def isValidAction(action: Action): Boolean = coinsTable.isValidAction(action)

  override def canContinue: Boolean = coinsTable.availableCoins(Coin.Black) > 0 || coinsTable.availableCoins(Coin.Red) > 0
}