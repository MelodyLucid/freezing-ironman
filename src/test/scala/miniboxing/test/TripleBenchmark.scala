package miniboxing.test

import org.scalameter.PerformanceTest
import org.scalameter.Gen
import miniboxing.example.triple.SpecializedTriple
import miniboxing.example.triple.GenericTriple
import miniboxing.example.triple.MiniboxTriple

object TripleBenchmark
extends PerformanceTest.Quickbenchmark {
  
  val size: Gen[Int] = Gen.single("size")(30000)

  performance of "Triple" in {
    measure method "SpecializedTriple" in {
      using(size) in {
        s => (0 until s).map(_ => new SpecializedTriple[Int, String, Double](3, "3.1", 3.1).getS + 1)
      }
    }
    measure method "GenericTriple" in {
      using(size) in {
        s => (0 until s).map(_ => new GenericTriple[Int, String, Double](3, "3.1", 3.1).getS + 1)
      }
    }
    measure method "MiniboxTriple" in {
      using(size) in {
        s => (0 until s).map(_ => new MiniboxTriple[Int, String, Double](3, "3.1", 3.1).getS + 1)
      }
    }
  }
}