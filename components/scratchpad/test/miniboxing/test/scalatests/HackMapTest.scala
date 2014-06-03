package miniboxing.test.scalatests

import org.scalatest.FunSuite
import miniboxing.paper.HackMap

class HackMapTest extends FunSuite {

  test("HackMap Sanity check") {
    val random = new scala.util.Random(0)
    val hm = new HackMap[Byte, Byte]
    
    for (i <- 0 until 1000) {
      hm.put(random.nextInt.toByte, random.nextInt.toByte)
    }
    
    var result = 0.0d
    for (i <- 0 until 1000) {
      result += hm.get(random.nextInt.toByte)
    }
    
    println(hm.size)
    println(result)
  }
  
  test("HashMap Sanity check") {
    val random = new scala.util.Random(0)
    val hm = new java.util.HashMap[Byte, Byte]
    
    for (i <- 0 until 1000) {
      hm.put(random.nextInt.toByte, random.nextInt.toByte)
    }
    
    var result = 0.0d
    for (i <- 0 until 1000) {
      result += hm.get(random.nextInt.toByte)
    }
    
    println(hm.size())
    println(result)
  }
}