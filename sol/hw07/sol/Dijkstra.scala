package hw07.sol

import hw07.src._

import scala.collection.mutable.HashMap
import scala.collection.mutable.PriorityQueue
import IGraph.Vertex

import IGraph.Edge

class Dijkstra(graph: DirectedGraph, source: Vertex) extends IDijkstra {
  private val distances = new HashMap[Vertex, Double]()
  dijkstra()
  
  /**
   * Performs Djikstra's algorithm from the given source Vertex, filling the
   * distances HashMap with distance from the source.
   */
  def dijkstra() {
    val ordering = Ordering[Double].on[(Vertex, Double)](-1 * _._2)
    var pq = PriorityQueue[(Vertex, Double)]()(ordering)

    distances.put(source, 0) //all the distances so far calculated

    //queue all of the nodes based on the current distance
    //Double.MaxValue is used as infinity/placeholder for unvisited nodes
    graph.vertices.foreach { v =>
      if (v.equals(source)) pq.+=((source, 0))
      else pq.+=((v, Double.MaxValue))
    }
    //go through all of the nodes in the graph
    while (!pq.isEmpty) {
      //get the closest node to the source
      val current = pq.dequeue()

      //stop when all the vertices in the queue are "unreachable"
      //If the closes has the maximum distance then the rest 
      //are also max distance away
      if (distances.get(current._1).isEmpty) {
        return
      }

      //go through the neighbors of the current
      //and update the respective distances
      current._1.getNeighbors.foreach { edge =>
        val dist = distances(current._1) + edge.weight
        
        //if there isn't a route the the node already
        //or if the existing route is longer
        //update it to the new value
        if (distances.get(edge.target).isEmpty || dist < distances.get(edge.target).get) {
          distances.put(edge.target, dist)
          edge.target.parent = Some(current._1)
        }
      }
      //reset priority queue with new distances
      val newPQ = PriorityQueue[(Vertex, Double)]()(ordering)
      pq.foreach(x => 
        if (distances.get(x._1).isDefined) newPQ.+=((x._1, distances.get(x._1).get))
        else newPQ.+=(x))
      pq = newPQ

    }

  }

  /**
   * Finds the shortest distance from the source to the destination
   *
   * @param destination - the destination vertex
   *
   * @return the length of the shortest path from the source
   *         to the given destination
   */
  override def findShortestDistance(destination: Vertex): Option[Double] = {
    if (!distances.get(destination).isDefined) return None
    return distances.get(destination)

  }

  /**
   * Produces the list of vertices comprising the shortest path from the
   * source to the destination.
   *
   * @param destination - the destination vertex
   *
   * @return the list of vertices from the source to the given destination
   *         that comprise the shortest path
   */
  override def findShortestPath(destination: Vertex): Option[List[Vertex]] = {
    if (!distances.get(destination).isDefined) return None
    var path = List(destination)
    while (!path.head.equals(source)) {
      path = path.head.parent.get :: path
    }
    return Some(path)
  }
}