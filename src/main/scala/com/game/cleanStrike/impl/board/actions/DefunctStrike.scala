package com.game.cleanStrike.impl.board.actions

import com.game.cleanStrike.api.board.{Action, Coin}
import com.game.cleanStrike.impl.board.actions.DefunctStrike.POINT

object DefunctStrike {
  private val POINT=2
}

case class DefunctStrike(private val coin: Coin) extends Action {
  override def update(currPoints: Int): Int = currPoints - POINT

  override def coinsPocketed: (Coin, Int) = (coin, 1)
}
