package hw07.sol

import tester.Tester

import hw07.src._

import IGraph.Vertex

import IGraph.Edge

class DijkstraTest {

  def testLine(t: Tester) {
    val g = new DirectedGraph()
    val v1 = new Vertex(0)
    val v2 = new Vertex(1)
    val v3 = new Vertex(2)
    val v4 = new Vertex(3)
    g.addVertex(v1)
    g.addVertex(v2)
    g.addVertex(v3)
    g.addVertex(v4)
    g.addEdge(v1, v2, 5) //edges right
    g.addEdge(v2, v3, 8)
    g.addEdge(v3, v4, 1)

    g.addEdge(v2, v1, 7) //edges left
    g.addEdge(v3, v2, 6)

    val d1 = new Dijkstra(g, v1)
    t.checkExpect(d1.findShortestDistance(v1).get, 0.0)
    t.checkExpect(d1.findShortestDistance(v2).get, 5.0)
    t.checkExpect(d1.findShortestDistance(v3).get, 13.0)
    t.checkExpect(d1.findShortestDistance(v4).get, 14.0)

    t.checkExpect(d1.findShortestPath(v1).get, List(v1))
    t.checkExpect(d1.findShortestPath(v2).get, List(v1, v2))
    t.checkExpect(d1.findShortestPath(v3).get, List(v1, v2, v3))
    t.checkExpect(d1.findShortestPath(v4).get, List(v1, v2, v3, v4))

    val d2 = new Dijkstra(g, v2)
    t.checkExpect(d2.findShortestDistance(v1).get, 7.0)
    t.checkExpect(d2.findShortestDistance(v2).get, 0.0)
    t.checkExpect(d2.findShortestDistance(v3).get, 8.0)
    t.checkExpect(d2.findShortestDistance(v4).get, 9.0)

    val d3 = new Dijkstra(g, v3)
    t.checkExpect(d3.findShortestDistance(v1).get, 13.0)
    t.checkExpect(d3.findShortestDistance(v2).get, 6.0)
    t.checkExpect(d3.findShortestDistance(v3).get, 0.0)
    t.checkExpect(d3.findShortestDistance(v4).get, 1.0)
    t.checkExpect(d3.findShortestPath(v1).get, List(v3, v2, v1))
    t.checkExpect(d3.findShortestPath(v2).get, List(v3, v2))
    t.checkExpect(d3.findShortestPath(v4).get, List(v3, v4))

    val d4 = new Dijkstra(g, v4)
    t.checkExpect(d4.findShortestDistance(v1).isEmpty)
    t.checkExpect(d4.findShortestDistance(v2).isEmpty)
    t.checkExpect(d4.findShortestDistance(v3).isEmpty)
    t.checkExpect(d4.findShortestDistance(v4).get, 0.0)
    t.checkExpect(d4.findShortestPath(v1).isEmpty)
    t.checkExpect(d4.findShortestPath(v2).isEmpty)
    t.checkExpect(d4.findShortestPath(v3).isEmpty)
  }

  def testCycle(t: Tester) {
    val g = new DirectedGraph()
    val v1 = new Vertex(0)
    val v2 = new Vertex(1)
    val v3 = new Vertex(2)
    val v4 = new Vertex(3)
    g.addVertex(v1)
    g.addVertex(v2)
    g.addVertex(v3)
    g.addVertex(v4)
    g.addEdge(v1, v2, 5) //edges right
    g.addEdge(v2, v3, 8)
    g.addEdge(v3, v4, 1)
    g.addEdge(v4, v1, 4)

    g.addEdge(v2, v1, 7) //edges left
    g.addEdge(v3, v2, 2)
    g.addEdge(v4, v3, 3)
    g.addEdge(v1, v4, 1)

    //var for convenience of testing
    //(i can copy paste more)
    var d = new Dijkstra(g, v1)
    t.checkExpect(d.findShortestDistance(v1).get, 0.0)
    t.checkExpect(d.findShortestDistance(v2).get, 5.0)
    t.checkExpect(d.findShortestDistance(v3).get, 4.0)
    t.checkExpect(d.findShortestDistance(v4).get, 1.0)

    t.checkExpect(d.findShortestPath(v1).get, List(v1))
    t.checkExpect(d.findShortestPath(v2).get, List(v1, v2))
    t.checkExpect(d.findShortestPath(v3).get, List(v1, v4, v3))
    t.checkExpect(d.findShortestPath(v4).get, List(v1, v4))

    d = new Dijkstra(g, v2)
    t.checkExpect(d.findShortestDistance(v1).get, 7.0)
    t.checkExpect(d.findShortestDistance(v2).get, 0.0)
    t.checkExpect(d.findShortestDistance(v3).get, 8.0)
    t.checkExpect(d.findShortestDistance(v4).get, 8.0)

    t.checkExpect(d.findShortestPath(v1).get, List(v2, v1))
    t.checkExpect(d.findShortestPath(v2).get, List(v2))
    t.checkExpect(d.findShortestPath(v3).get, List(v2, v3))
    t.checkExpect(d.findShortestPath(v4).get, List(v2, v1, v4))

    d = new Dijkstra(g, v3)
    t.checkExpect(d.findShortestDistance(v1).get, 5.0)
    t.checkExpect(d.findShortestDistance(v2).get, 2.0)
    t.checkExpect(d.findShortestDistance(v3).get, 0.0)
    t.checkExpect(d.findShortestDistance(v4).get, 1.0)

    t.checkExpect(d.findShortestPath(v1).get, List(v3, v4, v1))
    t.checkExpect(d.findShortestPath(v2).get, List(v3, v2))
    t.checkExpect(d.findShortestPath(v3).get, List(v3))
    t.checkExpect(d.findShortestPath(v4).get, List(v3, v4))

    d = new Dijkstra(g, v4)
    t.checkExpect(d.findShortestDistance(v1).get, 4.0)
    t.checkExpect(d.findShortestDistance(v2).get, 5.0)
    t.checkExpect(d.findShortestDistance(v3).get, 3.0)
    t.checkExpect(d.findShortestDistance(v4).get, 0.0)

    t.checkExpect(d.findShortestPath(v1).get, List(v4, v1))
    t.checkExpect(d.findShortestPath(v2).get, List(v4, v3, v2))
    t.checkExpect(d.findShortestPath(v3).get, List(v4, v3))
    t.checkExpect(d.findShortestPath(v4).get, List(v4))
  }

  def testExtreme(t: Tester) {
    val g = new DirectedGraph()
    val v1 = new Vertex(0)
    val v2 = new Vertex(1)
    val v3 = new Vertex(2)
    val v4 = new Vertex(3)
    g.addVertex(v1)
    g.addVertex(v2)
    g.addVertex(v3)
    g.addVertex(v4)
    g.addEdge(v1, v4, 5)
    g.addEdge(v1, v2, 1)
    g.addEdge(v2, v3, 1)
    g.addEdge(v3, v4, 1)
    g.addEdge(v4, v1, 1)

    //var for convenience of testing
    //(i can copy paste more)
    var d = new Dijkstra(g, v1)
    t.checkExpect(d.findShortestDistance(v1).get, 0.0)
    t.checkExpect(d.findShortestDistance(v2).get, 1.0)
    t.checkExpect(d.findShortestDistance(v3).get, 2.0)
    t.checkExpect(d.findShortestDistance(v4).get, 3.0)

    t.checkExpect(d.findShortestPath(v1).get, List(v1))
    t.checkExpect(d.findShortestPath(v2).get, List(v1, v2))
    t.checkExpect(d.findShortestPath(v3).get, List(v1, v2, v3))
    t.checkExpect(d.findShortestPath(v4).get, List(v1, v2, v3, v4))
  }

  def testEqual(t: Tester) {
    val g = new DirectedGraph()
    val v1 = new Vertex(0)
    val v2 = new Vertex(1)
    val v3 = new Vertex(2)
    val v4 = new Vertex(3)
    g.addVertex(v1)
    g.addVertex(v2)
    g.addVertex(v3)
    g.addVertex(v4)
    g.addEdge(v1, v4, 3)
    g.addEdge(v1, v2, 1)
    g.addEdge(v2, v3, 1)
    g.addEdge(v3, v4, 1)
    g.addEdge(v4, v1, 1)

    val d = new Dijkstra(g, v1)
    t.checkExpect(d.findShortestDistance(v1).get, 0.0)
    t.checkExpect(d.findShortestDistance(v2).get, 1.0)
    t.checkExpect(d.findShortestDistance(v3).get, 2.0)
    t.checkExpect(d.findShortestDistance(v4).get, 3.0)

    t.checkExpect(d.findShortestPath(v1).get, List(v1))
    t.checkExpect(d.findShortestPath(v2).get, List(v1, v2))
    t.checkExpect(d.findShortestPath(v3).get, List(v1, v2, v3))
    t.checkExpect(d.findShortestPath(v4).get, List(v1, v4))
  }

  def testExtreme2(t: Tester) {
    val g = new DirectedGraph()
    val v1 = new Vertex(0)
    val v2 = new Vertex(1)
    val v3 = new Vertex(2)
    val v4 = new Vertex(3)
    g.addVertex(v1)
    g.addVertex(v2)
    g.addVertex(v3)
    g.addVertex(v4)
    g.addEdge(v1, v4, 2)
    g.addEdge(v1, v2, 1)
    g.addEdge(v2, v3, 1)
    g.addEdge(v3, v4, 1)
    g.addEdge(v4, v1, 1)

    val d = new Dijkstra(g, v1)
    t.checkExpect(d.findShortestDistance(v1).get, 0.0)
    t.checkExpect(d.findShortestDistance(v2).get, 1.0)
    t.checkExpect(d.findShortestDistance(v3).get, 2.0)
    t.checkExpect(d.findShortestDistance(v4).get, 2.0)

    t.checkExpect(d.findShortestPath(v1).get, List(v1))
    t.checkExpect(d.findShortestPath(v2).get, List(v1, v2))
    t.checkExpect(d.findShortestPath(v3).get, List(v1, v2, v3))
    t.checkExpect(d.findShortestPath(v4).get, List(v1, v4))
  }

  def testRandom(t: Tester) {
    val g = new DirectedGraph()
    val v1 = new Vertex(0)
    val v2 = new Vertex(1)
    val v3 = new Vertex(2)
    val v4 = new Vertex(3)
    val v5 = new Vertex(4)
    val v6 = new Vertex(5)
    g.addVertex(v1)
    g.addVertex(v2)
    g.addVertex(v3)
    g.addVertex(v4)
    g.addVertex(v5)
    g.addVertex(v6)
    g.addEdge(v4, v5, 8)
    g.addEdge(v2, v3, 9)
    g.addEdge(v5, v4, 6)
    g.addEdge(v5, v3, 6)
    g.addEdge(v5, v1, 2)
    g.addEdge(v3, v5, 4)
    g.addEdge(v2, v5, 8)
    g.addEdge(v2, v4, 3)
    g.addEdge(v3, v2, 9)
    g.addEdge(v3, v5, 5)
    g.addEdge(v6, v4, 4)
    g.addEdge(v1, v4, 2)
    g.addEdge(v2, v1, 3)
    
    val d = new Dijkstra(g, v1)
    t.checkExpect(d.findShortestDistance(v1).get, 0.0)
    t.checkExpect(d.findShortestDistance(v2).get, 25.0)
    t.checkExpect(d.findShortestDistance(v3).get, 16.0)
    t.checkExpect(d.findShortestDistance(v4).get, 2.0)
    t.checkExpect(d.findShortestDistance(v5).get, 10.0)
    t.checkExpect(d.findShortestDistance(v6).isEmpty)

    t.checkExpect(d.findShortestPath(v1).get, List(v1))
    t.checkExpect(d.findShortestPath(v2).get, List(v1, v4,v5,v3,v2))
    t.checkExpect(d.findShortestPath(v3).get, List(v1, v4, v5,v3))
    t.checkExpect(d.findShortestPath(v4).get, List(v1, v4))
  }
}

/**
 * A companion object to the ConstructorTest class
 */
object CheckoutTest extends App {
  Tester.run(new DijkstraTest)
}
