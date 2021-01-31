package com.game.cleanStrike.impl

import com.game.cleanStrike.impl.SpecUtility.winningMoves
import org.scalatest.wordspec.AnyWordSpecLike

class CaromStrikeBoardISpec extends AnyWordSpecLike {


  "A strike board" when {
    "in initial state" should {
      "have correct player turn" in {
        val board = new BasicBoardBuilder(2).build
        assert(board.currentTurn == 1)
      }
      "should have no winner" in {
        val board = new BasicBoardBuilder(2).build
        assert(!board.isWon)
      }
      "players have 0 points" in {
        val board = new BasicBoardBuilder(2).build
        assert(board.points(1) == 0)
        assert(board.points(2) == 0)
      }
    }
    "with some sequence moves" should {
      "have correct winner" in {
        val board = new BasicBoardBuilder(2).build
        val moves = winningMoves
        moves.foreach(board.update)
        assert(board.isWon)
        assert(board.winner == Some(2))
      }
      "should have correct result" in {
        val board = new BasicBoardBuilder(2).build
        val moves = winningMoves
        moves.foreach(board.update)
        assert(board.points(1) == 1)
        assert(board.points(2) == 6)
      }
    }
  }
}
