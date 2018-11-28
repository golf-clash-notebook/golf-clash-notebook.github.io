// /*
//  * MIT License
//  *
//  * Copyright (c) 2018 golf-clash-notebook
//  *
//  * Permission is hereby granted, free of charge, to any person obtaining a copy
//  * of this software and associated documentation files (the "Software"), to deal
//  * in the Software without restriction, including without limitation the rights
//  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//  * copies of the Software, and to permit persons to whom the Software is
//  * furnished to do so, subject to the following conditions:
//  *
//  * The above copyright notice and this permission notice shall be included in all
//  * copies or substantial portions of the Software.
//  *
//  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//  * SOFTWARE.
//  */

// // Code added to `clubranker.scala` @ line 275

// // rankedClubs
// //   .sortWith { (clubA, clubB) =>
// //     if (clubA.clubLevel.tour != clubB.clubLevel.tour)
// //       clubA.clubLevel.tour < clubB.clubLevel.tour
// //     else clubA.clubLevel.level < clubB.clubLevel.level
// //   }
// //   .foreach { rankedClub =>
// //     println(
// //       f"${rankedClub.score * 100}%.2f, ${rankedClub.clubLevel.category}, ${rankedClub.clubLevel.name} ${rankedClub.clubLevel.level}"
// //     )
// //   }

// object clubscorecomparison {

//   val originalScoresPath =
//     "/home/cranston/Videos/golf-clash-notebook.github.io/club-update-comparison/OriginalClubScores.txt"
//   val newScoresPath =
//     "/home/cranston/Videos/golf-clash-notebook.github.io/club-update-comparison/NewClubScores.txt"

//   case class ClubScore(name: String, category: String, score: Double)

//   def main(args: Array[String]): Unit = {

//     val outputWidth = 35

//     val originalScores = loadScores(originalScoresPath)
//     val newScores      = loadScores(newScoresPath)

//     val scoreChanges =
//       originalScores.zip(newScores).map {
//         case (oldClubScore, newClubScore) =>
//           val scoreDiff = newClubScore.score - oldClubScore.score
//           (scoreDiff, oldClubScore, newClubScore)
//       }

//     println()

//     printDiffBlock("Clubs", scoreChanges, outputWidth)

//     scoreChanges.groupBy(_._3.category).foreach {
//       case (category, categoryScoreChanges) =>
//         printDiffBlock(category, categoryScoreChanges, outputWidth)
//     }

//   }

//   def loadScores(filePath: String): List[ClubScore] =
//     scala.io.Source
//       .fromFile(filePath)
//       .getLines
//       .map { line =>
//         val Array(scoreString, clubCategory, clubName) = line.split(",")
//         ClubScore(clubName.replaceAll("The", "").trim, clubCategory.trim, scoreString.toDouble)
//       }
//       .toList

//   private[this] def printDiffBlock(
//     category: String,
//     clubDiffs: List[(Double, ClubScore, ClubScore)],
//     outputWidth: Int
//   ) = {
//     println(sectionTitle(s"Most Beefed $category", outputWidth))
//     clubDiffs
//       .sortBy(-_._1)
//       .take(10)
//       .foreach(s => println(scoreDiffOutput(s, outputWidth)))

//     println(sectionTitle(s"Most Nerfed $category", outputWidth))
//     clubDiffs
//       .sortBy(_._1)
//       .take(10)
//       .foreach(s => println(scoreDiffOutput(s, outputWidth)))

//     println()
//   }

//   private[this] def sectionTitle(title: String, outputWidth: Int): String = {
//     List(
//       consoleColorize(Console.BLUE_B, center("", outputWidth)),
//       consoleColorize(Console.BLUE_B, center(title, outputWidth)),
//       consoleColorize(Console.BLUE_B, center("", outputWidth))
//     ).mkString("\n")
//   }

//   private[this] def scoreDiffOutput(
//     diffInfo: (Double, ClubScore, ClubScore),
//     outputWidth: Int
//   ): String = {

//     val (scoreDiff, _, newClubScore) = diffInfo
//     val consoleColor =
//       if (scoreDiff > 0) Console.GREEN_B
//       else if (scoreDiff < 0) Console.RED_B
//       else Console.YELLOW_B

//     val scoreDiffString = padLeft(f"${scoreDiff}%.2f", 8)

//     consoleColorize(
//       consoleColor,
//       s"$scoreDiffString - ${newClubScore.name}".padTo(outputWidth, ' ')
//     )
//   }

//   private[this] def padLeft(s: String, len: Int): String =
//     s.reverse.padTo(len, ' ').reverse

//   private[this] def center(s: String, width: Int): String = {
//     val padding = (width - s.size) / 2
//     (" " * padding) ++ s ++ (" " * (width - (s.size + padding)))
//   }

//   private[this] def consoleColorize(color: String, s: String): String = {
//     "   " + Console.BOLD + Console.WHITE + color + s + Console.RESET
//   }

// }
