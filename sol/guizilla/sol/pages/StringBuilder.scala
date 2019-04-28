package guizilla.sol.pages

import guizilla.src._

/**
  * Page to play with strings
  */
class StringBuilder extends Page {

  override def defaultHandler(inputs: Map[String, String], sessionId: String): String =
    menu(inputs, sessionId)

  /**
    * Menu page to enter string and desired option
    * @param inputs- Map[String, String]
    * @param sessionId- String containing session ID
    */
  def menu(inputs: Map[String, String], sessionId: String): String =
    "<html><body><p>StringBuilder\n--------------</p>" +
      "<form method=\"post\" action=\"/id:" + sessionId + "/result\">" +
      "<p> Please enter some text and select an option</p>" +
      "<p> String: </p>" +
      "<input type=\"text\" name=\"text\" />" +
      "<p> Options:\n1) Reverse\n2) Double\n3)Surprize! </p>" +
      "<input type=\"text\" name=\"option\" />" +
      "<input type=\"submit\" value=\"submit\" />" +
      "</form></body></html>"

  /**
    * Result page
    * @param inputs- Map[String, String] containing string and option
    * @param sessionId- String containing session ID
    */
  def result(inputs: Map[String, String], sessionId: String): String =
    inputs.get("option") match {
      case Some("1") =>
        "<html><body>" +
          "<p>" + inputs.get("text").get.reverse + "</p>" +
          "</body></html>"
      case Some("2") =>
        "<html><body>" +
          "<p>" + inputs.get("text").get + inputs.get("text").get + "</p>" +
          "</body></html>"
      case Some("3") =>
        "<html><body>" +
          "<p>SURPRIZE!!!\nLMAO GODEEM!!!!</p>" +
          "</body></html>"
      case Some(x) =>
        "<html><body>" +
          "<p>That isn't a valid option!" +
          "<a href=\"/StringBuilder\">Return to the Menu</a></p></body></html>"
      case None =>
        "<html><body><p>I'm sorry, there was an error retrieving your input." +
          "<a href=\"/StringBuilder\">Return to the Menu</a></p></body></html>"
    }

}