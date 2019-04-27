package hw06.src

/**
 * An interface for text processors.
 */
trait ITextProcessor {

  /**
   * Prints the top 10 most common words in the specified text file,
   * along with the number of times they each appear, and their
   * proportion relative to the other words in the document
   */
  def textStats()

}
