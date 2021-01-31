package com.game.cleanStrike.impl.board

import com.game.cleanStrike.api.board.Coin
import com.game.cleanStrike.impl.SpecUtility.multiStrikes
import com.game.cleanStrike.impl.board.actions.{BlackStrike, DefunctStrike, MultiStrike, RedStrike}
import org.scalatest.wordspec.AnyWordSpecLike

class CoinsTableImplSpec extends AnyWordSpecLike {

  "Coins table" when {
    "on a valid action" should {
      "should return true" in {
        val coinTable = new CoinsTableImpl
        assert(coinTable.isValidAction(BlackStrike))
      }
    }
    "on an invalid action" should {
      "should return false" in {
        val coinTable = new CoinsTableImpl
        coinTable.update(new RedStrike(List(BlackStrike)))
        assert(!coinTable.isValidAction(new RedStrike(List(BlackStrike))))
      }
    }
    "on a sequence of actions" should {
      "have correct coin count" in {
        val coinTable = new CoinsTableImpl
        coinTable.update(BlackStrike)
        coinTable.update(BlackStrike)
        coinTable.update(new MultiStrike(multiStrikes))
        coinTable.update(new RedStrike(List(BlackStrike)))

        assert(coinTable.availableCoins(Coin.Black) == 5)
        assert(coinTable.availableCoins(Coin.Red) == 0)
      }
    }
  }
}
