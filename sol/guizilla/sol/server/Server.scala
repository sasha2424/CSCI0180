package guizilla.sol.server

import guizilla.src._

import java.lang.reflect.InvocationTargetException
import java.net._
import java.io._
import scala.collection.immutable.HashMap

/**
 * Class for handling server
 */
class Server {

  val pageMap = new scala.collection.mutable.HashMap[String, Page]
  var ID = 0

  /**
   * Exception for invalid session ID
   */
  class InvalidSessionID(s: String) extends Exception(s) {}

  /**
   * Run main server loop
   */
  def run() {
    val server = new ServerSocket(8082)
    var socket: Socket = null
    try {
      while (true) {
        socket = server.accept()

        val in = socket.getInputStream

        val br = new BufferedReader(new InputStreamReader(in))

        val t = readInput(br)
        socket.shutdownInput()

        val data = t._2
        var link: String = null
        link = t._1.substring(1)

        val out = socket.getOutputStream
        val bw = new BufferedWriter(new OutputStreamWriter(out))
        try {
          val method = getMethodFromLink(link)
          val map = parseFormData(data)
          val id = getIDFromLink(link)
          val htmlPage = getHTMLPageFromLink(method, map, id)

          sendHTMLPageToClient(htmlPage, bw)

        } catch {
          case e: ClassNotFoundException =>
            sendHTMLNotFoundToClient(bw)
          case e: NoSuchMethodException =>
            sendHTMLBadToClient(bw)
          case e: InvocationTargetException =>
            sendHTMLInternalErrorToClient(bw)
          case e: InvalidSessionID =>
            sendHTMLSessionErrorToClient(bw)
          case e: Exception =>
            sendHTMLInternalErrorToClient(bw)
            e.printStackTrace()
        } finally {
          socket.shutdownOutput()
          socket.close()
        }
      }
    } finally {
      socket.close()
      server.close()
    }
  }

  /**
   * Send Internal Server Error page to client
   * @param bw- BufferedReader to write to client socket
   */
  private def sendHTMLInternalErrorToClient(bw: BufferedWriter) {
    bw.write("HTTP/1.0 500 Internal Server Error\n" +
      "Server: Sparkserver/1.0\n" +
      "Connection: close\n" +
      "Content-Type: text/html\n\n")
    bw.write("<html><body><p> Internal Server Error </p></body></html>")
    bw.write("")
    bw.flush()
  }

  /**
   * Send Invalid Session ID Error page to client
   * @param bw- BufferedReader to write to client socket
   */
  private def sendHTMLSessionErrorToClient(bw: BufferedWriter) {
    bw.write("HTTP/1.0 500 Internal Server Error\n" +
      "Server: Sparkserver/1.0\n" +
      "Connection: close\n" +
      "Content-Type: text/html\n\n")
    bw.write("<html><body><p> Internal Server Error\n" +
      "<a href=\"/Index\">Try returning to the Index</a></p></body></html>")
    bw.write("")
    bw.flush()
  }

  /**
   * Send Page Not FOund Error page to client
   * @param bw- BufferedReader to write to client socket
   */
  private def sendHTMLNotFoundToClient(bw: BufferedWriter) {
    bw.write("HTTP/1.0 404 Not Found\n" +
      "Server: Sparkserver/1.0\n" +
      "Connection: close\n" +
      "Content-Type: text/html\n\n")
    bw.write("<html><body><p> Page Not Found </p></body></html>")
    bw.write("")
    bw.flush()
  }

  /**
   * Send Bad Request Error Pae to client
   * @param bw- BufferedWriter to write to client socket
   */
  private def sendHTMLBadToClient(bw: BufferedWriter) {
    bw.write("HTTP/1.0 400 Bad Request\n" +
      "Server: Sparkserver/1.0\n" +
      "Connection: close\n" +
      "Content-Type: text/html\n\n")
    bw.write("<html><body><p> Bad Request </p></body></html>")
    bw.write("")
    bw.flush()
  }
  /**
   * Send HTML page to client
   * @param htmlPage- String containing html
   * @param bw- BufferedWriter to write to client socket
   */
  private def sendHTMLPageToClient(htmlPage: String, bw: BufferedWriter) {
    bw.write("HTTP/1.0 200 OK\n" +
      "Server: Sparkserver/1.0\n" +
      "Connection: close\n" +
      "Content-Type: text/html\n\n")
    bw.write(htmlPage)
    bw.write("")
    bw.flush()
  }

  /**
   * Parse encoded form data
   * @param data- String containing encoded form data
   * @return- Map[String, String] giving field values
   */
  private def parseFormData(data: String): Map[String, String] = {
    var inputs = new HashMap[String, String]
    if (data.length() == 0) {
      return inputs
    }

    for (pair <- data.split("&")) {
      try {
        val p1 = pair.split("=")(0)
        val p2 = pair.split("=")(1)
        inputs = inputs.+(URLDecoder.decode(p1, "UTF-8") ->
          URLDecoder.decode(p2, "UTF-8"))
      } catch {
        case e: IndexOutOfBoundsException =>
        // There was an error parsing this input
        // so we skip it and keep going through to parse the other inputs
        // This can happen when one input in a form is blank
      }
    }
    return inputs
  }

  /**
   * Get method name from link
   * @param link- String containing link
   * @return- String containing method name
   */
  private def getMethodFromLink(link: String): String = {
    if (!link.contains("/")) {
      return "defaultHandler"
    }
    val method = link.substring(link.indexOf("/") + 1)
    return method
  }

  /**
   * Get session ID from link
   * @param link- String containing link
   * @return- String containing ID
   */
  private def getIDFromLink(link: String): String = {
    if (link.startsWith("id:")) {
      val stringID = link.split("/")(0).substring(3)
      if (!pageMap.keySet.contains(stringID)) {
        throw new InvalidSessionID("Invalid Session ID: " + stringID)
      }
      val clone = pageMap.get(stringID).get.clone()
      val cloneID = ID.toString()
      pageMap.put(cloneID, clone)
      ID += 1
      return cloneID
    }
    val name = link.split("/")(0)
    val pageClass = Class.forName("guizilla.sol.pages." + name)
    val page = pageClass.newInstance().asInstanceOf[Page]
    val stringID = ID.toString()
    pageMap += (stringID -> page)
    ID += 1
    return stringID

  }

  /**
   * Gets HTML page for given method, inputs and session ID
   * @param method- String containing method name
   * @param inputs- Map[String, String] containing field values as input to method
   * @param id- String containing session ID
   * @return- String containing html for resulting page
   */
  private def getHTMLPageFromLink(method: String,
                                  inputs: Map[String, String],
                                  id: String): String = {
    val page = pageMap.get(id).get
    val pageMethod = page.getClass.getMethod(method,
      classOf[Map[String, String]], classOf[String])
    return pageMethod.invoke(page, inputs, id).asInstanceOf[String]
  }

  /**
   * Read request from client
   * @param br- BufferedReader for client socket
   */
  private def readInput(br: BufferedReader): (String, String) = {
    var link = ""
    var data = ""

    var s = br.readLine()
    link = s.split(" ")(1)
    if (s.startsWith("POST")) {
      while (s != "") {
        s = br.readLine()
      }
      data = br.readLine()
    }

    return (link, data)
  }

}
/**
 * Object to run Server
 */
object Server extends App {
  (new Server).run()
}