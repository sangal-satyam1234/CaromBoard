package com.game.cleanStrike.api.board

trait TurnHistory[T] {
  /**
   * Records action for a player
   *
   * @param player : Board player
   * @param action : Player's action
   */
  def recordAction(player: T, action: Action): Unit

  /**
   * Returns all actions performed by player in past
   *
   * @param player : Board Player
   * @return : A sequence of actions
   */
  def getAllActions(player: T): Seq[Action]

  /**
   * Returns all fouls for a player
   *
   * @param player : Board player
   * @return : Sequence of (action -> atTurn) pair for this player
   */
  def getAllFoulActions(player: T): Seq[(Action, Int)]

  /**
   * Returns latest 3 actions performed by player
   *
   * @param player : Board player
   * @return : A sequence
   */
  def lastThreeActions(player: T): Seq[Action]

  /**
   * Clears history
   */
  def clear(): Unit
}
