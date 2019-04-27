package hw06.sol

import hw06.src.AbsHeapTest

import tester.Tester
import hw06.src.testToString
import hw06.src.testEquals
import hw06.src.testIterator

trait testDeleteMin extends AbsHeapTest {

  // test for heap iterator
  def testDeleteMin(t: Tester) {

    t.checkExpect(heap.deleteMin, None)
    heap.insert(-1)
    t.checkExpect(heap.deleteMin.get, -1)
    t.checkExpect(heap.deleteMin, None)

    heap.insert(6)
    heap.insert(7)
    heap.insert(2)
    t.checkExpect(heap.iterator.next, 2)
    t.checkExpect(heap.deleteMin.get, 2)
    t.checkExpect(heap.iterator.next, 6)
    t.checkExpect(heap.deleteMin.get, 6)
    t.checkExpect(heap.iterator.next, 7)
    t.checkExpect(heap.deleteMin.get, 7)
    t.checkExpect(heap.deleteMin, None)

    heap.insert(6)
    heap.insert(7)
    heap.insert(2)
    heap.insert(5)
    heap.insert(-1)
    heap.insert(8)

    t.checkExpect(heap.iterator.next, -1)
    t.checkExpect(heap.deleteMin.get, -1)
    t.checkExpect(heap.iterator.next, 2)
    t.checkExpect(heap.deleteMin.get, 2)
    t.checkExpect(heap.iterator.next, 5)
    t.checkExpect(heap.deleteMin.get, 5)
    t.checkExpect(heap.iterator.next, 6)
    t.checkExpect(heap.deleteMin.get, 6)
    t.checkExpect(heap.iterator.next, 7)
    t.checkExpect(heap.deleteMin.get, 7)
    t.checkExpect(heap.iterator.next, 8)
    t.checkExpect(heap.deleteMin.get, 8)
    t.checkExpect(heap.deleteMin, None)

    heap.insert(2)
    heap.insert(6)
    heap.insert(7)
    heap.insert(2)
    heap.insert(8)
    heap.insert(2)

    t.checkExpect(heap.iterator.next, 2)
    t.checkExpect(heap.deleteMin.get, 2)
    t.checkExpect(heap.iterator.next, 2)
    t.checkExpect(heap.deleteMin.get, 2)
    t.checkExpect(heap.iterator.next, 2)
    t.checkExpect(heap.deleteMin.get, 2)
    t.checkExpect(heap.iterator.next, 6)
    t.checkExpect(heap.deleteMin.get, 6)
    t.checkExpect(heap.iterator.next, 7)
    t.checkExpect(heap.deleteMin.get, 7)
    t.checkExpect(heap.iterator.next, 8)
    t.checkExpect(heap.deleteMin.get, 8)
    t.checkExpect(heap.deleteMin, None)
  }
}

trait testGetMin extends AbsHeapTest {

  // test for heap iterator
  def testGetMin(t: Tester) {

    t.checkExpect(heap.getMin, None)
    t.checkExpect(heap.getMin, None)
    heap.insert(-1)
    t.checkExpect(heap.getMin.get, -1)
    t.checkExpect(heap.getMin.get, -1)
    t.checkExpect(heap.deleteMin.get, -1)
    t.checkExpect(heap.deleteMin, None)

    heap.insert(6)
    heap.insert(7)
    heap.insert(2)
    t.checkExpect(heap.iterator.next, 2)
    t.checkExpect(heap.getMin.get, 2)
    t.checkExpect(heap.deleteMin.get, 2)
    t.checkExpect(heap.iterator.next, 6)
    t.checkExpect(heap.getMin.get, 6)
    t.checkExpect(heap.deleteMin.get, 6)
    t.checkExpect(heap.iterator.next, 7)
    t.checkExpect(heap.getMin.get, 7)
    t.checkExpect(heap.deleteMin.get, 7)
    t.checkExpect(heap.deleteMin, None)

    heap.insert(6)
    heap.insert(7)
    heap.insert(2)
    heap.insert(5)
    heap.insert(-1)
    heap.insert(8)

    t.checkExpect(heap.iterator.next, -1)
    t.checkExpect(heap.getMin.get, -1)
    t.checkExpect(heap.deleteMin.get, -1)
    t.checkExpect(heap.iterator.next, 2)
    t.checkExpect(heap.getMin.get, 2)
    t.checkExpect(heap.deleteMin.get, 2)
    t.checkExpect(heap.iterator.next, 5)
    t.checkExpect(heap.getMin.get, 5)
    t.checkExpect(heap.deleteMin.get, 5)
    t.checkExpect(heap.iterator.next, 6)
    t.checkExpect(heap.getMin.get, 6)
    t.checkExpect(heap.deleteMin.get, 6)
    t.checkExpect(heap.iterator.next, 7)
    t.checkExpect(heap.getMin.get, 7)
    t.checkExpect(heap.deleteMin.get, 7)
    t.checkExpect(heap.iterator.next, 8)
    t.checkExpect(heap.getMin.get, 8)
    t.checkExpect(heap.deleteMin.get, 8)
    t.checkExpect(heap.deleteMin, None)

    heap.insert(2)
    heap.insert(6)
    heap.insert(7)
    heap.insert(2)
    heap.insert(8)
    heap.insert(2)

    t.checkExpect(heap.iterator.next, 2)
    t.checkExpect(heap.getMin.get, 2)
    t.checkExpect(heap.deleteMin.get, 2)
    t.checkExpect(heap.iterator.next, 2)
    t.checkExpect(heap.getMin.get, 2)
    t.checkExpect(heap.deleteMin.get, 2)
    t.checkExpect(heap.iterator.next, 2)
    t.checkExpect(heap.getMin.get, 2)
    t.checkExpect(heap.deleteMin.get, 2)
    t.checkExpect(heap.iterator.next, 6)
    t.checkExpect(heap.getMin.get, 6)
    t.checkExpect(heap.deleteMin.get, 6)
    t.checkExpect(heap.iterator.next, 7)
    t.checkExpect(heap.getMin.get, 7)
    t.checkExpect(heap.deleteMin.get, 7)
    t.checkExpect(heap.iterator.next, 8)
    t.checkExpect(heap.getMin.get, 8)
    t.checkExpect(heap.deleteMin.get, 8)
    t.checkExpect(heap.deleteMin, None)
  }
}

object Main extends App {
  Tester.run(new AbsHeapTest(new Heap[Int](3), new Heap[Int](3)) with testDeleteMin)
  Tester.run(new AbsHeapTest(new Heap[Int](3), new Heap[Int](3)) with testGetMin)
  Tester.run(new AbsHeapTest(new Heap[Int](3), new Heap[Int](3)) with testToString)
  Tester.run(new AbsHeapTest(new Heap[Int](3), new Heap[Int](3)) with testEquals)
  Tester.run(new AbsHeapTest(new Heap[Int](3), new Heap[Int](3)) with testIterator)

  Tester.run(new AbsHeapTest(new Heap[Int](10), new Heap[Int](10)) with testDeleteMin)
  Tester.run(new AbsHeapTest(new Heap[Int](10), new Heap[Int](10)) with testGetMin)
  Tester.run(new AbsHeapTest(new Heap[Int](10), new Heap[Int](10)) with testToString)
  Tester.run(new AbsHeapTest(new Heap[Int](10), new Heap[Int](10)) with testEquals)
  Tester.run(new AbsHeapTest(new Heap[Int](10), new Heap[Int](10)) with testIterator)
}