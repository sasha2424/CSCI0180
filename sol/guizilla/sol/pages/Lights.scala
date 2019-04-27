package guizilla.sol.pages

import guizilla.src._

class Lights extends Page {
  private val rows = 5
  private val cols = 5
  private var grid = Array.ofDim[Boolean](rows, cols)

  for (i <- 0 until rows) {
    for (j <- 0 until cols) {
      grid(i)(j) = if (Math.random() < .5) true else false
    }
  }

  override def defaultHandler(inputs: Map[String, String], sessionId: String): String =
    game(inputs, sessionId)

  def game(inputs: Map[String, String], sessionId: String): String = {
    if(inputs.contains("i") && inputs.contains("j")){
      clickOnGrid(inputs.get("i").get.toInt,inputs.get("j").get.toInt)
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
      lights +
      "</body></html>"
  }
  
  def clickOnGrid(i : Integer, j : Integer) {
    toggleGrid(i,j)
    toggleGrid(i,j+1)
    toggleGrid(i,j-1)
    toggleGrid(i+1,j)
    toggleGrid(i-1,j)
  }

  def toggleGrid(i : Integer, j : Integer) {
    if(i < 0 || i >= rows || j <0 || j >= cols){
      return
    }
    grid(i)(j) = !grid(i)(j)
  }
}