package hw07.src

import IGraph.Vertex

/**
 * Trait defining operations of a Dijkstra class.
 */
trait IDijkstra {

  /**
   * Finds the shortest distance from the source to the destination
   *
   * @param destination - the destination vertex
   *
   * @return the length of the shortest path from the source
   *         to the given destination
   */
  def findShortestDistance(destination: Vertex): Option[Double]

  /**
   * Produces the list of vertices comprising the shortest path from the
   * source to the destination.
   *
   * @param destination - the destination vertex
   *
   * @return the list of vertices from the source to the given destination
   *         that comprise the shortest path
   */
  def findShortestPath(destination: Vertex): Option[List[Vertex]]

}
