package com.game.cleanStrike.api.board

trait CleanStrikeBoard[T] {

  def currentResult: String

  def points(player: T): Int

  def history(player: T): Seq[Action]

  def currentTurn: T

  def update(action: Action): Unit

  def isWon: Boolean

  def winner: Option[T]

  def canContinue: Boolean

  def isValidAction(action: Action): Boolean
}
