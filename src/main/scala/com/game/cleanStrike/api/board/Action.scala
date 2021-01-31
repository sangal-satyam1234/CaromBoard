package com.game.cleanStrike.api.board

trait Action {
  /**
   * Calculates new score
   *
   * @param currPoints :  The current score
   * @return : New score
   */
  def update(currPoints: Int): Int

  /**
   * Calculates pocketed coins
   *
   * @return : The number of coins to be removed from board
   */
  def coinsPocketed: (Coin, Int)
}

trait Coin
object Coin {
  case object Striker extends Coin
  case object Red extends Coin
  case object Black extends Coin
  case object None extends Coin
}