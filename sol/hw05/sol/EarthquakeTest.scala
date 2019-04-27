package hw05.sol.copy

import tester.Tester

class EarthquakeTest{

  //20150904 10 20150905 .001 20151004 200 150 175 20151005 0.002 0.03 20151007 130 0.54 20151101 17 20151102 4 8 1 9 3 4 20151108 20

  def testEarthquake(t: Tester) {
    val q = new Earthquake();
    val L1 = List(20150904, 10, 20150905, .001, 20151004, 200, 150, 175, 20151005, 0.002, 0.03, 20151007, 130, 0.54, 20151101, 17, 20151102, 4, 8, 1, 9, 3, 4, 20151108, 20)

    val act1 = List[MaxHzReport](new MaxHzReport(20151004, 200.0), new MaxHzReport(20151005, .03), new MaxHzReport(20151007, 130.0))
    t.checkExpect(act1, q.dailyMaxForMonth(L1, 10))

    val act2 = List[MaxHzReport](new MaxHzReport(20150904, 10.0), new MaxHzReport(20150905, .001))
    t.checkExpect(act2, q.dailyMaxForMonth(L1, 9))

    val act3 = List[MaxHzReport]()
    t.checkExpect(act3, q.dailyMaxForMonth(L1, 8))

    val act4 = List[MaxHzReport](new MaxHzReport(20151101, 17.0), new MaxHzReport(20151102, 9), new MaxHzReport(20151108, 20.0))
    t.checkExpect(act4, q.dailyMaxForMonth(L1, 11))
  }
} 

/**
 * A companion object to the ConstructorTest class
 */
object EarthquakeTest extends App {
  Tester.run(new EarthquakeTest)
}