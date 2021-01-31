package com.game.cleanStrike.impl


import com.game.cleanStrike.api.board.{Action, Coin, TurnHistory}
import com.game.cleanStrike.impl.board.actions._
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.{mock, when}

object SpecUtility {
  //This should account for 6 points
  def multiStrikes: Seq[Action] = List(BlackStrike, BlackStrike, BlackStrike, BlackStrike)

  // This should penalize 1 point by rule 2
  def foulsByPlayerOne: Seq[(Action, Int)] = List((StrikerStrike, 1), (new DefunctStrike(Coin.Black), 3), (StrikerStrike, 4), (StrikerStrike, 6))

  // This should penalize 2 points by rule 2
  def foulsByPlayerTwo: Seq[(Action, Int)] = List((StrikerStrike, 1), (new DefunctStrike(Coin.Black), 3), (StrikerStrike, 4), (StrikerStrike, 6), (StrikerStrike, 7), (StrikerStrike, 9))

  def winningMoves: Seq[Action] = {
    List(new DefunctStrike(Coin.Black),
      new MultiStrike(multiStrikes),
      new RedStrike(multiStrikes),
      BlackStrike,
      StrikerStrike,
      StrikerStrike,
      StrikerStrike,
      StrikerStrike,
      StrikerStrike,
      BlackStrike
    )
    // player1 -> defunctstrike,redstrike,strikerstrike,strikerstrike,strikerstrike
    // player2 -> multistrike,blackstrike,strikerstrike,strikerstrike,blackstrike
  }

  def mockHistory: TurnHistory[Int] = {
    val history = mock(classOf[TurnHistory[Int]])
    when(history.getAllFoulActions(anyInt())).thenReturn(Seq())
    //when(history.getAllFoulActions(2)).thenReturn(Seq())
    history
  }
}
