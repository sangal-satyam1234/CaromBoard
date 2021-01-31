package com.game.cleanStrike.impl.board

import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.wordspec.AnyWordSpecLike

class TurnIteratorSpec extends AnyWordSpecLike{

  val players = List(1,2,3)

  "Player turn " when{
    val turnIterator=new TurnIteratorImpl(players)
    "should be for 1st player" in{
      turnIterator.current should be(1)
    }
    "should be for 2nd player" in{
      turnIterator.next()
      turnIterator.current should be(2)
    }
    "should roll-over to 1st" in{
      turnIterator.next()
      turnIterator.next()
      turnIterator.current should be(1)
    }
  }
}
