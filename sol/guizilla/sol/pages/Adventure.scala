package guizilla.sol.pages

import guizilla.src._

/**
 * Class for Adventure game page
 */
class Adventure extends Page {
  private var name = ""
  private var playerClass = ""

  override def defaultHandler(inputs: Map[String, String], sessionId: String): String =
    menu(inputs, sessionId)

  /**
   * Menu page to enter name and class
   * @param inputs- Map[String, String]
   * @param sessionId- String containing session ID
   */
  def menu(inputs: Map[String, String], sessionId: String): String =
    "<html><body><p>A D V E N T U R E\n\n</p>" +
      "<form method=\"post\" action=\"/id:" + sessionId + "/check\">" +
      "<p>Please enter your name and choose a class </p>" +
      "<input type=\"text\" name=\"name\" />" +
      "<p>Class Options:\n Mage\tFighter\tRogue </p>" +
      "<input type=\"text\" name=\"class\" />" +
      "<p>\nDepart on an Adventure!</p>" +
      "<input type=\"submit\" value=\"submit\" />" +
      "</form></body></html>"

  /**
   * Name and class confirmation/rejection page
   * @param inputs- Map[String, String] containing chosen name and class
   * @param sessionId- String containing session ID
   */
  def check(inputs: Map[String, String], sessionId: String): String = {
    if (inputs.get("class").isEmpty) {
      return "<html><body>" +
        "<p> Seems like you are a mere commoner</p>" +
        "<p> Come back when you have chosen a class! </p>" +
        "<p><a href=\"/id:" + sessionId +
        "/menu\"> I am ready to choose</a></p>" +
        "</body></html>"
    }
    playerClass = inputs.get("class").get.toLowerCase()
    if (playerClass != "mage" && playerClass != "rogue"
      && playerClass != "fighter") {
      return "<html><body>" +
        "<p> You are a " + playerClass + "? </p>" +
        "<p> You are not worth an adventure. \n\n</p>" +
        "<p><a href=\"/id:" + sessionId + "/menu\"> Respond:\n" +
        "Oh! I thought you asked about my cousin!" +
        " I can tell you what I am.</a></p>" +
        "</body></html>"
    }
    if (inputs.get("name").isEmpty) {
      return "<html><body>" +
        "<p> It seams that you are nameless!</p>" +
        "<p> How can a legendary " + playerClass + " be nameless? </p>" +
        "<p><a href=\"/id:" + sessionId +
        "/menu\"> I have remembered my name! </a></p>" +
        "</body></html>"
    }
    name = inputs.get("name").get
    return "<html><body>" +
      "<p> Well " + name + " seems like you are ready for an adventure</p>" +
      "<p> You will make a legendary " + playerClass + " </p>" +
      "<p><a href=\"/id:" + sessionId +
      "/cave\"> Onward to the cave! </a></p>" +
      "</body></html>"
  }

  /**
   * Page for cave scene
   * @param inputs- Map[String, String]
   * @param sessionId- String containing session ID
   */
  def cave(inputs: Map[String, String], sessionId: String): String =
    "<html><body>" +
      "<p>You enter an ancient cave in the side of a mountain.</p>" +
      "<p>After walking through the dark and moist " +
      "tunnels you see a light!</p>" +
      "<p>Thinking it is the way out you run toward it...\n\n" +
      " ... and get yeeted by a rock pillar hanging from the ceiling</p>" +
      "<p>You wake up and you are in a small hollowing in the rock.</p>" +
      "<p>Before you is a sketchy old man holding a stick.</p>" +
      "<p>The old man sees that you are awake and start wiggling " +
      "his toes.\n\n</p>" +
      "<p>Old man: \n\tAhh a " + playerClass + " You must think you " +
      "are very clever!</p>" +
      "<p>Well riddle me this... " + name + "!\n</p>" +
      "<form method=\"post\" action=\"/id:" + sessionId + "/cave2\">" +
      "<p>What can travel around the world while staying in a corner?</p>" +
      "<input type=\"text\" name=\"riddle\" />" +
      "<p><a href=\"/id:" + sessionId + "/end\"> Try to " + 
      "Grab the stick! </a></p>" +
      "<input type=\"submit\" value=\"submit\" />" +
      "</form></body></html>"

  /**
   * Page for game over
   * @param inputs- Map[String, String]
   * @param sessionId- String containing session ID
   */
  def end(inputs: Map[String, String], sessionId: String): String = {
    return "<html><body>" +
      "<p>" + name + "... it seems that you have died.</p>" +
      "<p> Better luck next time!</p>" +
      "<p><a href=\"/Adventure\"> Try Again</a></p>" +
      "</body></html>"
  }
  /**
   * Page for cave riddle scene
   * @param inputs- Map[String, String] containing answer to riddle
   * @param sessionId- String containing session ID
   */
  def cave2(inputs: Map[String, String], sessionId: String): String = {
    val option =
      playerClass match {
        case "mage" =>
          "<p><a href=\"/id:" + sessionId + "/end\"> Cast a spell to Darken the room</a></p>" +
            "<p><a href=\"/id:" + sessionId + "/win\"> Cast the Yeet Spell</a></p>"
        case "fighter" =>
          "<p><a href=\"/id:" + sessionId + "/end\"> Stab the old man with your sword</a></p>" +
            "<p><a href=\"/id:" + sessionId + "/win\"> Yeet the old man with your sword</a></p>"
        case "rogue" =>
          "<p><a href=\"/id:" + sessionId + "/end\"> Sneek away from the old man</a></p>" +
            "<p><a href=\"/id:" + sessionId + "/win\"> Disappear only to YEET the old man from behind</a></p>"
      }

    if (inputs.get("riddle").isEmpty) { return end(inputs, sessionId) }
    if (inputs.get("riddle").get.toLowerCase().contains("stamp")) {
      return "<html><body>" +
        "<p>Old man: \n\tA stamp!! YES.\n You are clever... but you are pOwErLeSs tO My sTiCc!!!!\n\n</p>" +
        "<p><a href=\"/id:" + sessionId + "/end\"> attempt to tackle the OldMan</a></p>" +
        option +
        "<p><a href=\"/id:" + sessionId + "/end\"> Grab the stick as he swings it at you</a></p>" +
        "</body></html>"
    }
    return end(inputs, sessionId)

  }

  /**
   * Page for victory
   * @param inputs- Map[String, String]
   * @param sessionId- String containing session ID
   */
  def win(inputs: Map[String, String], sessionId: String): String = {
    return "<html><body>" +
      "<p>Congradulations " + name + "! You have become a legend</p>" +
      "<p> The prophecy of the great YEETER has come true</p>" +
      "<p> May you praise the people of this humble kingdome with your yeets!\n\n</p>" +
      "<p><a href=\"/Adventure\"> Yeet yourself into a new form and try again</a></p>" +
      "</body></html>"
  }

}