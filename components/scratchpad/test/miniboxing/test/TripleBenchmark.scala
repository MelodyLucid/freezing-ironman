package miniboxing.test

import org.scalameter.Gen
import org.scalameter.PerformanceTest

import infrastructure.FreezingTest
import miniboxing.example.triple.GenericTriple
import miniboxing.example.triple.MiniboxedTriple
import miniboxing.example.triple.SpecializedTriple
import miniboxing.test.infrastructure.FreezingTest

object TripleBenchmark extends FreezingTest {

  var outsider: Double = 0.0
  val size: Int = 300000

  // Triples
  val mbTriple = new MiniboxedTriple[Int, String, Double](3, "3.5", 3.5)
  val spTriple = new SpecializedTriple[Int, String, Double](3, "3.5", 3.5)
  val gnTriple = new GenericTriple[Int, String, Double](3, "3.5", 3.5)

  assert(mbTriple.getClass.getSimpleName() == "MiniboxedTriple_JLJ")

  val mbTripleGen = Gen.single("Miniboxed Triple")(mbTriple)
  val spTripleGen = Gen.single("Specialized Triple")(spTriple)
  val gnTripleGen = Gen.single("Generic Triple")(gnTriple)

  performance of "Triple" in {
    measure method "getters" in {
      using(gnTripleGen) in {
        t =>
          var i = 0
          var result = 0.0
          while (i < size) {
            result += t.getS
            result += t.getT
            result += t.getR.toDouble
            i += 1
          }

          outsider = result  // avoid in-lining
      }

      using(spTripleGen) in {
        t =>
          var i = 0
          var result = 0.0
          while (i < size) {
            result += t.getS
            result += t.getT
            result += t.getR.toDouble
            i += 1
          }

          outsider = result  // avoid in-lining
      }

      using(mbTripleGen) in {
        t =>
          var i = 0
          var result = 0.0
          while (i < size) {
            result += t.getS
            result += t.getT
            result += t.getR.toDouble
            i += 1
          }

          outsider = result  // avoid in-lining
      }
    }
  }
}
