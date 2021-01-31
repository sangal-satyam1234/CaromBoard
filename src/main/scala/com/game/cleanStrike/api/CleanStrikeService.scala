package com.game.cleanStrike.api

import com.game.cleanStrike.api.board.Action

trait CleanStrikeService {

  /**
   * Start a new game
   *
   * @return : Unique ID for this game
   */
  def newGame(nPlayers: Int): String

  /**
   * For game with given ID ,get current player position
   *
   * @param ID : Board ID
   * @return : Current player position
   */
  def currentTurn(ID: String): Option[Int]

  /**
   * For a player ,take its sampleInput
   *
   * @param turn   : Current turn
   * @param action : Player action
   * @return : True,if turn was correct and action performed
   */

  def performTurn(ID: String, turn: Int, action: Action): Boolean

  /**
   * Returns whether game can be continued or not
   *
   * @param ID : Board id
   * @return : False if game not_exists/stopped/no_turns
   */
  def canContinue(ID: String): Boolean

  /**
   * Queries a board game of a player points
   *
   * @param ID     : Board ID
   * @param player : Player ID
   * @return : None if invalid board
   */
  def currentPoints(ID: String, player: Int): Option[Int]

  /**
   * Returns latest status of for a board game
   *
   * @param ID : Board ID
   * @return : Board status
   */
  def status(ID: String): String
}
