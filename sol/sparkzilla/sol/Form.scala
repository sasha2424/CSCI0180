package sparkzilla.sol

import java.net.URLEncoder

/**
 * A form element of an HTMLPage
 *
 * @param url - The URL to send the form data
 * @param elements - The HTML elements contained in the form
 */
case class Form(val url: String, private var elements: List[HTMLElement])
  extends HTMLElement {
  def setElements(elements: List[HTMLElement]) {
    this.elements = elements
  }

  /**
   * Get form data in encoded form
   * @returns String representing form data
   */
  def getFormData(): String = {
    val data = elements.foldLeft("")((b, a) => a match {
      case TextInput(name, value) =>
        b + URLEncoder.encode(name, "UTF-8") + "=" +
          URLEncoder.encode(
            if (value.isEmpty) ""
            else value.get, "UTF-8") + "&"
      case _ => b
    })
    if (data.length() == 0) {
      ""
    } else {
      data.substring(0, data.length() - 1)
    }
  }

}
