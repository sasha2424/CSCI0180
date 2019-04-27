package guizilla.sol.pages

import guizilla.src._
import java.net._
import java.io._
import java.util.HashSet

class Search extends Page {

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
    val errorPage = "<html><body><p>There was an error retrieving your query.</p>" +
      "<p><a href=\"/Search\">Return</a></p></body></html>"
    if (!inputs.contains("key")) {
      return errorPage
    }
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
      response = response.substring(9)
    var display = ""
    var i = 1
    for (x <- response.split("\t").map { x => x.substring(x.indexOf(" ") + 1) }) {
      display += "<p><a href=\"/id:" + sessionId + "/viewResults\">title=" + x + "%" + i.toString() + "." + x + "\n</a></p>"
      i += 1
    }
    return "<html><body>" +
      "<p>Search Results: \n</p>" +
      display +
      "</body></html>"
  }

  def viewResults(inputs: Map[String, String], sessionId: String): String = {
    val socket = new Socket(host, port2)
    val br = new BufferedReader(new InputStreamReader(socket.getInputStream))
    val bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream))
    val errorPage = "<html><body><p>Sorry, that was not a result.</p></body></html>"
    bw.write(inputs("title") + "\n")

    bw.flush()
    var display: String = ""
    var line = br.readLine()
    while (line != null) {
      display += line + "\n"
      line = br.readLine()
    }
    bw.close()
    br.close()
    return "<html><body><p>" + inputs("title") + "\n</p>" +
      "<p>" + display + "</p>" +
      "</body></html>"
  }

}