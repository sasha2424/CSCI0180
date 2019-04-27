package hw05.sol.copy

import tester.Tester

class CheckoutTest {

  /**
   * Tests the totalCost method for Checkout
   *
   * @param t - Our tester
   */
  def testTotalCostMethod(t: Tester) {
    val a = new CartItem("nails", 10)
    val b = new CartItem("nails", 20)
    val c = new CartItem("nails", 30)
    val d = new CartItem("smoke alarm", 10)
    val e = new CartItem("smoke alarm", 15.5)
    val k = new CartItem("smoke alarm", 18.5)
    val f = new CartItem("f", 10)
    val g = new CartItem("g", 14.44)
    val h = new CartItem("h", 60)
    val i = new CartItem("i", 50)

    val check = new Checkout()

    var cart = List[CartItem]()

    t.checkExpect(check.totalCost(cart), 0d);
    cart = a :: cart
    t.checkExpect(check.totalCost(cart), 10d);
    cart = b :: cart
    cart = f :: cart
    cart = g :: cart
    cart = h :: cart
    t.checkExpect(check.totalCost(cart), 114.44d);
    cart = d :: cart
    t.checkExpect(check.totalCost(cart), 124.44d);
    cart = e :: cart
    t.checkExpect(check.totalCost(cart), 129.94d);
    cart = k :: cart
    t.checkExpect(check.totalCost(cart), 148.44d);

    cart = List[CartItem]()

    cart = d :: cart
    t.checkExpect(check.totalCost(cart), 10d);
    cart = e :: cart
    t.checkExpect(check.totalCost(cart), 15.5);
    cart = k :: cart
    t.checkExpect(check.totalCost(cart), 34d);
    cart = a :: cart
    t.checkExpect(check.totalCost(cart), 44d);
    cart = b :: cart
    t.checkExpect(check.totalCost(cart), 64d);
    cart = c :: cart
    t.checkExpect(check.totalCost(cart), 85d);
  }
 
}
  
 
/**
 * A companion object to the ConstructorTest class
 */
object CheckoutTest extends App {
  Tester.run(new CheckoutTest)
}

