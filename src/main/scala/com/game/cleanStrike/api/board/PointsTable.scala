package com.game.cleanStrike.api.board

trait PointsTable[T] {
  /**
   * @param player
   * @return The current points for the player
   */
  def points(player: T): Int

  /**
   * Updates new point as per performed action
   *
   * @param player
   * @param action
   */
  def update(player: T, action: Action): Unit

  /**
   * Returns whether a player has sufficient points to win
   *
   * @return True/false
   */
  def isWinning: Boolean

  /**
   * Returns if a player is winning by points
   *
   * @return Some player if winning else None
   */
  def getWinner: Option[T]

  /**
   * Returns point table as string value
   *
   * @return (player,point) pair delimited by new line
   */
  def getSummary: String
}
