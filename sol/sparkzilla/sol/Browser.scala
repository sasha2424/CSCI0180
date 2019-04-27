package sparkzilla.sol

import java.net._

import java.io._

import java.io.BufferedReader
import java.util.ArrayList

import sparkzilla.src.parser.HTMLParser
import sparkzilla.src.parser.HTMLPage
import sparkzilla.src.HTMLTokenizer
import sparkzilla.src.parser.ParseException

/**
 * A text-based browser class
 */
class Browser() {

  val port = 8082
  val interactiveElementList = new ArrayList[HTMLElement]
  var pageCache = List[(String, List[HTMLElement])]()

  var currentHost: String = null
  val homePage = List(PageText("\n\nWelcome To Sparkzilla?\n\n"))
  val connectionErrorPage =
    List(new PageText("\n\nError communicating with server\n\n"))
  val unknownHostPage = List(new PageText("\n\nUnknown host\n\n"))

  /**
   * Runs browser
   * @returns Unit
   */
  private def runBrowser() {

    var br: BufferedReader = null
    var input = 0
    try {
      br = new BufferedReader(new InputStreamReader(System.in))

      while (input != 3) {
        try {
          if (pageCache.isEmpty) {
            renderPage(homePage)
          } else {
            renderPage(pageCache.head._2)
          }
          promptUser()
          val text = br.readLine()
          if (text == null) return
          input = (text).toInt
          doAction(input, br)
        } catch {
          case e: NumberFormatException =>
            println("Please input a valid number")
        }
      }
    } finally {
      println("Good Bye!")
      br.close()
    }
  }

  /**
   * Prompt user to take action
   * @returns Unit
   */
  private def promptUser() {
    println("-----------")
    print("Actions: (1) Back, (2) Go to URL, (3) Quit")
    if (interactiveElementList.size() == 1) {
      print(", (4) Page Elements")
    }
    if (interactiveElementList.size() > 1) {
      print(", (4-" + (interactiveElementList.size() + 3) + ") Page Elements")
    }
    println(":")
  }

  /**
   * Parses URL to give host and path
   * @param url- String containing url
   * @returns- (String, String) containing host and path
   */
  //unit-testable
  private def parseUrl(url: String): (String, String) = {
    if (url.startsWith("http://")) {
      val address = url.replace("http://", "")
      val host = address.substring(0,
        if (address.indexOf("/") == -1) address.length
        else address.indexOf("/"))
      val path = address.substring(
        if (address.indexOf("/") == -1) address.length
        else address.indexOf("/"))
      currentHost = host
      (host, if (path == "") "/" else path)
    } else if (url.startsWith("/")) {
      val host = currentHost
      val path = url.substring(url.indexOf("/"))
      (host, path)
    } else {
      throw new InvalidURLException
    }
  }
  /**
   * Connect to specified URL with form data, if any
   * @param url- String containing URL
   * @param formData- String containing encoded form data
   * @returns Unit
   */
  private def connectToUrl(url: String, formData: String) {
    try {
      val parsedUrl = parseUrl(url)
      loadPage(parsedUrl._1, parsedUrl._2, formData)
    } catch {
      case e: InvalidURLException =>
        println("Invalid Url")
      case e: UnknownHostException =>
        pageCache = (currentHost, unknownHostPage) :: pageCache
      case e: ConnectException =>
        pageCache = (currentHost, connectionErrorPage) :: pageCache
      case e: SocketException =>
        println("Connection Lost")
      case e: NullPointerException =>
        println("Invalid Url")
    }

  }

  /**
   * Sends request with given page details and adds page to cache
   * @param host- String containing host name
   * @param path- String containing path to file
   * @param formData- String containing encoded form data
   * @returns Unit
   */
  private def loadPage(host: String, path: String, formData: String) {
    println("Connecting to: " + host + ":" + port)
    val socket: Socket = new Socket(host, port)

    println("Connected")
    val br = new BufferedReader(new InputStreamReader(socket.getInputStream))
    val bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream))

    println("Requesting page and sending data (if need be): " + path)
    if (formData == "") {
      bw.write("GET " + path + " HTTP/1.0\r\n" +
        "Connection: close\r\n" +
        "User-Agent: Sparkzilla/2.0\r\n\r\n")
    } else {
      bw.write("POST " + path + " HTTP/1.0\r\n" +
        "Connection: close\r\n" +
        "User-Agent: Sparkzilla/2.0\r\n" +
        "Content-Type: application/x-www-form-urlencoded\r\n" +
        "Content-Length: " + formData.length() + "\r\n\r\n" + formData + "\r\n")
      bw.flush()
    }
    bw.flush()
    print("Request Sent")

    var line = br.readLine()
    while (line != "") {
      line = br.readLine()
      println("reading: " + line);
    }

    println("Server responded with OK")
    println("Parsing Page...")
    pageCache = (host, getHTMLElementList(br)) :: pageCache
  }

  /**
   * Performs action entered by user
   * @param i- Int representing user input
   * @param br- BufferedReader to accept input
   * @returns Unit
   */
  private def doAction(i: Int, br: BufferedReader) {
    i match {
      case 1 =>
        if (pageCache.isEmpty) {
          println("No pages to go back to")
        } else {
          pageCache = pageCache.tail
          currentHost = pageCache.head._1
        }
      case 2 =>
        print("Enter Url:")
        val url = br.readLine()
        connectToUrl(url, "")
      case 3 => Unit
      case _ =>
        if (i > 3 && i <= interactiveElementList.size + 3) {
          interactiveElementList.get(i - 4) match {
            case Link(ele, PageText(content)) =>
              connectToUrl(ele, "")
            case t: TextInput =>
              print("Enter value for field " + t.name + ": ")
              val text = br.readLine
              if (text == null) return
              t.setValue(text)
            case form: Form =>
              connectToUrl(form.url, form.getFormData)
          }
        } else {
          println("Invalid Code")
        }
    }
  }

  /**
   * Renders page
   * @param page- Page to be rendered
   */
  private def renderPage(page: List[HTMLElement]) {
    interactiveElementList.clear()
    println("Rendering Page...")
    println("-----------")
    page.foreach { renderElement(_) }
  }

  private def renderElement(ele: HTMLElement) {
    ele match {
      case Paragraph(ele) =>
        ele.foreach { x => renderElement(x) }
      case PageText(content) =>
        println(content)
      case Link(ele, PageText(content)) =>
        interactiveElementList.add(new Link(ele, PageText(content)))
        println(content + "[" + (interactiveElementList.size() + 3) + "]")
      case Form(url, ele) =>
        ele.foreach { x => renderElement(x) }
      case t: TextInput =>
        interactiveElementList.add(t)
        if (t.isBlank) {
          println("___________ [" + (interactiveElementList.size() + 3) + "]")
        } else {
          println("__" + t.getValue.get
            + "__ [" + (interactiveElementList.size() + 3) + "]")
        }
      case SubmitInput(form) =>
        interactiveElementList.add(form)
        println("[Submit] [" + (interactiveElementList.size() + 3) + "]")
    }
  }

  /**
   * Parses the input from the server into an HTMLElement list.
   * @param inputFromServer- BufferedReader containing HTML from server
   * @returns HTMLElement list containing hierarchical list of the HTMLElements
   */
  private def getHTMLElementList(inputFromServer: BufferedReader): List[HTMLElement] = {
    val parser = new HTMLParser(new HTMLTokenizer(inputFromServer))
    return parser.parse().toHTML
  }

}

class InvalidURLException extends Exception

object Browser extends App {
  val b = new Browser()
  b.runBrowser()
}
