package com.game.cleanStrike.impl.board.actions

import com.game.cleanStrike.api.board.{Action, Coin}
import com.game.cleanStrike.impl.board.actions.RedStrike.POINT

object RedStrike {
  private val POINT = 3
}

case class RedStrike(private val coins: Seq[Action]) extends Action {
  override def update(currPoints: Int): Int = {
    coins.foldLeft(currPoints)((lastPoint, action) => action.update(lastPoint)) + POINT
  }

  override def coinsPocketed: (Coin, Int) = {
    (Coin.Red, 1)
  }
}
