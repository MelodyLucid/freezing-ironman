package miniboxing.test.paper

import miniboxing.test.infrastructure.FreezingTest
import org.scalameter.Gen
import miniboxing.hackmap.HackMap

class HackMapBenchmark extends FreezingTest {

  def rand(seed: Int) = new scala.util.Random(seed)
  
  
  // Empty HashMaps
  
  val genericHackMap = new HackMap.Generic[Int, Double]
  val specializedHackMap = new HackMap.Specialized[Int, Double]
  val miniboxedHackMap = new HackMap.Miniboxed[Int, Double]
  val javaHashMap = new java.util.HashMap[Int, Double]
  
  val genericGen = Gen.single("Empty Generic HackMap")(genericHackMap)
  val specializedGen = Gen.single("Empty Specilaized HackMap")(specializedHackMap)
  val miniboxedGen = Gen.single("Empty Miniboxed HackMap")(miniboxedHackMap)
  val javaGen = Gen.single("Empty Java HashMap")(javaHashMap)
  
  
  // Randomly predefined HashMaps
  
  val rdmGenericHackMap = new HackMap.Generic[Int, Double]
  val rdmSpecializedHackMap = new HackMap.Specialized[Int, Double]
  val rdmMiniboxedHackMap = new HackMap.Miniboxed[Int, Double]
  val rdmJavaHashMap = new java.util.HashMap[Int, Double]
  
  // The following fills the HashMaps with the same random values
  {
    val random = rand(1)
    
    for (i <- 0 until 1000) {
      val k = random.nextInt
      val v = random.nextDouble
      
      rdmGenericHackMap.put(k, v)
      rdmSpecializedHackMap.put(k, v)
      rdmMiniboxedHackMap.put(k, v)
      rdmJavaHashMap.put(k, v)
    }
  }
  
  
  val rdmGenericGen = Gen.single("Empty Generic HackMap")(rdmGenericHackMap)
  val rdmSpecializedGen = Gen.single("Empty Specilaized HackMap")(rdmSpecializedHackMap)
  val rdmMiniboxedGen = Gen.single("Empty Miniboxed HackMap")(rdmMiniboxedHackMap)
  val rdmJavaGen = Gen.single("Empty Java HashMap")(rdmJavaHashMap)
  
  // variable result in order to avoid in-lining
  var result = 0.0d
  performance of "Generic HackMap" in {
    measure method "put" in {
      using(genericGen) in {
        hm =>
          val random = rand(0)
          for (i <- 0 until 500000) {
            result += hm.put(random.nextInt, random.nextDouble)
          }
      }
    }
    
    measure method "get" in {
      using(rdmGenericGen) in {
        hm =>
          val random = rand(0)
          for (i <- 0 until 500000) {
            result += hm.get(random.nextInt)
          }
      }
    }
  }
  
  performance of "Specialized HackMap" in {
    measure method "put" in {
      using(specializedGen) in {
        hm =>
          val random = rand(0)
          for (i <- 0 until 500000) {
            result += hm.put(random.nextInt, random.nextDouble)
          }
      }
    }
    
    measure method "get" in {
      using(rdmSpecializedGen) in {
        hm =>rdmSpecializedHackMap
          val random = rand(0)
          for (i <- 0 until 500000) {
            result += hm.get(random.nextInt)
          }
      }
    }
  }
  
  performance of "Miniboxed HackMap" in {
    measure method "put" in {
      using(miniboxedGen) in {
        hm =>
          val random = rand(0)
          for (i <- 0 until 500000) {
            result += hm.put(random.nextInt, random.nextDouble)
          }
      }
    }
    
    measure method "get" in {
      using(rdmMiniboxedGen) in {
        hm =>
          val random = rand(0)
          for (i <- 0 until 500000) {
            result += hm.get(random.nextInt)
          }
      }
    }
  }
  
  performance of "Java HashMap" in {
    measure method "put" in {
      using(javaGen) in {
        hm =>
          val random = rand(0)
          for (i <- 0 until 500000) {
            result += hm.put(random.nextInt, random.nextDouble)
          }
      }
    }
    
    measure method "get" in {
      using(rdmJavaGen) in {
        hm =>
          val random = rand(0)
          for (i <- 0 until 500000) {
            result += hm.get(random.nextInt)
          }
      }
    }
  }
}