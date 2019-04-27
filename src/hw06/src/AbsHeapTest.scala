package hw06.src

import tester.Tester

class AbsHeapTest(val heap: IHeap[Int], val heap2: IHeap[Int])

trait testToString extends AbsHeapTest {

  // tests for toString method 
  def testToString(t: Tester) {
    t.checkExpect(heap.toString, "")

    heap.insert(2)
    t.checkExpect(heap.toString, "(0: Some(2))")

    heap.insert(8)
    heap.insert(1)
    heap.insert(4)
    heap.insert(11)
    t.checkExpect(heap.toString, "(0: Some(1)) (1: Some(4)) (2: Some(2)) (3: Some(8)) (4: Some(11))")

    for (i <- 1 to 5) heap.deleteMin
  }

}

trait testEquals extends AbsHeapTest {

  // test for overridden heap equality
  def testEquals(t: Tester) {
    // heap & heap2 have same set of items
    t.checkExpect(heap == heap2)

    heap.insert(2)
    heap2.insert(2)
    t.checkExpect(heap == heap2)

    heap.insert(7)
    heap2.insert(7)
    heap.insert(1)
    heap2.insert(1)
    t.checkExpect(heap == heap2)

    // clone has more items
    heap2.insert(6)
    t.checkExpect(heap != heap2)

    // heap & clone have same # items, but not same set of items
    heap.insert(50)
    t.checkExpect(heap != heap2)

    // testing equality against things that are not heaps
    t.checkExpect(heap != heap2)

    for (i <- 1 to 4) {
      heap.deleteMin
      heap2.deleteMin
    }
  }

}

trait testIterator extends AbsHeapTest {

  // test for heap iterator
  def testIterator(t: Tester) {
    // iterator over the empty heap
    val iter = heap.iterator
    t.checkExpect(!iter.hasNext)
    t.checkException(new NoSuchElementException("next on empty iterator"), iter, "next")

    // iterator over populated heap
    heap.insert(6)
    heap.insert(7)
    heap.insert(2)
    val iter2 = heap.iterator
    t.checkExpect(iter2.hasNext)
    t.checkExpect(iter2.next, 2)
    t.checkExpect(iter2.hasNext)
    t.checkExpect(iter2.next, 7)
    t.checkExpect(iter2.hasNext)
    t.checkExpect(iter2.next, 6)
    t.checkExpect(!iter2.hasNext)
    t.checkException(new NoSuchElementException("next on empty iterator"), iter2, "next")

    for (i <- 1 to 3) heap.deleteMin
  }
}
