package miniboxing.test

import org.scalameter.api._

// we don't need this guy wasting our time with each full test:
//
//object RangeBenchmark extends FreezingTest {
//  val sizes: Gen[Int] = Gen.range("size")(300000, 1500000, 300000)
//
//  val ranges: Gen[Range] = for {
//    size <- sizes
//  } yield 0 until size
//
//  performance of "Range" in {
//    measure method "map" in {
//      using(ranges) in {
//        r => r.map(_ + 1)
//      }
//    }
//  }
//}
