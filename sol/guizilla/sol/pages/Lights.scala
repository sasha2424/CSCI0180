package guizilla.sol.pages

import guizilla.src._

class Lights extends Page {
  private var text = ""
  private var option = 0

  override def defaultHandler(inputs: Map[String, String], sessionId: String): String =
    menu(inputs, sessionId)

  def menu(inputs: Map[String, String], sessionId: String): String =
    "<html><body><p>StringBuilder\n--------------</p>" +
      "<form method=\"post\" action=\"/id:" + sessionId + "/result\">" +
      "<p> Please enter some text and select an option</p>" +
      "<p> String: </p>" +
      "<input type=\"text\" name=\"text\" />" +
      "<p> <a href=\"/StringBuilder\">Return to the Menu</a><a href=\"/StringBuilder\">Return to the Menu</a><a href=\"/StringBuilder\">Return to the Menu</a></p>" +
      "<input type=\"text\" name=\"option\" />" +
      "<input type=\"submit\" value=\"submit\" />" +
      "</form></body></html>"

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