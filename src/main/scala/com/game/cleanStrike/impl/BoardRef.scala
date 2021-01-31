package com.game.cleanStrike.impl

import com.game.cleanStrike.api.CleanStrikeResource
import com.game.cleanStrike.api.board.{Action, CleanStrikeBoard}
import org.slf4j.LoggerFactory

class BoardRef(private val id: String, private val nPlayers: Int) extends CleanStrikeResource {

  private val log = LoggerFactory.getLogger(classOf[BoardRef])
  private var board: Option[CleanStrikeBoard[Int]] = None

  override def startGame(): Unit = {
    board = Some(new BasicBoardBuilder(nPlayers).build)
    log.info(s"[$id] Board Ready")
  }

  override def currentTurn: Int = call(ref => ref.currentTurn).getOrElse(-1)

  override def performTurn(turn: Int, action: Action): Boolean = call { ref =>
    if (ref.isWon) {
      log.warn(s"[$id] Board already has a winner. Should not perform any more turns.")
      false
    } else if (ref.currentTurn != turn) {
      log.error(s"[$id] Incorrect turn:$turn Expected:${ref.currentTurn}")
      false
    } else if (!ref.isValidAction(action)) {
      log.error(s"[$id] Action[$action] cannot be performed")
      false
    } else {
      ref.update(action)
      true
    }
  }.getOrElse(false)

  override def resetGame(): Unit = {
    board = Some(new BasicBoardBuilder(nPlayers).build)
    log.info(s"[$id] Board is reset")
  }

  override def stopGame(): Unit = {
    log.info(s"[$id] Board stopped")
    board = None
  }

  override def canContinue: Boolean = call(ref => !ref.isWon && ref.canContinue).get

  override def currentPoints(player: Int): Int = call(ref => ref.points(player)).get

  override def getStatus: String = call(ref => ref.currentResult).getOrElse("No status")

  private def call[T](f: CleanStrikeBoard[Int] => T): Option[T] = board match {
    case Some(b) => Some(f(b))
    case None =>
      log.error(s"[$id] Board is not ready")
      None
  }
}
