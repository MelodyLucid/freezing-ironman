package miniboxing.test

import org.scalameter.PerformanceTest
import org.scalameter.Gen
import miniboxing.example.triple.SpecializedTriple
import miniboxing.example.triple.GenericTriple
import miniboxing.example.triple.MiniboxTriple

object TripleBenchmark
extends PerformanceTest.Quickbenchmark {
  
  val size: Gen[Int] = Gen.single("size")(300000)

  performance of "Triple" in {
    measure method "SpecializedTriple" in {
      using(size) in {
        s =>
          val specTriple = new SpecializedTriple[Int, String, Double](3, "3.5", 3.5)
          var i = 0
          var result = 0.
          while (i < s) {
            result += specTriple.getS
            result += specTriple.getT
            result += specTriple.getR.toDouble
            i += 1
          }
          println("[Triple] Specialized result: " + result)
      }
    }
    measure method "GenericTriple" in {
      using(size) in {
        s =>
          val genTriple = new GenericTriple[Int, String, Double](3, "3.5", 3.5)
          var i = 0
          var result = 0.
          while (i < s) {
            result += genTriple.getS
            result += genTriple.getT
            result += genTriple.getR.toDouble
            i += 1
          }
          println("[Triple] Generic result: " + result)
      }
    }
    measure method "MiniboxTriple" in {
      using(size) in {
        s =>
          val miniTriple = new MiniboxTriple[Int, String, Double](3, "3.5", 3.5)
          var i = 0
          var result = 0.
          while (i < s) {
            result += miniTriple.getS
            result += miniTriple.getT
            result += miniTriple.getR.toDouble
            i += 1
          }
          println("[Triple] Miniboxed result: " + result)
      }
    }
  }
}