package miniboxing.test.scalatests

import org.scalatest.FunSuite

import miniboxing.example.hashmap.GenericHashMap

/**
 * Checks the well behavior of the GenericHashMap[K,V].
 * 
 */
class GenericHashMapScalaTest extends FunSuite {

  test("Instanciation") {
    val hashMap = new GenericHashMap[Int, String]
  }
  
  test("put(key, value) def") {
    val hashMap = new GenericHashMap[Int, String]
    hashMap.put(3, "3")
  }
  
  test("get(key) def") {
    val hashMap = new GenericHashMap[Int, String]
    hashMap.put(3, "3")
    assert("3".equals(hashMap.get(3)),"HashMap does not record correct elements.")
  }
  
  test("contains(key) def") {
    val hashMap = new GenericHashMap[Int, String]
    hashMap.put(3        , "3")
    hashMap.put(3 + 128  , "131")
    hashMap.put(3 + 128*2, "259")
    assert(hashMap.contains(3),"HashMap does not record correct elements.")
    assert(hashMap.contains(131),"HashMap does not record correct elements.")
    assert(hashMap.contains(259),"HashMap does not record correct elements.")
    assert(!hashMap.contains(6))
  }
  
  test("conflicting get(key) def") {
    val hashMap = new GenericHashMap[Int, String]
    hashMap.put(3        , "3")
    hashMap.put(3 + 128  , "131")
    hashMap.put(3 + 128*2, "259")
    
    val v3   = hashMap.get(3)
    val v131 = hashMap.get(131)
    val v259 = hashMap.get(259)
    
    assert("259".equals(v259),"HashMap has not recorded the element " + v259 + ".")
    assert("131".equals(v131),"HashMap has not recorded the element " + v131 + ".")
    assert(  "3".equals(v3)  ,"HashMap has not recorded the element " + v3 + ".")
  }
  
  test("remove(key) head def") {
    val hashMap = new GenericHashMap[Int, String]
    hashMap.put(3        , "3")
    hashMap.put(3 + 128  , "131")
    hashMap.put(3 + 128*2, "259")
    
    hashMap.remove(3)
    
    val v3   = hashMap.get(3)
    val v131 = hashMap.get(131)
    val v259 = hashMap.get(259)
    
    assert("259".equals(v259),"HashMap has not recorded the element " + v259 + ".")
    assert("131".equals(v131),"HashMap has not recorded the element " + v131 + ".")
    assert(v3 == null,"HashMap has not removed the head element.")
  }
  
  test("remove(key) tail def") {
    val hashMap = new GenericHashMap[Int, String]
    hashMap.put(3        , "3")
    hashMap.put(3 + 128  , "131")
    hashMap.put(3 + 128*2, "259")
    
    hashMap.remove(259)
    
    val v3   = hashMap.get(3)
    val v131 = hashMap.get(131)
    val v259 = hashMap.get(259)
    
    assert(v259 == null,"HashMap has not removed the tail element.")
    assert("131".equals(v131),"HashMap has not recorded the element " + v131 + ".")
    assert(  "3".equals(v3)  ,"HashMap has not recorded the element " + v3 + ".")
  }
  
  test("remove(key) middle def") {
    val hashMap = new GenericHashMap[Int, String]
    hashMap.put(3        , "3")
    hashMap.put(3 + 128  , "131")
    hashMap.put(3 + 128*2, "259")
    
    hashMap.remove(131)
    
    val v3   = hashMap.get(3)
    val v131 = hashMap.get(131)
    val v259 = hashMap.get(259)
    
    assert("259".equals(v259),"HashMap has not recorded the element " + v259 + ".")
    assert(v131 == null,"HashMap has not removed the middle element.")
    assert(  "3".equals(v3)  ,"HashMap has not recorded the element " + v3 + ".")
  }
}