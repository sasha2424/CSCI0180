package hw06.sol

import hw06.src.ITextProcessor
import hw06.src.FileProcessor
import java.io._

import java.util.HashMap

import scala.collection.mutable.PriorityQueue

class TextProcessor(val fileName: String) extends ITextProcessor {

  //get all the lines from the file
  val lines = FileProcessor.getText(fileName)

  //Create a hashmap to temporarily store word counts
  val counts = new HashMap[String, Integer]
  var totalWords = 0.0

  //create a PriorityQueue that will later be used for sorting
  val ordering = Ordering[Integer].on[(String, Integer)] { x => x._2 }
  val pq = PriorityQueue.empty(ordering)

  //read in the lines from the file and update counts and totalWords
  lines.foreach {
    x => getWordsFromLine(x).foreach { word => processWord(word) }
  }

  //take all of the entries of count and put them into the PriorityQueue
  val itt = counts.entrySet().iterator()
  while (itt.hasNext()) {
    val e = itt.next()
    pq += ((e.getKey, e.getValue))
  }

  /**
   * Given a word puts it in the hashmap and increments totalWords
   * If the word is an empty string then does nothing
   * @param word - the word to be added to counts
   */
  private def processWord(word: String) {
    if (word != "") {
      addToCounts(word)
      totalWords += 1
    }
  }

  /**
   * Takes a line from the file being read and parses out
   * all of the individual words using a regex
   * @param line - The line to be parsed
   * @return an Array of the words in the line
   */
  private def getWordsFromLine(line: String): Array[String] = {
    line.toLowerCase().split("[\\s\\W]+")
  }

  /**
   * Adds a given word to the counts hashmap.
   * If the word isn't in the hashmap it is added.
   * If the word is in the hashmap its value is increased by 1
   * @param word - the word to be added to Counts
   */
  private def addToCounts(word: String) {
    if (counts.containsKey(word)) {
      counts.put(word, counts.get(word) + 1)
    } else {
      counts.put(word, 1)
    }
  }
  /**
   * Prints the top 10 most common words in the specified text file,
   * along with the number of times they each appear, and their
   * proportion relative to the other words in the document
   */
  def textStats() {
    println("#|\tWord\t|\tCount\t|\t%")
    println("$"*60)
    for (i <- 0 until 10) {
      if (pq.isEmpty) {
        return ;
      }
      val t = pq.dequeue()
      println((i+1) + "|\t" + t._1 + "\t|\t" + t._2
        + "\t|\t" + (100 * (t._2) / totalWords))
    }
  }
}

object TextProcessor {
  def main(args: Array[String]): Unit = {
    //verifies that a file is given
    //verifies that only one file is given
    if (args.length != 1) {
      System.out.println("Invalid number of arguments")
      System.exit(0)
    }

    try {
      //prints out the file stats and catches exceptions
      (new TextProcessor(args(0))).textStats()
    } catch {
      case e: FileNotFoundException =>
        println("The file was not found or could not be opened")
    }

  }
}