package hw06.sol

import hw06.src.AbsHeap

import scala.reflect.ClassTag
class Heap[T <% Ordered[T]: ClassTag](override val initCapacity: Int) extends AbsHeap[T](initCapacity) {

  
  /**
   * Makes sure that the element located at index doesn't break the heap's invariant.
   * If an item's value is less than its parent's, swaps the parent and child.
   *
   * @param index - index of the item to inspect and shift if necessary.
   */
  override def siftUp(index: Int) {
    if (index == 0) return //we have reached the top of the heap so more more ordering is needed
    val parent = ((index-1)/2).toInt
    if (heap(parent).get > heap(index).get) {
      swap(parent, index)
      siftUp(parent) // continue sifting up from parent
    }
  }

  /**
   * (copied for convenience)
   * Makes sure that the element located at index doesn't break the heap's invariant
   * If an item's value is greater than its left or right child, swaps the parent
   * with the smaller child.
   *
   * @param index - index of the item to inspect and shift if necessary.
   */
  override def siftDown(index: Int) {
    val child1 = index * 2
    val child2 = index * 2 + 1
    if (child2 >= numItems || heap(child1).isEmpty) return // we are at the bottom of the heap or outside of the internal array
    if (heap(child2).isEmpty) { 
      //there is only one child to switch with
      //switch with child if necessary
      //and then terminate recursion
      if (heap(child1).get < heap(index).get){
        swap(index, child1)
        siftDown(child1)
      }
      return
    }
    if (heap(child1).get >= heap(index).get && heap(child2).get >= heap(index).get) {
      //both children are larger than or equal to parent. no switch is needed
      return
    }
    
    //both children exit so swap with the smaller child
    val smallerChild = if (heap(child1).get < heap(child2).get) child1 else child2
    swap(index, smallerChild)
    siftDown(smallerChild)
  }

}