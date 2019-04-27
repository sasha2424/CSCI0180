package hw07.src

/**
 * An interface for Graphs
 */
trait IGraph {

  import IGraph.Vertex
  import IGraph.Edge

  /**
   * Adds a Vertex to this graph.
   *
   * @param n - the Vertex to add
   */
  def addVertex(n: Vertex)

  /**
   * Creates an edge between two Vertices with the given weight.
   *
   * @param n1 - the first Vertex of the edge
   * @param n2 - the second Vertex of the edge
   * @param weight - the weight of the edge
   */
  def addEdge(n1: Vertex, n2: Vertex, weight: Double)

}

object IGraph {

  /**
   * A case class representing a Graph vertex.
   */
  case class Vertex(id: Int) {
    var edges: List[Edge] = Nil
    var parent: Option[Vertex] = None

    def getNeighbors: List[Edge] = edges

    override def equals(that: Any) = that match {
      case Vertex(id_num) => {
        // casting that to a Vertex
        val thatVertex: Vertex = that.asInstanceOf[Vertex]
        // checking equality based on id number and edges
        this.id == id_num && (thatVertex.getNeighbors sameElements this.edges)
      }
      case _ => false
    }
  }

  /**
   * A case class representing a Graph edge between two vertices.
   */
  case class Edge(val target: Vertex, val weight: Double) {
    override def equals(that: Any) = that match {
      case Edge(thatTarget, thatWeight) => {
        thatTarget.equals(target) && (thatWeight == weight)
      }
      case _ => false
    }
  }
}
