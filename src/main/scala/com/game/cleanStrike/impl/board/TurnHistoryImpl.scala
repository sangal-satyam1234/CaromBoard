package com.game.cleanStrike.impl.board

import com.game.cleanStrike.api.board.{Action, TurnHistory}
import com.game.cleanStrike.impl.board.HistoryUtility.foulsOnlyFilter
import com.game.cleanStrike.impl.board.actions.{DefunctStrike, NoStrike, StrikerStrike}
import org.slf4j.LoggerFactory

import scala.collection.mutable


class TurnHistoryImpl[T] extends TurnHistory[T] {

  private val history = mutable.Map[T, Seq[Action]]()
  private val log = LoggerFactory.getLogger(classOf[TurnHistoryImpl[T]])

  override def recordAction(player: T, action: Action): Unit = {
    val currList = history.getOrElse(player, Seq())
    val newList = currList.appended(action)
    log.info(s"Action[$action] recorded for player [$player]")
    history += (player -> newList)
  }

  override def getAllActions(player: T): Seq[Action] = history.getOrElse(player, Seq())

  override def lastThreeActions(player: T): Seq[Action] = {
    val currList = history.getOrElse(player, Seq())
    currList.takeRight(3)
  }

  override def clear(): Unit = {
    history.clear()
    log.info("All players turn history cleared")
  }

  override def getAllFoulActions(player: T): Seq[(Action, Int)] = history.get(player) match {
    case None => Seq()
    case Some(list) => list.zipWithIndex.filter({ case (action, _) => foulsOnlyFilter(action) })
  }
}

object HistoryUtility {
  def foulsOnlyFilter(action: Action): Boolean = action match {
    case DefunctStrike(_) | StrikerStrike => true
    case _ => false
  }

  def noPocketFilter(action: Action): Boolean = action match {
    case DefunctStrike(_) | StrikerStrike | NoStrike => true
    case _ => false
  }
}