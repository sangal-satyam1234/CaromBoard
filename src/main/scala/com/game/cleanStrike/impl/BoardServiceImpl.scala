package com.game.cleanStrike.impl

import com.game.cleanStrike.api.CleanStrikeService
import com.game.cleanStrike.api.board.Action
import org.slf4j.LoggerFactory

import java.util.UUID
import scala.collection.mutable

class BoardServiceImpl extends CleanStrikeService {

  private val log = LoggerFactory.getLogger(classOf[BoardServiceImpl])
  private val allGames = mutable.Map[String, BoardRef]()

  override def newGame(nPlayers: Int): String = {
    val id = UUID.randomUUID().toString
    val board = new BoardRef(id, nPlayers)
    allGames += (id -> board)
    board.startGame()
    id
  }

  override def currentTurn(ID: String): Option[Int] = call(ID) {
    ref => ref.currentTurn
  }

  override def performTurn(ID: String, turn: Int, action: Action): Boolean = call(ID) {
    ref => ref.performTurn(turn, action)
  }.getOrElse(false)

  override def canContinue(ID: String): Boolean = call(ID) {
    ref => ref.canContinue
  }.getOrElse(false)

  override def currentPoints(ID: String, position: Int): Option[Int] = call(ID) {
    ref => ref.currentPoints(position)
  }

  override def status(ID: String): String = call(ID) {
    ref => ref.getStatus
  }.getOrElse("")

  private def call[T](ID: String)(f: BoardRef => T): Option[T] = allGames.get(ID) match {
    case Some(ref) => Some(f(ref))
    case None =>
      log.error(s"[$ID] Unknown Game")
      None
  }
}
