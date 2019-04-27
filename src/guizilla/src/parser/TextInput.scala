package guizilla.sol.client

/**
 * A text input element of an HTML page
 *
 * @param name - Name of the text input for communicating with the server
 * @param value - Value of the text input as given by the user
 */
case class TextInput(val name: String, private var value: Option[String])
  extends HTMLElement {

  /**
   * Check whether value has been entered
   * @returns Boolean
   */
  def isBlank = value.isEmpty

  /**
   * Get value entered
   * @returns Option[String]
   */
  def getValue = value

  /**
   * Sets value that is entered
   * @returns Unit
   */
  def setValue(newValue: String) = { value = Some(newValue) }
}