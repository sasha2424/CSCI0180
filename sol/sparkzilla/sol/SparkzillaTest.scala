package sparkzilla.sol

import tester.Tester

class SparkzillaTest {

  def testTextInput(t: Tester) {
    val username = TextInput("john", None)
    t.checkExpect(username.isBlank)
    t.checkExpect(username.getValue, None)
    username.setValue("")
    t.checkExpect(!username.isBlank)
    t.checkExpect(username.getValue, Some(""))
    username.setValue("mallen")
    t.checkExpect(!username.isBlank)
    t.checkExpect(username.getValue, Some("mallen"))
  }

  def testForm(t: Tester) {
    t.checkExpect(Form("", List()).getFormData, "")
    val application = Form(
      "cs18",
      List(TextInput("Lord", None), PageText("2"), TextInput("Peasant", None)))
    t.checkExpect(application.getFormData, "Lord=&Peasant=")
    application.setElements(
      List(TextInput("Lord", Some("Kathi")), PageText("2"), TextInput("Peasant", Some("Me & Partner"))))
    t.checkExpect(application.getFormData, "Lord=Kathi&Peasant=Me+%26+Partner")
  }
}

object SparkzillaTest extends App {
  Tester.run(new SparkzillaTest)
}