package miniboxing.test.paper

import miniboxing.test.infrastructure.FreezingTest
import miniboxing.paper.HackMap
import org.scalameter.Gen

class HackMapBenchmark extends FreezingTest {

  val random = new scala.util.Random(0)
  
  val hackMap = new HackMap[Int, Double]
  val hashMap = new java.util.HashMap[Int, Double]
  
  val genHack = Gen.single("Empty HackMap")(hackMap)
  val genHash = Gen.single("Empty HashMap")(hashMap)
  
  val rdmHackMap = new HackMap[Int, Double]
  val rdmHashMap = new java.util.HashMap[Int, Double]
  
  
  for (i <- 0 until 1000) {
    rdmHackMap.put(random.nextInt, random.nextDouble)
  }
  for (i <- 0 until 1000) {
    rdmHashMap.put(random.nextInt, random.nextDouble)
  }
  
  val genRdmHack = Gen.single("Randomly Preloaded HackMap")(hackMap)
  val genRdmHash = Gen.single("Randomly Preloaded HashMap")(hashMap)
  
  
  var result = 0.0d
  performance of "Java HashMap" in {
    measure method "put" in {
      using(genHash) in {
        hm =>
          for (i <- 0 until 50000) {
            hm.put(random.nextInt, random.nextDouble)
          }
      }
    }
    
    measure method "get" in {
      using(genHash) in {
        hm =>
          for (i <- 0 until 50000) {
            result += hm.get(random.nextInt)
          }
      }
    }
  }
  performance of "Freezing HackMap" in {
    measure method "put" in {
      using(genHack) in {
        hm =>
          for (i <- 0 until 50000) {
            hm.put(random.nextInt, random.nextDouble)
          }
      }
    }
    
    measure method "get" in {
      using(genHack) in {
        hm =>
          for (i <- 0 until 50000) {
            result += hm.get(random.nextInt)
          }
      }
    }
  }
}