package miniboxing.test.paper

import miniboxing.test.infrastructure.FreezingTest
import miniboxing.hackmap._
import org.scalameter.Gen

class HackMapBenchmark extends FreezingTest {

  def rand(seed: Int) = new scala.util.Random(seed)

  var hackMap: HackMapGeneric[Int, Double] = null
  var hashMap: stolen.HashMap[Int, Double] = null

  val size =  Gen.single("size")(500000)

  var result = 0.0d
  performance of "HashMaps" in {
    performance of "Freezing HackMap" config (testSettings: _*) in {
      measure method "put" in {
        using(size) setUp {
          size =>
            System.gc()
            hackMap = new HackMapGeneric[Int, Double]
        } in {
          size =>
            val random = rand(0)
            for (i <- 0 until size) {
              hackMap.put(random.nextInt, random.nextDouble)
            }
            hackMap = null
        }
      }

      measure method "get" in {
        using(size) setUp {
          size =>
            System.gc()
            hackMap = new HackMapGeneric[Int, Double]
            val random = rand(1)
            for (i <- 0 until size/2) {
              hackMap.put(random.nextInt, random.nextDouble)
            }
        } in {
          size =>
            val random = rand(0)
            for (i <- 0 until size) {
              result += hackMap.get(random.nextInt)
            }
            hackMap = null
        }
      }
    }
    performance of "Java HashMap" in {
      measure method "put" in {
        using(size) setUp {
          size =>
            System.gc()
            hashMap = new stolen.HashMap[Int, Double]
        } in {
          size =>
            val random = rand(0)
            for (i <- 0 until size) {
              hashMap.put(random.nextInt, random.nextDouble)
            }
            hashMap = null
        }
      }

      measure method "get" in {
        using(size) setUp {
          size =>
            System.gc()
            hashMap = new stolen.HashMap[Int, Double]
            val random = rand(1)
            for (i <- 0 until size/2) {
              hashMap.put(random.nextInt, random.nextDouble)
            }
        } in {
          size =>
            val random = rand(0)
            for (i <- 0 until size) {
              result += hashMap.get(random.nextInt)
            }
            hashMap = null
        }
      }
    }
  }
}
