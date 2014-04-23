package miniboxing.test.scalatests

import org.scalatest.FunSuite
import miniboxing.example.linkedlist.MiniboxedLinkedList

class MiniboxedLinkedListScalaTest extends FunSuite {

  test("Sanity check") {
    val lst = new MiniboxedLinkedList[Int]
    for (i <- 1 to 10) {
      lst.add(i)
    }

    assert(10 == lst.size, "Size = " + lst.size)

    lst.remove(5)
    assert(lst.get(5) == 7, "6th element = " + lst.get(5))

    lst.addAt(100, 0)
    assert(lst.get(0) == 100, "first element = " + lst.get(0))
    lst.addAt(101, 6)
    assert(lst.get(6) == 101, "10th element = " + lst.get(6))
    assert(lst.get(7) == 7, "8th element = " + lst.get(7))
    lst.addAt(102, 0)
    assert(lst.get(0) == 102, "first element = " + lst.get(0))
  }
}