package guizilla.sol.pages

import guizilla.src.Page

import scala.collection.mutable.Set

class Adventure2 extends Page {
  private var name = ""
  private var playerClass = ""
  private var inv = Set[String]()
  
  override def clone() : Page = {
    val self = super.clone().asInstanceOf[Adventure2]
    self.inv = this.inv.clone().asInstanceOf[Set[String]]
    return self
  }

  override def defaultHandler(inputs: Map[String, String], sessionId: String): String =
    menu(inputs, sessionId)

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

  def check(inputs: Map[String, String], sessionId: String): String = {
    if (inputs.get("class").isEmpty) {
      return "<html><body>" +
        "<p> Seems like you are a mere commoner</p>" +
        "<p> Come back when you have chosen a class! </p>" +
        "<p><a href=\"/id:" + sessionId + "/menu\">#I am ready to choose</a></p>" +
        "</body></html>"
    }
    playerClass = inputs.get("class").get.toLowerCase()
    if (playerClass != "mage" && playerClass != "rogue" && playerClass != "fighter") {
      return "<html><body>" +
        "<p> You are a " + playerClass + "? </p>" +
        "<p> You are not worth an adventure. \n\n</p>" +
        "<p><a href=\"/id:" + sessionId + "/menu\">#Respond:   " +
        "Oh! I thought you asked about my cousin! I can tell you what I am.</a></p>" +
        "</body></html>"
    }
    if (inputs.get("name").isEmpty) {
      return "<html><body>" +
        "<p> It seams that you are nameless!</p>" +
        "<p> How can a legendary " + playerClass + " be nameless? </p>" +
        "<p><a href=\"/id:" + sessionId + "/menu\">#I have remembered my name! </a></p>" +
        "</body></html>"
    }
    name = inputs.get("name").get
    return "<html><body>" +
      "<p> Well " + name + " seems like you are ready for an adventure</p>" +
      "<p> You will make a legendary " + playerClass + " </p>" +
      "<p><a href=\"/id:" + sessionId + "/cave\">#Onward to the cave! </a></p>" +
      "</body></html>"
  }

  def cave(inputs: Map[String, String], sessionId: String): String = {
    return "<html><body>" +
      "<p>You enter an ancient cave in the side of a mountain.</p>" +
      "<p>After walking through the dark and moist tunnels you see a light!</p>" +
      "<p>Thinking it is the way out you run toward it...\n\n" +
      " ... and get yeeted by a rock pillar hanging from the ceiling</p>" +
      "<p>You wake up and you are in a small hollowing in the rock.</p>" +
      "<p>Before you is a sketchy old man " + (if (inv.contains("stick")) "" else "holding a stiC") + "</p>" +
      "<p>The old man sees that you are awake and start wiggling his toes.\n\n</p>" +
      (if (inv.contains("stick"))
        "<p><a href=\"/id:" + sessionId + "/caveStick\"> Give back the stick </a></p>"
      else
        "<p><a href=\"/id:" + sessionId + "/caveStick\"> Take his stick </a></p>") +
      (if (inv.contains("old man"))
        "<p><a href=\"/id:" + sessionId + "/end\"> Drop Old Man </a></p>"
      else
        "<p><a href=\"/id:" + sessionId + "/caveOldMan\"> Take Old Man</a></p>") +
      "</body></html>"
  }

  def caveStick(inputs: Map[String, String], sessionId: String): String = {
    if (inv.contains("stick")) {
      inv.-("stick")
      return "<html><body>" +
        "<p>You return the stick to the old man\n</p>" +
        "<p>He cradles it like one would a hat. Very odd indeed.</p>" +
        "<p>Quite a stick it must be. Why else whould he cradle it so?</p>" +
        "<p><a href=\"/id:" + sessionId + "/cave\"> Continue </a></p>" +
        "</body></html>"

    } else {
      inv.+("stick")
      return "<html><body>" +
        "<p>You snatch the stick from old mans hands!\n</p>" +
        "<p>Surprizingly the old man had quite a bit of strength in his old hands.</p>" +
        "<p>You had to pry his fingers one by one. He even tried bitting you!</p>" +
        "<p>This must be one very important stick.</p>" +
        "<p><a href=\"/id:" + sessionId + "/cave\"> Continue </a></p>" +
        "</body></html>"
    }
  }

  def caveOldMan(inputs: Map[String, String], sessionId: String): String = {
    inv.+("old man")
    return "<html><body>" +
      "<p>You Grab the old man and squeeze him into your pack.</p>" +
      "<p>Thankfully his old frail body folds up nicely and there is plenty" +
      " of room for anything else you will find on your adventure\n\n</p>" +
      "<p>You hear the old man murmer from your pack\n\n</p>" +
      "<p>Old Man:\n\tWhat a dweep... urghgh ... he folded me sandwich not hot dog!</p>" +
      "<p>The murmers cease.\n\n</p>" +
      "<p><a href=\"/id:" + sessionId + "/caveDig\">Try to dig out of the cave</a></p>" +
      "<p><a href=\"/id:" + sessionId + "/caveSquat\">S Q U A T</a></p>" +
      (if (playerClass == "mage") "<p><a href=\"/id:" + sessionId + "/caveOldManMage\">Utter the teleport spell</a></p>" else "") +
      (if (playerClass == "fighter") "<p><a href=\"/id:" + sessionId + "/caveOldManFighter\">Strike the Yeet Sword against the cave walls</a></p>" else "") +
      (if (playerClass == "rogue") "<p><a href=\"/id:" + sessionId + "/caveOldManRogue\">Sneak around the darkness of the cave</a></p>" else "") +
      "</body></html>"
  }

  def caveDig(inputs: Map[String, String], sessionId: String): String = {
    return "<html><body>" +
      "<p>You attempt to dig the floor of the cave.</p>" +
      "<p>Maybe your fingernails aren't as strong as you thought.</p>" +
      "<p><a href=\"/id:" + sessionId + "/caveOldMan\">Continue</a></p>" +
      "</body></html>"
  }

  def caveSquat(inputs: Map[String, String], sessionId: String): String = {
    inv.-("old man")
    return "<html><body>" +
      "<p>You squat like a true slav in the cave!\n\n</p>" +
      "<p>The old man doesn't like it. He jumps out of your pack</p>" +
      "<p><a href=\"/id:" + sessionId + "/cave\">Continue</a></p>" +
      "</body></html>"
  }

  def caveOldManMage(inputs: Map[String, String], sessionId: String): String = {
    return "<html><body>" +
      "<p>You telleport into a parallel universe!\n\n</p>" +
      "<p>Seems like you need to work on pronunciation.</p>" +
      "<p><a href=\"/id:" + sessionId + "/cave\">Continue</a></p>" +
      "</body></html>"
  }

  def caveOldManFighter(inputs: Map[String, String], sessionId: String): String = {
    return "<html><body>" +
      "<p>The Yeet Sword slices through the walls of the cave.</p>" +
      "<p>Sharpened by the best blacksmith of the kingdomw the sword not only" +
      "split the rock of the cave but the fabric of space time!</p>" +
      "<p>You fall through the void outside the space time continuum and end up in a familiar place.\n\n</p>" +
      "<p>Remember to tell the smithy to take it easy on the grind wheel next time.</p>" +
      "<p><a href=\"/id:" + sessionId + "/cave\">Continue</a></p>" +
      "</body></html>"
  }

  def caveOldManRogue(inputs: Map[String, String], sessionId: String): String = {
    return "<html><body>" +
      "<p>You carefully step through the darkness of the cave system.</p>" +
      "<p>However, in your obsession of avoiding even the smallest of sounds you" +
      "somehow wandered a few loops around a singularity in the complex space function.</p>" +
      "<p>This resulted in a transfer to a parallel plane of existance.\n\n</p>" +
      "<p>The old man in your pack seems unaffected</p>" +
      "<p><a href=\"/id:" + sessionId + "/cave\">Continue</a></p>" +
      "</body></html>"
  }
  
  def caveOldManDrop(inputs: Map[String, String], sessionId: String): String = {
    inv.-("old man")
    return "<html><body>" +
      "<p>You drop the old man from your pack.\n\n</p>" +
      "<p>Right away the two old men look at each other.</p>" +
      "<p>They mumble at each other in what seems to be a language full composed from \"honest to pete\",\"lawn\", and \"in my day\".</p>" +
      "<p>Suddenly they square up and begin to wiggle their toes at each other</p>" +
      "<p>It is the ultimate battle of wits</p>" +
      "<p>It seems that they can wiggle their toes like this for centuries.\n\n</p>" +
      "<p>Suddenly you are caught in a cross breaze from the wiggling toes and fall into a secret opening</p>" +
      "<p><a href=\"/id:" + sessionId + "/cave\">Fall into the secret opening</a></p>" +
      "</body></html>"
  }

}