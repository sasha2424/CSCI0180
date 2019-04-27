package hw06.src

import scala.language.implicitConversions

/**
 * An interface for heaps.
 */
trait IHeap[T] extends Iterable[T] {

  /**
   * Ensures that the type of the items in the heap is ordered.
   */
  implicit def coerce(t: T): Ordered[T]

  /**
   * @return optionally, the lowest-valued item in the heap
   */
  def getMin: Option[T]

  /**
   * Deletes the lowest-valued item from the heap
   *
   * @return optionally, the lowest-valued item in the heap
   */
  def deleteMin: Option[T]

  /**
   * Inserts an item into the heap
   *
   * @param item - an item to insert into the heap
   */
  def insert(item: T)

}
