package com.game.cleanStrike.impl

import com.game.cleanStrike.api.AbstractBasicBoardBuilder

/**
 * Builds a board for players just denoted by an Integer
 *
 * @param nPlayers : The number of players
 */
class BasicBoardBuilder(private val nPlayers: Int) extends AbstractBasicBoardBuilder[Int] {
  override def getPlayersList: Seq[Int] = {
    val list = for (p <- 1 to nPlayers) yield p
    list
  }
}
