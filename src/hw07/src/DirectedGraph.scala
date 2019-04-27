package hw07.src

import IGraph.Vertex

import IGraph.Edge

/**
 * The DirectedGraph class represents a directed graph.
 */
class DirectedGraph extends IGraph {

  var vertices: Set[Vertex] = Set()

  override def addVertex(n: Vertex) {
    vertices = vertices + n
  }

  override def addEdge(n1: Vertex, n2: Vertex, weight: Double) {
    n1.edges = new Edge(n2, weight) :: n1.edges
  }

}
