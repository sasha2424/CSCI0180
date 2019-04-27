package hw05.sol.copy

import tester.Tester

/**
 * A class to check all of our scala constructors for hw05
 */
class ConstructorTest {

  // Testing all constructors in the Shopping Cart problem
  /**
   * Tests the CartItem constructor
   * @param t - our tester
   */
  def testCartItemConstructor(t: Tester) {
    new CartItem("blueno", 0.2)
  }

  /**
   * Tests the Checkout constructor
   * @param t - our tester
   */
  def testCheckoutConstructor(t: Tester) {
    new Checkout()
  }
 
  /**
   * Tests the totalCost method for Checkout
   *
   * @param t - Our tester
   */
  def testTotalCostMethod(t: Tester) {
    val check = new Checkout()
    val blueno = new CartItem("blueno", 0.2)
    val cart = List(blueno)
    check.totalCost(cart)
  }


  // Testing all the constructors in the Vote Counting problem

  /**
   * Tests the Vote constructor
   * @param t - our tester
   */
  def testVoteConstructor(t: Tester) {
    new Vote("Pikachu", "Electric")
  }
  /**

   * Tests the VoteCounting constructor and candPercentage() method
   * @param t - our tester
   */
  def VoteCounting(t: Tester) {
    val vc = new VoteCounting(List[Vote]())
    vc.candPercentage("Dylan")
  }


  // Testing all the constructors in the Earthquake problem
  /**
   * Tests the MaxHzReport constructor
   * @param t - our tester
   */
  def testMaxHzReportConstructor(t: Tester) {
    new MaxHzReport(20151004, 200.3)
  }


  /**
   * Tests the Earthquake constructor
   * @param t - our tester
   */
  def testEarthquakeConstructor(t: Tester) {
    new Earthquake()
  }

}

/**
 * A companion object to the ConstructorTest class
 */
object ConstructorTest extends App {
  Tester.run(new ConstructorTest())
}


