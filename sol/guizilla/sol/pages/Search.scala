package guizilla.sol.pages

import guizilla.src._
import java.net._
import java.io._
import java.util.HashSet

class Search extends Page {

  val results = new HashSet[String]()
  val host = "eckert"
  val port1 = 8081
  val port2 = 8082

  def defaultHandler(inputs: Map[String, String], sessionId: String): String = home(inputs, sessionId)

  def home(inputs: Map[String, String], sessionId: String): String = {
    return "<html><body><p>Search</p>" +
      "<form method=\"post\" action=\"/id:" + sessionId + "/results\">" +
      "<p>Enter Keyword:\n</p>" +
      "<input type=\"text\" name=\"key\"/>" +
      "<input type=\"submit\" value=\"submit\">" +
      "</form></body></html>"
  }

  def results(inputs: Map[String, String], sessionId: String): String = {
    val socket = new Socket(host, port1)
    val br = new BufferedReader(new InputStreamReader(socket.getInputStream))
    val bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream))
    val errorPage = "<html><body><p>An error occurred!</p></body></html>"
    bw.write("REQUEST\t" + inputs("key") + "\n")
    bw.flush()
    var response: String = ""
    var line = ""
    while (line != null) {
      line = br.readLine
      response += line
    }
    bw.close()
    br.close()
    if (!response.startsWith("RESPONSE"))
      return errorPage
    else
      response = response.substring(10)
    var display = ""
    var i = 1
    for (x <- response.split("\t ")) {
      results.add(x)
      display += (i.toString + ". " + x + "\n")
      i += 1
    }
    return "<html><body><p>Search Results: \n</p><p>" + display + "</p>" +
      "<form method=\"post\" action=\"/id:" + sessionId + "/viewResults\">" +
      "<p>Enter result title you want to view:\n</p>" +
      "<input type=\"text\" name=\"title\"/>" +
      "</form></body></html>"
  }

  def viewResults(inputs: Map[String, String], sessionId: String): String = {
    val socket = new Socket(host, port2)
    val br = new BufferedReader(new InputStreamReader(socket.getInputStream))
    val bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream))
    val errorPage = "<html><body><p>Sorry, that was not a result.</p></body></html>"
    if (results.contains(inputs("title")))
      bw.write(inputs("title"))
    else
      return errorPage
    bw.flush()
    var display: String = ""
    var line = ""
    while (line != null) {
      line = br.readLine
      display += line + "\n"
    }
    bw.close()
    br.close()
    return "<html><body><p>" + inputs("title") + "\n</p>" +
      "<p>" + display + "</p>" +
      "</body></html>"
  }

}