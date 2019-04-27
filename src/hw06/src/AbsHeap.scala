package hw06.src

import scala.reflect.ClassTag

import scala.language.implicitConversions

abstract class AbsHeap[T <% Ordered[T]: ClassTag](val initCapacity: Int) extends IHeap[T] {

  override implicit def coerce(t: T): Ordered[T] = t

  protected var capacity = initCapacity
  protected var heap = Array.fill[Option[T]](capacity)(None)
  protected var numItems = 0

  private val FACTOR = 2 // grow or shrink

  private def isFull: Boolean = (numItems == capacity - 1)
  private def isSparse: Boolean = (numItems < (capacity / FACTOR))
  override def isEmpty: Boolean = (numItems == 0)

  override def getMin = heap(0)

  override def deleteMin: Option[T] =
    if (isEmpty) None
    else {
      val toReturn = getMin

      // Decrement numItems
      numItems -= 1

      // Move last item to the root
      heap(0) = heap(numItems)
      heap(numItems) = None

      // Restore heap order
      siftDown(0)

      if (isSparse) resizeArray(capacity / FACTOR)

      toReturn
    }

  override def insert(item: T) {
    if (isFull) resizeArray(capacity * FACTOR)

    // Insert new ordered! item into heap
    assert(heap(numItems) == None)
    heap(numItems) = Some(item)

    // Restore heap order
    siftUp(numItems)

    // Increment numItems
    numItems += 1
  }

  private def resizeArray(newCapacity: Int) {
    val newHeap = Array.fill[Option[T]](newCapacity)(None)
    for (i <- 0 to numItems) newHeap(i) = heap(i)

    heap = newHeap
    capacity = newCapacity
    // numItems is unchanged
  }

  /**
   * Swaps two elements in heap
   *
   * @param source - the index of the first element in the heap
   * @param target - the index of the second element in the heap
   */
  protected def swap(idx1: Int, idx2: Int) {
    val temp = heap(idx1)
    heap(idx1) = heap(idx2)
    heap(idx2) = temp
  }

  /**
   * Makes sure that the element located at index doesn't break the heap's invariant.
   * If an item's value is less than its parent's, swaps the parent and child.
   *
   * @param index - index of the item to inspect and shift if necessary.
   */
  def siftUp(index: Int)

  /**
   * Makes sure that the element located at index doesn't break the heap's invariant
   * If an item's value is greater than its left or right child, swaps the parent
   * with the smaller child.
   *
   * @param index - index of the item to inspect and shift if necessary.
   */
  def siftDown(index: Int)

  override def toString: String =
    (for (i <- 0 until numItems) yield "(" + i + ": " + heap(i) + ")").mkString(" ")

  override def equals(that: Any): Boolean = that match {
    case AbsHeap(thatHeap) => heap.flatten sameElements thatHeap.flatten.toIterable
    case _                 => false
  }

  def iterator: Iterator[T] = heap.flatten.iterator

}

object AbsHeap {

  def unapply[T: ClassTag](absHeap: AbsHeap[T]): Option[Array[Option[T]]] =
    if (absHeap == null) None
    else Some(absHeap.heap)

}
