package com.game.cleanStrike.impl.board

import com.game.cleanStrike.impl.SpecUtility.multiStrikes
import com.game.cleanStrike.impl.board.actions.{BlackStrike, MultiStrike, NoStrike, StrikerStrike}
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class TurnHistoryImplSpec extends AnyFlatSpecLike {

  val turnHistory = new TurnHistoryImpl[Int]
  val multiStrikeAction=new MultiStrike(multiStrikes)
  val turnsMade = List(NoStrike, BlackStrike, StrikerStrike, multiStrikeAction)
  turnsMade.foreach(turnHistory.recordAction(1, _))

  it should "record history in correct turn order" in {
    val history=turnHistory.getAllActions(1)
    assert(turnsMade.equals(history))
  }

  it should "return only fouls" in {
    val fouls=turnHistory.getAllFoulActions(1)
    fouls should be (List((StrikerStrike,2)))
  }

  it should "return last 3 actions" in{
    val fouls=turnHistory.lastThreeActions(1)
    fouls should be (List(BlackStrike,StrikerStrike,multiStrikeAction))
  }
}
