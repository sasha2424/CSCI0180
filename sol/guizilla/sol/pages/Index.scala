package guizilla.sol.pages

import guizilla.src._

class Index extends Page {

  override def defaultHandler(inputs: Map[String, String], sessionId: String): String =
    index(inputs, sessionId)

  def index(inputs: Map[String, String], sessionId: String): String =
    "<html><body><p>WELCOME TO THE SERVER\n---------------------</p>" +
      "<p>Directory: " +
      "<a href=\"/SubNumbers\">Number Subtracter</a>" +
      "<a href=\"/StringBuilder\">String Builder</a>" + 
      "<a href=\"/Adventure\">Adventure</a>" + 
      "<a href=\"/Search\">Search</a>"+ 
      "</p></body></html>"

}