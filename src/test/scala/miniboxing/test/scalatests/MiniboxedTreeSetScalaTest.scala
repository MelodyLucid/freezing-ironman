package miniboxing.test.scalatests

import org.scalatest.FunSuite
import miniboxing.example.treeset.MiniboxedTreeSet

class MiniboxedTreeSetScalaTest extends FunSuite {

  test("10 elements") {
    val set: MiniboxedTreeSet[Int] = new MiniboxedTreeSet[Int]
    
    set.add(5)
    set.add(7)
    set.add(2)
    set.add(1)
    set.add(10)
    set.add(8)
    set.add(5)
    set.add(3)
    set.add(9)
    set.add(4)
    set.add(6)
    
    assert(set.size == 10)
    for (i <- (1 to 10)) {
      val b = set.contains(i);
      assert(b, "Set does not contains " + i)
    }
    
    assert(!set.contains(11), "Set does contain 11")
  }
  
  test("collisions") {
    val set: MiniboxedTreeSet[Int] = new MiniboxedTreeSet[Int]
    
    set.add(5)
    set.add(7)
    set.add(2)
    set.add(1)
    set.add(10)
    set.add(8)
    set.add(5)
    set.add(3)
    set.add(9)
    set.add(4)
    set.add(6)
    
    set.add(6)
    set.add(5)
    set.add(6)
    set.add(4)
    set.add(6)
    set.add(5)
    
    assert(set.size == 10)
    for (i <- (1 to 10)) {
      val b = set.contains(i);
      assert(b, "Set does not contains " + i)
    }
    assert(!set.contains(11), "Set does contain 11")
  }
  
  
}