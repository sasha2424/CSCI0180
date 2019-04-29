package guizilla.sol.pages

import guizilla.src._

/**
  * Page to subtract two numbers
  */
class SubNumbers extends Page {
  private var num1 = 0
  override def defaultHandler(inputs: Map[String, String], sessionId: String): String =
    subTwoNumbers(inputs, sessionId)

  /**
    * Main page to enter first number
    * @param inputs- Map[String, String]
    * @param sessionId- String containing session ID
    */
  def subTwoNumbers(inputs: Map[String, String], sessionId: String): String = "<html><body><p>Subtract Two Numbers</p>" +
    "<form method=\"post\" action=\"/id:" + sessionId + "/secondNumber\">" +
    "<p> Please enter the first number you would like to subtract: </p>" +
    "<input type=\"text\" name=\"num1\" />" +
    "<input type=\"submit\" value=\"submit\" />" +
    "</form></body></html>"

  /**
    * Enter second number
    * @param inputs- Map[String, String] containing first number
    * @param sessionId- String containing session ID
    */
  def secondNumber(inputs: Map[String, String], sessionId: String): String =
    inputs.get("num1") match {
      case Some(num) =>
        try {
          num1 = num.toInt
          "<html><body><p>The first number entered was " + num1 + "</p>" +
            "<form method=\"post\" action=\"/id:" + sessionId + "/displayResult\">" +
            "<p>Please enter the second number you would like to add: </p>" +
            "<input type=\"text\" name=\"num2\" />" +
            "<p><a href=\"/id:" + sessionId + "/subTwoNumbers\"> Return to first page </a></p>" +
            "<input type=\"submit\" value=\"submit\" />" +
            "</form></body></html>"
        } catch {
          case _: NumberFormatException =>
            "<html><body><p>I'm sorry you did not input a valid number." +
              "<a href=\"/SubNumbers\">Return</a></p></body></html>"
        }
      case None =>
        "<html><body><p>I'm sorry, there was an error retrieving your input." +
          "<a href=\"/SubNumbers\">Return</a></p></body></html>"
    }

  /**
    * Page to display result
    * @param inputs- Map[String, String] containing second number
    * @param sessionId- String containing session ID
    */
  def displayResult(inputs: Map[String, String], sessionId: String): String =
    inputs.get("num2") match {
      case Some(num) =>
        try {
          val num2 = num.toInt
          "<html><body>" +
            "<p>" + num1 + " - " + num2 + " = " + (num1 - num2) + "</p>" +
            "</body></html>"
        } catch {
          case _: NumberFormatException =>
            "<html><body><p>I'm sorry you did not input a valid number." + "<a hreef=\"/SubNumbers\">Return</a></p></body></html>"
        }
      case None =>
        "<html><body><p>I'm sorry, there was an error retrieving your input." +
          "<a href=\"/SubNumbers\">Return</a></p></body></html>"
    }
}