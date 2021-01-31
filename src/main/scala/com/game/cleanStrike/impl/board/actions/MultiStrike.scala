package com.game.cleanStrike.impl.board.actions

import com.game.cleanStrike.api.board.{Action, Coin}
import com.game.cleanStrike.impl.board.actions.MultiStrike.POINT

object MultiStrike {
  private val POINT=2
}

case class MultiStrike(private val coins: Seq[Action]) extends Action {

  override def update(currPoints: Int): Int = {
    coins.foldLeft(currPoints) {
      (lastPoint, action) => action.update(lastPoint)
    } + POINT
  }

  override def coinsPocketed: (Coin, Int) = {
    (Coin.Black, 2)
  }
}
