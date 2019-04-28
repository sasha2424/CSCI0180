package guizilla.sol.client

import javafx.event.ActionEvent

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import javafx.scene.layout.Pane
import javafx.scene.layout.HBox
import javafx.scene.control._
import javafx.stage.Stage
import javafx.event._
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue

import java.net._

import java.io._

import java.io.BufferedReader
import java.util.ArrayList

import guizilla.sol.client._
import guizilla.src.parser._
import guizilla.src._

class InvalidURLException extends Exception

/**
  * Class responsible for handling browser navigation.
  */
class GUIBrowser {

  @FXML protected var gPane: GridPane = null
  @FXML protected var urlBar: TextField = null
  @FXML var box: VBox = null

  private var stage: Stage = null
  private var urlText: String = null

  val port = 8082

  var currentHost: String = null
  val homePage = List(
    PageText("\n\nG U I Z I L L A   B R O W S E R\n______________________________\n"),
    PageText("Searching Made Easy\n"),
    Link("http://localhost/Index", PageText("#Go to Index")))
  val connectionErrorPage =
    List(new PageText("\n\nError communicating with server\n\n"))
  val unknownHostPage = List(new PageText("\n\nUnknown host\n\n"))

  var pageCache = List[(String, List[HTMLElement])]()
  pageCache = (null, homePage) :: pageCache

  /**
    * Handles the pressing of the submit button on the main GUI page.
    */

  @FXML def handleQuitButtonAction(event: ActionEvent) {
    stage.close()
  }

  /**
    * Handles the pressing of the back button on the main GUI page.
    */
  @FXML def handleBackButtonAction(event: ActionEvent) {
    pageCache = pageCache.tail

    if (pageCache.isEmpty) {
      pageCache = (currentHost, homePage) :: pageCache
    }
    currentHost = pageCache.head._1

    render()
  }

  /**
    * Handles submitting URL button action.
    */
  @FXML def handleSubmitButtonAction(event: ActionEvent) {
    urlText = urlBar.getText
    connectToUrl(urlText, "")
    render()
  }

  def render() {
    renderPage(pageCache.head._2)
  }

  /**
    * Sets the stage field of the controller to the given stage.
    *
    * @param stage The stage
    */
  def setStage(stage: Stage) {
    this.stage = stage
    render()
  }

  // TODO: implement a rendering method.
  // HINT: If you want to add elements to your box and update the stage, what
  // fields might you want the rendering process to have access to?

  /**
    * Parses URL to give host and path
    * @param url- String containing url
    * @returns- (String, String) containing host and path
    */
  //unit-testable
  private def parseUrl(url: String): (String, String) = {
    if (url.startsWith("http://")) {
      val address = url.replace("http://", "")
      val host = address.substring(
        0,
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
    }

    println("Server responded with OK")
    println("Parsing Page...")
    pageCache = (host, getHTMLElementList(br)) :: pageCache
  }

  /**
    * Renders page
    * @param page- Page to be rendered
    */
  private def renderPage(page: List[HTMLElement]) {
    println("Rendering Page...")
    println("-----------")
    box.getChildren.clear()
    page.foreach { renderElement(_, box) }
    stage.show()
  }

  /**
    * Renders HTML element appropriately in HBox
    * @param ele- HTMLElement to be rendered
    * @param box- VBox in which element needs to be rendered
    */
  private def renderElement(ele: HTMLElement, box: Pane) {
    ele match {
      case Paragraph(ele) =>
        val hbox = new HBox();
        ele.foreach { x => renderElement(x, hbox) }
        box.getChildren.add(hbox)
      case PageText(content) =>
        val label = new Label()
        label.setText(content)
        label.wrapTextProperty().setValue(true)
        box.getChildren.add(label)
      case Link(ele, PageText(content)) =>
        var data = if (content.startsWith("#")) "#" else ""
        var text = if (content.startsWith("#")) content.substring(1) else content
        if (content.contains("%")) {
          data = content.split("%")(0)
          text = content.split("%")(1)
        }
        if (data.startsWith("#")) {
          val button = new Button(text)
          button.setOnAction(
            new EventHandler[ActionEvent]() {
              override def handle(a: ActionEvent) {
                connectToUrl(ele, data.substring(1))
                render()
              }
            })
          box.getChildren.add(button)
        } else {
          val button = new Hyperlink(text)
          button.setOnAction(
            new EventHandler[ActionEvent]() {
              override def handle(a: ActionEvent) {
                connectToUrl(ele, data)
                render()
              }
            })
          box.getChildren.add(button)
        }
      case Form(url, ele) =>
        ele.foreach { x => renderElement(x, box) }
      case t: TextInput =>
        val textField = new TextField()
        if (t.getValue.isDefined) textField.setText(t.getValue.get)
        textField.textProperty().addListener(new ChangeListener[String]() {
          override def changed(ov: ObservableValue[_ <: String], oldV: String, newV: String) {
            t.setValue(newV)
          }

        })
        box.getChildren.add(textField)
      case SubmitInput(form) =>

        val button = new Button("Submit")
        button.setFont(Font.font ("Verdana", 20))
        button.setStyle("-fx-text-fill: #0000ff");
        button.setOnAction(
          new EventHandler[ActionEvent]() {
            override def handle(a: ActionEvent) {
              connectToUrl(form.url, form.getFormData)
              render()
            }
          })
        box.getChildren.add(button)
    }
  }

  /**
    * Parses the input from the server into an HTMLElement list.
    * @param inputFromServer- BufferedReader containing HTML from server
    * @returns HTMLElement list containing hierarchical list of the HTMLElements
    */
  private def getHTMLElementList(inputFromServer: BufferedReader): List[HTMLElement] = {
    val parser = new HTMLParser(new HTMLTokenizer(inputFromServer))
    println("TEST")
    return parser.parse().toHTML
  }

}
