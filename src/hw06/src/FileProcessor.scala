package hw06.src

/**
 * An object that reads in a file and stores its contents in an array.
 */
object FileProcessor {

  /**
   * Reads in a file and stores its contents, line by line, in an array.
   *
   * @param filename - the file to read in
   * @return an array storing the file's contents, line by line
   */
  def getText(filename: String): Array[String] = {
    // If you get weird errors from a book you've downloaded from 
    // the Internet, try passing "utf-8" as second arg to fromFile
    val source = scala.io.Source.fromFile(filename) //, "utf-8")
    val toReturn = source.mkString.split("\n")
    source.close()
    toReturn
  }

}
