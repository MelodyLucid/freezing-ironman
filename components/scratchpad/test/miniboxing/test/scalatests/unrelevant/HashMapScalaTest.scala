package miniboxing.test.scalatests.unrelevant

import org.scalatest.FunSuite

/**
 * Checks the behavior of the actual Scala {@link HashMap}.
 * 
 */
class HashMapScalaTest extends FunSuite {

  test("Immutable HashMap: map function") {
    def rec0(hashmap: scala.collection.immutable.HashMap[Int,String], i: Int): scala.collection.immutable.HashMap[Int,String] =
      if (i == 10) hashmap else rec0(hashmap.updated(i, "" + i), i+1)
    
    val hashMap = rec0(new scala.collection.immutable.HashMap[Int,String],1)

    val mappedHashMap = hashMap.map(pair => (pair._1 * 10, "new " + pair._2))

    assert(mappedHashMap.forall(pair => ("new " + pair._1/10).equals(pair._2)))
    assert(mappedHashMap.get(40) match {
      case Some(str) => "new 4".equals(str)
      case None => false
    })
  }

  test("Mutable HashMap: map function") {
    val hashMap = new scala.collection.mutable.HashMap[Int,String]
    for (i <- (0 until 10)) {
      hashMap.put(i, i.toString)
    }

    hashMap.remove(7)

    val mappedHashMap = hashMap.map(pair => (pair._1 * 10, "new " + pair._2))

    assert(mappedHashMap.forall(pair => ("new " + pair._1/10).equals(pair._2)))
    assert(mappedHashMap.get(40) match {
      case Some(str) => "new 4".equals(str)
      case None => false
    })
  }
}