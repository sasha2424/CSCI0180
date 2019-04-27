package hw05.sol.copy

import tester.Tester

class VoteCountingTest {

  /**
   * Tests the totalCost method for Checkout
   *
   * @param t - Our tester
   */
  def testCandPercentage(t: Tester) {
    var list = List[Vote]()

    t.checkExpect((new VoteCounting(list)).candPercentage("a"), 0.0)
    list = (new Vote("a", "A")) :: list
    t.checkExpect((new VoteCounting(list)).candPercentage("a"), 1.0)
    t.checkExpect((new VoteCounting(list)).candPercentage("b"), 0.0)
    list = (new Vote("b", "Electric")) :: list
    t.checkExpect((new VoteCounting(list)).candPercentage("a"), 1.0)
    t.checkExpect((new VoteCounting(list)).candPercentage("b"), 0.0)
    list = (new Vote("a", "A")) :: list
    list = (new Vote("a", "A")) :: list
    list = (new Vote("e", "B")) :: list
    t.checkExpect((new VoteCounting(list)).candPercentage("a"), 0.75)
    list = (new Vote("f", "Electric")) :: list
    list = (new Vote("g", "Electric")) :: list
    list = (new Vote("h", "Electric")) :: list
    t.checkExpect((new VoteCounting(list)).candPercentage("a"), 0.75)
    t.checkExpect((new VoteCounting(list)).candPercentage("e"), 0.25)
    t.checkExpect((new VoteCounting(list)).candPercentage("g"), 0.0)
    list = (new Vote("i", "C")) :: list
    list = (new Vote("j", "C")) :: list
    list = (new Vote("k", "C")) :: list
    list = (new Vote("l", "C")) :: list
    list = (new Vote("m", "C")) :: list
    t.checkExpect((new VoteCounting(list)).candPercentage("a"), 0.3333333333333333)
    t.checkExpect((new VoteCounting(list)).candPercentage("i"), 0.1111111111111111)
    t.checkExpect((new VoteCounting(list)).candPercentage("m"), 0.1111111111111111)
    t.checkExpect((new VoteCounting(list)).candPercentage("j"), 0.1111111111111111)

  }

}

/**
 * A companion object to the VoteCountingTest class
 */
object VoteCountingTest extends App { 
  Tester.run(new VoteCountingTest)
}

