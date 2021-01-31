package com.game.cleanStrike.impl.board

import com.game.cleanStrike.api.board.TurnIterator


class TurnIteratorImpl[T](private val players: Seq[T]) extends TurnIterator[T] {
  private var cursor: Int = 0

  /**
   * Grants turn to next player
   */
  override def next(): Unit = {
    increaseOrRollOver()
  }

  /**
   * @return : Current player having turn
   */
  override def current: T = players(cursor)

  private def increaseOrRollOver(): Unit = {
    cursor = cursor + 1
    if (cursor == players.length)
      cursor = 0
  }
}