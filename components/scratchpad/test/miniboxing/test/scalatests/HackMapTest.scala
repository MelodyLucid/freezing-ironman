package miniboxing.test.scalatests

import org.scalatest.FunSuite
import miniboxing.hackmap.HackMap

class HackMapTest extends FunSuite {

  test("Generic HackMap Sanity check") {
    val random = new scala.util.Random(0)
    val hm = new HackMap.Generic[Byte, Byte]
    
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
  
  test("Specialized HackMap Sanity check") {
    val random = new scala.util.Random(0)
    val hm = new HackMap.Specialized[Byte, Byte]
    
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
  
  test("Miniboxed HackMap Sanity check") {
    val random = new scala.util.Random(0)
    val hm = new HackMap.Miniboxed[Byte, Byte]
    
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
  
  test("Java HashMap Sanity check") {
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