package com.game.cleanStrike

import com.game.cleanStrike.api.board.{Action, Coin}
import com.game.cleanStrike.impl.BoardServiceImpl
import com.game.cleanStrike.impl.board.actions._
import org.kohsuke.args4j.{CmdLineParser, Option}

import java.io.File
import java.net.URL
import scala.io.BufferedSource
import scala.jdk.javaapi.CollectionConverters.asJava
import scala.util.Try

object Parser {

  implicit def parseToCoin(coin: String): Coin = {
    coin match {
      case "B" => Coin.Black
      case "R" => Coin.Red
      case "SS" => Coin.Striker
      case _ => Coin.None
    }
  }

  implicit def parseToAction(str: Seq[String]): Action = {
    str.head match {
      case "B" => BlackStrike
      case "SS" => StrikerStrike
      case "R" =>
        new RedStrike(parseToActions(str.tail))
      case "M" =>
        new MultiStrike(parseToActions(str.tail))
      case "D" =>
        val coinType: Coin = parseToCoin(str(1))
        new DefunctStrike(coinType)
      case _ => NoStrike
    }
  }

  implicit def parseToActions(str: Seq[String]): Seq[Action] = {
    val n = str(0).toInt
    if (n == 0) Seq()
    else {
      val action: Action = parseToAction(str.tail)
      (1 to n).map(_ => action)
    }
  }
}

object Driver {

  import Parser._

  @Option(name = "-f", aliases = Array("--file"), usage = "Fully qualified path and name of input file", required = false)
  private var customInput: String = ""

  def main(args: Array[String]): Unit = {
    val cmdLineParser = new CmdLineParser(this)
    Try(cmdLineParser.parseArgument(asJava(args))).getOrElse {
      cmdLineParser.printUsage(System.out)
      System.exit(-1)
    }
    if (customInput.isEmpty) {
      val inputFile: URL = getClass.getClassLoader.getResource("CleanStrike/sampleInput")
      processInput(inputFile)
    }
    else {
      val inputFile: URL = new File(customInput).toURI.toURL
      processInput(inputFile)
    }
  }

  def processInput(url: URL): Unit = {
    println(s"Input Source : $url")
    val gameService = new BoardServiceImpl
    val gameID: String = gameService.newGame(2)
    val input: BufferedSource = scala.io.Source.fromURL(url)
    input.getLines().foreach {
      line =>
        val strArr = line.split(" ")
        val player = strArr(0).toInt
        val turnType: Action = strArr.tail.toSeq
        if (gameService.canContinue(gameID)) gameService.performTurn(gameID, player, turnType)
    }
    println(gameService.status(gameID))
  }
}
