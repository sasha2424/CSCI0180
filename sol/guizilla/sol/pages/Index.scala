package guizilla.sol.pages

import guizilla.src._

/**
 * Server index page
 */
class Index extends Page {

  override def defaultHandler(inputs: Map[String, String],
                              sessionId: String): String =
    index(inputs, sessionId)

  /**
   * Links to indexed pages
   */
  def index(inputs: Map[String, String], sessionId: String): String =
    "<html><body><p>WELCOME TO THE SERVER\n---------------------</p>" +
      "<p>Directory:</p>" +
      "<p><a href=\"/SubNumbers\">Number Subtracter</a></p>" +
      "<p><a href=\"/StringBuilder\">String Builder</a></p>" +
      "<p><a href=\"/Adventure\">Adventure</a></p>" +
      "<p><a href=\"/Adventure2\">Adventure 2 (not complete)</a></p>" +
      "<p><a href=\"/Search\">Search</a></p>" +
      "<p><a href=\"/Lights\">Lights</a></p>" +
      "</body></html>"

}