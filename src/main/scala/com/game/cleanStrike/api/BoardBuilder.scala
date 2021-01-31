package com.game.cleanStrike.api

import com.game.cleanStrike.api.board._
import com.game.cleanStrike.impl.board._

/**
 * Build a carom board for players of type T
 *
 * @tparam T : Player type
 */
trait BoardBuilder[T] {
  def build: CleanStrikeBoard[T]
}

abstract class AbstractBasicBoardBuilder[T] extends BoardBuilder[T] {

  private var turnHistory: TurnHistory[T] = _

  private def coinManager: CoinsTable = new CoinsTableImpl

  private def historyManager: TurnHistory[T] = {
    turnHistory = new TurnHistoryImpl[T]
    turnHistory
  }

  private def turnManager: TurnIterator[T] = new TurnIteratorImpl[T](getPlayersList)

  private def pointManager: PointsTable[T] = new PointsTableImpl[T](turnHistory)

  override def build: CleanStrikeBoard[T] = new BoardImpl[T](turnManager, historyManager, pointManager, coinManager)

  /**
   * Generates unique player instances
   *
   * @return
   */
  def getPlayersList: Seq[T]

}