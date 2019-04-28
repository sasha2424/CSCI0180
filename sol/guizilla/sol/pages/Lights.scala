package guizilla.sol.pages

import guizilla.src._

/**
  * Page for Lights game
  */
class Lights extends Page {
  private val rows = 5
  private val cols = 5
  private var grid = Array.ofDim[Boolean](rows, cols)

  /**
    * Randomly generate board
    */
  for (i <- 0 until rows) {
    for (j <- 0 until cols) {
      grid(i)(j) = if (Math.random() < .5) true else false
    }
  }

  override def clone(): Page = {
    val self = super.clone().asInstanceOf[Lights]
    self.grid = Array.ofDim[Boolean](rows, cols)
    for (i <- 0 until rows) {
      for (j <- 0 until cols) {
        self.grid(i)(j) = grid(i)(j)
      }
    }
    return self
  }

  override def defaultHandler(inputs: Map[String, String], sessionId: String): String =
    game(inputs, sessionId)

  /**
    * Game page
    * @param inputs- Map[String, String] containing move entered
    * @param sessionId- String containing session ID
    */
  def game(inputs: Map[String, String], sessionId: String): String = {
    if (inputs.contains("i") && inputs.contains("j")) {
      clickOnGrid(inputs.get("i").get.toInt, inputs.get("j").get.toInt)
    }

    var lights = ""
    for (i <- 0 until rows) {
      lights += "<p>"
      for (j <- 0 until cols) {
        lights += "<a href=\"/id:" + sessionId + "/game\">#i=" + i + "&j=" + j + "%"
        if (grid(i)(j)) {
          lights += "X</a>"
        } else {
          lights += "O</a>"
        }
      }
      lights += "</p>"
    }

    return "<html><body>" +
      "<p>THE LIGHT GAME\n----------------</p>" +
      "<p> Try to turn off all the light!</p>" +
      "<p> O: on\tX:off\n\n</p>" +
      lights +
      winText() +
      "</body></html>"
  }

  /**
    * Returns win text if user has won
    * @returns- String
    */
  private def winText(): String = {
    for (i <- 0 until rows) {
      for (j <- 0 until cols) {
        if (!grid(i)(j)) return ""
      }
    }
    return "<p>\n\nYOU WIN!\n\n</p>"
  }

  /**
    * Register user's move on particular grid entry
    * @param i- Row number
    * @param j- Column number
    */
  private def clickOnGrid(i: Integer, j: Integer) {
    toggleGrid(i, j)
    toggleGrid(i, j + 1)
    toggleGrid(i, j - 1)
    toggleGrid(i + 1, j)
    toggleGrid(i - 1, j)
  }

  /**
    * Switch light on/off
    * @param i- Row number
    * @param j- Column number
    */
  private def toggleGrid(i: Integer, j: Integer) {
    if (i < 0 || i >= rows || j < 0 || j >= cols) {
      return
    }
    grid(i)(j) = !grid(i)(j)
  }
}