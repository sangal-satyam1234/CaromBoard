package com.game.cleanStrike.api

import com.game.cleanStrike.api.board.Action

/**
 * Manages clean strike game
 */
trait CleanStrikeResource {
  /**
   * Starts a new carom board game
   *
   */
  def startGame(): Unit

  /**
   * Utility method to get position of current player having turn
   *
   * @return Pointer index for current player
   */
  def currentTurn: Int

  /**
   * Returns current points of player
   *
   * @param player : Board player
   * @return : Default 0
   */
  def currentPoints(player: Int): Int

  /**
   * Updates state for current player's action
   *
   * @param turn   : Turn number
   * @param action : Player action
   */
  def performTurn(turn: Int, action: Action): Boolean

  /**
   * Returns true if board is ready to play and has sufficient coins. Else false
   *
   * @return
   */

  def canContinue: Boolean

  def getStatus: String

  /**
   * Resets the game to initial state
   */
  def resetGame(): Unit

  /**
   * Stops current game.
   */
  def stopGame(): Unit
}
