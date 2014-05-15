package miniboxing.test

import org.scalameter.Gen
import miniboxing.example.linkedlist.MiniboxedLinkedList
import miniboxing.example.linkedlist.SpecializedLinkedList
import miniboxing.example.linkedlist.GenericLinkedList
import miniboxing.test.infrastructure.FreezingTest

object LinkedListBenchmark extends FreezingTest {
  var outsider = 0.0
  val size = 3000000

  val mbLinkedList = new MiniboxedLinkedList[Int]
  val spLinkedList = new SpecializedLinkedList[Int]
  val gnLinkedList=  new GenericLinkedList[Int]
  
  // assertions
  assert(mbLinkedList.getClass.getSimpleName() == "MiniboxedLinkedList_J")

  // Gen
  val mbLinkedListGen = Gen.single("Miniboxed LinkedList")(mbLinkedList)
  val spLinkedListGen = Gen.single("Specialized LinkedList")(spLinkedList)
  val gnLinkedListGen = Gen.single("Generic LinkedList")(gnLinkedList)

  performance of "LinkedList [Int]" in {
    measure method "get" in {
      using(gnLinkedListGen) in {
        v => 
          var i = 0
          var result = 0.0
          v.add(1)
          v.add(2)
          v.add(3)
          while (i < size) {
            result += v.get(2)
            i += 1
          }

          outsider = result  // avoid in-lining
      }
      using(spLinkedListGen) in {
        v => 
          var i = 0
          var result = 0.0
          v.add(1)
          v.add(2)
          v.add(3)
          while (i < size) {
            result += v.get(2)
            i += 1
          }

          outsider = result  // avoid in-lining
      }
      using(mbLinkedListGen) in {
        v => 
          var i = 0
          var result = 0.0
          v.add(1)
          v.add(2)
          v.add(3)
          while (i < size) {
            result += v.get(2)
            i += 1
          }

          outsider = result  // avoid in-lining
      }
    }
  }
}
