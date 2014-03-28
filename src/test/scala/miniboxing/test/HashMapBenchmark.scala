package miniboxing.test

import org.scalameter.api._
import miniboxing.example.hashmap.MiniboxedHashMap
import miniboxing.example.hashmap.SpecializedHashMap
import miniboxing.example.hashmap.GenericHashMap
import miniboxing.example.hashmap.MiniboxedSome

object HashMapBenchmark
extends PerformanceTest.Quickbenchmark {
  var outsider = 0.
  val size = 30000

  // HashMaps [Int, Int]
  val mbHashMap_II = new MiniboxedHashMap[Int, Int]
  val spHashMap_II = new SpecializedHashMap[Int, Int]
  val gnHashMap_II = new GenericHashMap[Int, Int]
  
  // HashMaps [Int, String]
  val mbHashMap_IS = new MiniboxedHashMap[Int, String]
  val spHashMap_IS = new SpecializedHashMap[Int, String]
  val gnHashMap_IS = new GenericHashMap[Int, String]
  
  // HashMaps [String, Int]
  val mbHashMap_SI = new MiniboxedHashMap[String, Int]
  val spHashMap_SI = new SpecializedHashMap[String, Int]
  val gnHashMap_SI = new GenericHashMap[String, Int]
  
  // HashMaps [String, String]
  val mbHashMap_SS = new MiniboxedHashMap[String, String]
  val spHashMap_SS = new SpecializedHashMap[String, String]
  val gnHashMap_SS = new GenericHashMap[String, String]
  
  
  // assertions
  assert(mbHashMap_II.getClass.getSimpleName() == "MiniboxedHashMap_JJ")
  assert(mbHashMap_IS.getClass.getSimpleName() == "MiniboxedHashMap_JL")
  assert(mbHashMap_SI.getClass.getSimpleName() == "MiniboxedHashMap_LJ")
  assert(mbHashMap_SS.getClass.getSimpleName() == "MiniboxedHashMap_LL")
  
  
  // Gen [Int, Int]
  val mbHashMapGen_II = Gen.single("Miniboxed HashMap[Int, Int]")(mbHashMap_II)
  val spHashMapGen_II = Gen.single("Specialized HashMap[Int, Int]")(spHashMap_II)
  val gnHashMapGen_II = Gen.single("Generic HashMap[Int, Int]")(gnHashMap_II)
  
  // Gen [Int, String]
  val mbHashMapGen_IS = Gen.single("Miniboxed HashMap[Int, String]")(mbHashMap_IS)
  val spHashMapGen_IS = Gen.single("Specialized HashMap[Int, String]")(spHashMap_IS)
  val gnHashMapGen_IS = Gen.single("Generic HashMap[Int, String]")(gnHashMap_IS)
  
  // Gen [String, Int]
  val mbHashMapGen_SI = Gen.single("Miniboxed HashMap[String, Int]")(mbHashMap_SI)
  val spHashMapGen_SI = Gen.single("Specialized HashMap[String, Int]")(spHashMap_SI)
  val gnHashMapGen_SI = Gen.single("Generic HashMap[String, Int]")(gnHashMap_SI)
  
  // Gen [String, String]
  val mbHashMapGen_SS = Gen.single("Miniboxed HashMap[String, String]")(mbHashMap_SS)
  val spHashMapGen_SS = Gen.single("Specialized HashMap[String, String]")(spHashMap_SS)
  val gnHashMapGen_SS = Gen.single("Generic HashMap[String, String]")(gnHashMap_SS)
  
  
  performance of "HashMap [Int, Int]" in {
    measure method "get" in {
      using(gnHashMapGen_II) in {
        h =>
          var i = 0
          var result = 0.
          h.put(8, 1)
          while (i < size) {
            result += h.get(8)
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(spHashMapGen_II) in {
        h =>
          var i = 0
          var result = 0.
          h.put(8, 1)
          while (i < size) {
            result += h.get(8)
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(mbHashMapGen_II) in {
        h =>
          var i = 0
          var result = 0.
          h.put(8, 1)
          while (i < size) {
            result += h.get(8)
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
    }
    
    measure method "put" in {
      using(gnHashMapGen_II) in {
        h =>
          var i = 0
          var result = 0.
          while (i < size) {
            result += h.put(8, 1)
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(spHashMapGen_II) in {
        h =>
          var i = 0
          var result = 0.
          while (i < size) {
            result += h.put(8, 1)
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(mbHashMapGen_II) in {
        h =>
          var i = 0
          var result = 0.
          while (i < size) {
            result += h.put(8, 1)
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
    }
  }
  
  performance of "HashMap [Int, String]" in {
    measure method "get" in {
      using(gnHashMapGen_IS) in {
        h =>
          var i = 0
          var result = 0.
          h.put(8, "1")
          while (i < size) {
            result += h.get(8).toDouble
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(spHashMapGen_IS) in {
        h =>
          var i = 0
          var result = 0.
          h.put(8, "1")
          while (i < size) {
            result += h.get(8).toDouble
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(mbHashMapGen_IS) in {
        h =>
          var i = 0
          var result = 0.
          h.put(8, "1")
          while (i < size) {
            result += h.get(8).toDouble
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
    }
    
    measure method "put" in {
      using(gnHashMapGen_IS) in {
        h =>
          var i = 0
          var result = 0.
          while (i < size) {
            result += h.put(8, "1").toDouble
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(spHashMapGen_IS) in {
        h =>
          var i = 0
          var result = 0.
          while (i < size) {
            result += h.put(8, "1").toDouble
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(mbHashMapGen_IS) in {
        h =>
          var i = 0
          var result = 0.
          while (i < size) {
            result += h.put(8, "1").toDouble
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
    }
  }
  
  performance of "HashMap [String, Int]" in {
    measure method "get" in {
      using(gnHashMapGen_SI) in {
        h =>
          var i = 0
          var result = 0.
          h.put("8", 1)
          while (i < size) {
            result += h.get("8")
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(spHashMapGen_SI) in {
        h =>
          var i = 0
          var result = 0.
          h.put("8", 1)
          while (i < size) {
            result += h.get("8")
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(mbHashMapGen_SI) in {
        h =>
          var i = 0
          var result = 0.
          h.put("8", 1)
          while (i < size) {
            result += h.get("8")
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
    }
    
    measure method "put" in {
      using(gnHashMapGen_SI) in {
        h =>
          var i = 0
          var result = 0.
          while (i < size) {
            result += h.put("8", 1)
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(spHashMapGen_SI) in {
        h =>
          var i = 0
          var result = 0.
          while (i < size) {
            result += h.put("8", 1)
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(mbHashMapGen_SI) in {
        h =>
          var i = 0
          var result = 0.
          while (i < size) {
            result += h.put("8", 1)
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
    }
  }
  
  performance of "HashMap [String, String]" in {
    measure method "get" in {
      using(gnHashMapGen_SS) in {
        h =>
          var i = 0
          var result = 0.
          h.put("8", "1")
          while (i < size) {
            result += h.get("8").toDouble
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(spHashMapGen_SS) in {
        h =>
          var i = 0
          var result = 0.
          h.put("8", "1")
          while (i < size) {
            result += h.get("8").toDouble
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(mbHashMapGen_SS) in {
        h =>
          var i = 0
          var result = 0.
          h.put("8", "1")
          while (i < size) {
            result += h.get("8").toDouble
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
    }
    
    measure method "put" in {
      using(gnHashMapGen_SS) in {
        h =>
          var i = 0
          var result = 0.
          while (i < size) {
            result += h.put("8", "1").toDouble
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(spHashMapGen_SS) in {
        h =>
          var i = 0
          var result = 0.
          while (i < size) {
            result += h.put("8", "1").toDouble
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      
      using(mbHashMapGen_SS) in {
        h =>
          var i = 0
          var result = 0.
          while (i < size) {
            result += h.put("8", "1").toDouble
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
    }
  }
}