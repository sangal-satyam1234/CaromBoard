package com.game.cleanStrike.impl.board.actions

import com.game.cleanStrike.api.board.{Action, Coin}

case object NoStrike extends Action{
  override def update(currPoints: Int): Int = currPoints

  override def coinsPocketed: (Coin, Int) = (Coin.None,0)
}
