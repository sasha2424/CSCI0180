package hw07.sol

import tester.Tester
import hw07.src.DirectedGraph
import hw07.src.IGraph.Vertex

/**
  * A class to test our constructors for hw07
  */
class ConstructorTest {

  /**
    * Tests the Dijkstra constructor
    * @param t - our tester
    */
  def testDijkstraConstructor(t: Tester): Unit = {
    val g = new DirectedGraph()
    val v = new Vertex(0)
    g.addVertex(v)
    new Dijkstra(g, v)
  }

}

/**
  * A companion object to the ConstructorTest class
  */
object ConstructorTest extends App {
  Tester.run(new ConstructorTest())
}
