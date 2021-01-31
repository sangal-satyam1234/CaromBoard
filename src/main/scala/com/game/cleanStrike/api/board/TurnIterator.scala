package com.game.cleanStrike.api.board

trait TurnIterator[T] {
  /**
   * @return : Update to next position
   */
  def next(): Unit

  /**
   * Get current position
   *
   * @return
   */
  def current: T
}

