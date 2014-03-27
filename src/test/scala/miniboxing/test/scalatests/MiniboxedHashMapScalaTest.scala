package miniboxing.test.scalatests

import org.scalatest.FunSuite

import miniboxing.example.hashmap.MiniboxedHashMap

/**
 * Checks the well behavior of the MiniboxedHashMap.
 * 
 */
class MiniboxedHashMapScalaTest extends FunSuite {

  test("Instanciation") {
    val hashMap = new MiniboxedHashMap[Int, String]
  }
  
  test("put(key, value) def") {
    val hashMap = new MiniboxedHashMap[Int, String]
    hashMap.put(3, "3")
  }
  
  test("get(key) def") {
    val hashMap = new MiniboxedHashMap[Int, String]
    hashMap.put(3, "3")
    assert("3".equals(hashMap.get(3)),"HashMap does not record correct elements.")
  }
  
  test("conflicting get(key) def") {
    val hashMap = new MiniboxedHashMap[Int, String]
    hashMap.put(3, "3")
    hashMap.put(3 + 32, "35")
    hashMap.put(3 + 32*2, "67")
    
    assert("67".equals(hashMap.get(67)),"HashMap does not record the previous elements.")
    
    assert("35".equals(hashMap.get(35)),"HashMap does not record the previous elements.")
    
    assert("3".equals(hashMap.get(3)),"HashMap does not record the previous elements.")
  }
  
  test("remove(key) head def") {
    val hashMap = new MiniboxedHashMap[Int, String]
    hashMap.put(3, "3")

    hashMap.remove(3)

    assert(hashMap.get(3) == null,"HashMap does not remove elements properly.")
  }
  
  test("remove(key) tail def") {
    val hashMap = new MiniboxedHashMap[Int, String]
    hashMap.put(3, "3")
    hashMap.put(3 + 32, "35")
    hashMap.put(3 + 32*2, "67")

    hashMap.remove(67)

    assert(hashMap.get(67) == null,"HashMap has not removed the element.")
    
    assert("35".equals(hashMap.get(35)),"HashMap does not record the previous elements.")
    
    assert("3".equals(hashMap.get(3)),"HashMap does not record the previous elements.")
  }
  
  test("remove(key) middle def") {
    val hashMap = new MiniboxedHashMap[Int, String]
    hashMap.put(3, "3")
    hashMap.put(3 + 32, "35")
    hashMap.put(3 + 32*2, "67")

    hashMap.remove(35)

    assert("67".equals(hashMap.get(67)),"HashMap does not record the previous elements.")
    
    assert(hashMap.get(35) == null,"HashMap has not removed the element.")
    
    assert("3".equals(hashMap.get(3)),"HashMap does not record the previous elements.")
  }
}