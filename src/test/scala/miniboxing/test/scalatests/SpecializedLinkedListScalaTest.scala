package miniboxing.test.scalatests

import org.scalatest.FunSuite
import miniboxing.example.linkedlist.SpecializedLinkedList

class SpecializedLinkedListScalaTest extends FunSuite {

  test("Sanity check") {
    val lst = new SpecializedLinkedList[Int]
    for (i <- 1 to 10) {
      lst.add(i)
    }

    assert(10 == lst.size, "Size = " + lst.size)

    lst.remove(5)
    assert(lst.get(5) == 7, "6th element = " + lst.get(5))

    lst.add(100, 0)
    assert(lst.get(0) == 100, "first element = " + lst.get(0))
    lst.add(101, 6)
    assert(lst.get(6) == 101, "10th element = " + lst.get(6))
    assert(lst.get(7) == 7, "8th element = " + lst.get(7))
    lst.add(102, 0)
    assert(lst.get(0) == 102, "first element = " + lst.get(0))
  }
}