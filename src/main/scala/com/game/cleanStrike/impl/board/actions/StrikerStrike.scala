package com.game.cleanStrike.impl.board.actions

import com.game.cleanStrike.api.board.{Action, Coin}

case object StrikerStrike extends Action {
  private val POINT = 1

  override def update(currPoints: Int): Int = currPoints - POINT

  override def coinsPocketed: (Coin, Int) = (Coin.None, 0)
}
