package com.game.cleanStrike.impl.board

import com.game.cleanStrike.impl.SpecUtility.{foulsByPlayerOne, foulsByPlayerTwo, mockHistory, multiStrikes}
import com.game.cleanStrike.impl.board.actions.{BlackStrike, MultiStrike, NoStrike, StrikerStrike}
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.{doReturn, when}
import org.scalatest.wordspec.AnyWordSpecLike

class PointsTableImplSpec extends AnyWordSpecLike {
  "The points table" when {
    "with no turn history" should {
      "have no winner" in {
        val turnHistory = mockHistory
        val pointTable = new PointsTableImpl(turnHistory)
        assert(!pointTable.isWinning)
      }
      "have winning player " in {
        val turnHistory = mockHistory
        when(turnHistory.lastThreeActions(anyInt())).thenReturn(List())
        val pointTable = new PointsTableImpl(turnHistory)
        pointTable.update(1, new MultiStrike(multiStrikes))
        pointTable.update(2, BlackStrike)
        assert(pointTable.isWinning)
        assert(pointTable.getWinner.contains(1))
      }
    }
    "with some history " should {
      "deduct point for 3 consecutive no pocket" in {
        val turnHistory = mockHistory
        when(turnHistory.lastThreeActions(1)).thenReturn(List(NoStrike, StrikerStrike, NoStrike))
        doReturn(List(BlackStrike, NoStrike, NoStrike)).when(turnHistory).lastThreeActions(2)
        val pointTable = new PointsTableImpl(turnHistory)
        pointTable.update(1, new MultiStrike(multiStrikes))
        pointTable.update(2, new MultiStrike(multiStrikes ++ List(BlackStrike, BlackStrike)))
        assert(pointTable.getWinner.contains(2))
      }

      "deduct point for every 3 fouls" in {
        val turnHistory = mockHistory
        when(turnHistory.lastThreeActions(anyInt())).thenReturn(List())
        doReturn(foulsByPlayerTwo).when(turnHistory).getAllFoulActions(2)
        doReturn(foulsByPlayerOne).when(turnHistory).getAllFoulActions(1)
        val pointTable = new PointsTableImpl(turnHistory)
        pointTable.update(1, new MultiStrike(multiStrikes))
        pointTable.update(2, new MultiStrike(multiStrikes))
        assert(pointTable.points(1) == 5)
        assert(pointTable.points(2) == 4)
        assert(pointTable.getWinner.isEmpty)
      }
    }
  }
}
