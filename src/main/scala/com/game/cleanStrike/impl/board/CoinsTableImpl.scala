package com.game.cleanStrike.impl.board

import com.game.cleanStrike.api.board.{Action, Coin, CoinsTable}
import org.slf4j.LoggerFactory

import scala.collection.mutable

class CoinsTableImpl extends CoinsTable {

  private val table = mutable.Map[Coin, Int]()
  private val log = LoggerFactory.getLogger(classOf[CoinsTableImpl])

  private val initialize = {
    table += (Coin.Black -> 9)
    table += (Coin.Red -> 1)
    table += (Coin.Striker -> 1)
  }

  override def update(action: Action): Unit = {
    if (!isValidAction(action)) {
      log.error(s"[$action] Insufficient coins on board")
    } else {
      val (coinType, coinsToRemove) = action.coinsPocketed
      val currCount = availableCoins(coinType)
      val newCount = currCount - coinsToRemove
      table += (coinType -> newCount)
    }
  }

  override def isValidAction(action: Action): Boolean = {
    val (coinType, coinsToRemove) = action.coinsPocketed
    val availableCoins = this.availableCoins(coinType)
    availableCoins >= coinsToRemove
  }

  override def availableCoins(coinType: Coin): Int = table.getOrElse(coinType, 0)
}
