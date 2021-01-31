package com.game.cleanStrike.api.board

trait CoinsTable {
  /**
   * Returns whether the action can be performed or not
   *
   * @param action
   * @return True/False
   */
  def isValidAction(action: Action): Boolean

  /**
   * Updates available coins if the action can be performed
   *
   * @param action
   */
  def update(action: Action): Unit

  /**
   * Returns count.0 if no coins
   *
   * @param coinType
   * @return
   */
  def availableCoins(coinType: Coin): Int
}
