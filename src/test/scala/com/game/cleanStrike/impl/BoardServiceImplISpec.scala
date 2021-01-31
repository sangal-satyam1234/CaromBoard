package com.game.cleanStrike.impl

import com.game.cleanStrike.api.CleanStrikeService
import com.game.cleanStrike.impl.board.actions.NoStrike
import org.scalatest.wordspec.AnyWordSpecLike

class BoardServiceImplISpec extends AnyWordSpecLike {

  val service = new BoardServiceImpl

  "The service" should {
    "handle 3 games concurrently" when {
      val games = createGames(service, 3)
      "Game 1" should {
        "get current turn" in {
          assert(service.currentTurn(games(0)).contains(1))
        }
      }
      "Game 2" should {
        "perform current turn" in {
          val success = service.performTurn(games(1), 1, NoStrike)
          assert(success.equals(true))
          assert(service.currentTurn(games(1)).contains(2))
        }
        "handle incorrect turn" in {
          val failure = service.performTurn(games(1), 1, NoStrike)
          assert(failure.equals(false))
        }
      }
      "Game 3" should {
        "have status" in {
          val status = service.status(games(2))
          val expected = "No winner. Score: None"
          assert(expected.equals(status))
        }
      }
      "Invalid game" should {
        "have no turn" in {
          assert(service.currentTurn("random_id").isEmpty)
        }
        "have no status" in {
          assert(service.status("random_id").isEmpty)
        }
      }
    }
  }

  private def createGames(service: CleanStrikeService, n: Int): Seq[String] = {
    for (_ <- 1 to n) yield service.newGame(2)
  }
}
