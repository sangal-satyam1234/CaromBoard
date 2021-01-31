package com.game.cleanStrike.impl.board

import com.game.cleanStrike.api.board.{Action, PointsTable, TurnHistory}
import com.game.cleanStrike.impl.board.HistoryUtility.noPocketFilter
import org.slf4j.LoggerFactory

import scala.collection.mutable

class PointsTableImpl[T](val turnHistory: TurnHistory[T]) extends PointsTable[T] {

  private val table = mutable.Map[T, Int]()
  private var winner: T = _
  private val log = LoggerFactory.getLogger(classOf[PointsTableImpl[T]])

  override def points(player: T): Int = table.getOrElse(player, 0)

  override def update(player: T, action: Action): Unit = {
    val currPoints = table.getOrElse(player, 0)
    val newPoints = action.update(currPoints)
    val penaltyPoints = calculatePenalty(player)
    val finalPoints = newPoints - penaltyPoints
    log.info(s"Player [$player] Old_points:[$currPoints] Extra_penalty:[$penaltyPoints] Final:[$finalPoints]")
    table += (player -> finalPoints)
  }

  override def isWinning: Boolean = {
    val (leader, secondLeader) = topTwoScorers
    if ((leader._2 >= 5) && (leader._2 - secondLeader._2 >= 3)) {
      winner = leader._1
      true
    } else false
  }

  private def topTwoScorers = {
    var leader: (T, Int) = (null.asInstanceOf[T], 0) // (player -> points)
    var secondLeader: (T, Int) = (null.asInstanceOf[T], 0)
    table.keys.foreach {
      player =>
        val points = table(player)
        if (points > leader._2) {
          secondLeader = leader
          leader = (player, points)
        } else if (points > secondLeader._2) {
          secondLeader = (player, points)
        } else {}
    }
    (leader, secondLeader)
  }

  override def getWinner: Option[T] = {
    if (isWinning) Some(winner)
    else None
  }

  override def getSummary: String = {
    val DELIMITER = "\n"
    if (table.isEmpty) "None"
    else table.mkString(DELIMITER)
  }

  private def calculatePenalty(implicit player: T): Int = {
    penaltyForNoPocket + penaltyForMultipleFouls
  }

  private def penaltyForNoPocket(implicit player: T) = {
    val lastMoves = turnHistory.lastThreeActions(player)
    if (lastMoves.length < 3) 0
    else {
      val consecutiveMisses = lastMoves.forall(noPocketFilter)
      if (consecutiveMisses) 1 else 0
    }
  }

  private val cache = mutable.Map[T, Set[(Action, Int)]]()

  private def penaltyForMultipleFouls(implicit player: T): Int = {
    val fouledMoves = turnHistory.getAllFoulActions(player).toSet
    val cachedMoves = cache.getOrElse(player, Set[(Action, Int)]())
    val uncountedFouls = fouledMoves.diff(cachedMoves)
    cache += (player -> cachedMoves.union(uncountedFouls))
    uncountedFouls.size / 3
  }
}
