package miniboxing.test

import org.scalameter.api._
import miniboxing.example.hashmap.MiniboxedHashMap
import miniboxing.example.hashmap.SpecializedHashMap
import miniboxing.example.hashmap.GenericHashMap

object HashMapBenchmark
extends PerformanceTest.Quickbenchmark {
  var outsider = 0.
  val size = 3000000

  // Vectors
  val mbHashMap = new MiniboxedHashMap[String, Double]
  val spHashMap = new SpecializedHashMap[String, Double]
  val gnHashMap = new GenericHashMap[String, Double]
  
  assert(mbHashMap.getClass.getSimpleName() == "MiniboxedHashMap_LJ")
  
  val mbHashMapGen = Gen.single("Miniboxed HashMap")(mbHashMap)
  val spHashMapGen = Gen.single("Specialized HashMap")(spHashMap)
  val gnHashMapGen = Gen.single("Generic HashMap")(gnHashMap)

  performance of "HashMap" in {
    measure method "get" in {
      using(gnHashMapGen) in {
        h =>
          var i = 0
          var result = 0.
          h.put("8", 1.)
          while (i < size) {
            result += (h.get("8") match {
              case Some(d) => d
              case _ => 0.
            })
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(spHashMapGen) in {
        h =>
          var i = 0
          var result = 0.
          h.put("8", 1.)
          while (i < size) {
            result += (h.get("8") match {
              case Some(d) => d
              case _ => 0.
            })
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(mbHashMapGen) in {
        h =>
          var i = 0
          var result = 0.
          h.put("8", 1.)
          while (i < size) {
            result += (h.get("8") match {
              case Some(d) => d
              case _ => 0.
            })
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
    }
  }
}