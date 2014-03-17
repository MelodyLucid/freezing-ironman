package miniboxing.test

import org.scalameter.api._
import miniboxing.example.box.MiniBox
import miniboxing.example.box.GenericBox
import miniboxing.example.box.SpecializedBox
import miniboxing.example.box.MutableMiniBox
import miniboxing.example.box.MutableGenericBox
import miniboxing.example.box.MutableSpecializedBox

object BoxBenchmark
extends PerformanceTest.Quickbenchmark {

  // Immutable boxes
  val specBox: SpecializedBox[Int] = new SpecializedBox[Int](1)
  val genericBox: GenericBox[Int] = new GenericBox[Int](1)
  val miniBox: MiniBox[Int] = new MiniBox[Int](1)
  
  val genericBoxGen: Gen[GenericBox[Int]] = Gen.single("genericBox")(genericBox)
  val specBoxGen: Gen[SpecializedBox[Int]] = Gen.single("specBox")(specBox)
  val miniBoxGen: Gen[MiniBox[Int]] = Gen.single("miniBox")(miniBox)
  
  // Mutable boxes
  val mutSpecBox: MutableSpecializedBox[Int] = new MutableSpecializedBox[Int](1)
  val mutGenericBox: MutableGenericBox[Int] = new MutableGenericBox[Int](1)
  val mutMiniBox: MutableMiniBox[Int] = new MutableMiniBox[Int](1)
  
  val mutGenericBoxGen: Gen[MutableGenericBox[Int]] = Gen.single("mutGenericBox")(mutGenericBox)
  val mutSpecBoxGen: Gen[MutableSpecializedBox[Int]] = Gen.single("mutSpecBox")(mutSpecBox)
  val mutMiniBoxGen: Gen[MutableMiniBox[Int]] = Gen.single("mutMiniBox")(mutMiniBox)

  performance of "ImmutableBoxes" in {
    measure method "retrieve" in {
      using(genericBoxGen) in {
        b => 
          var i = 0
          var result = 0
          while (i < 30000000) {
            result += b.retrieve
            i += 1
          }
          println("[Immutable Boxes] Generic result: " + result)
      }
      using(specBoxGen) in {
        b => 
          var i = 0
          var result = 0
          while (i < 30000000) {
            result += b.retrieve
            i += 1
          }
          println("[Immutable Boxes] Specialized result: " + result)
      }
      using(miniBoxGen) in {
        b =>
          var i = 0
          var result = 0
          while (i < 30000000) {
            result += b.retrieve
            i += 1
          }
          println("[Immutable Boxes] Miniboxed result: " + result)
      }
    }
  }
  
  performance of "MutableBoxes" in {
    measure method "add" in {
      using(mutGenericBoxGen) in {
        b => 
          var i = 0
          var result = 0
          while (i < 30000000) {
            result += b.add(1)
            i += 1
          }
          println("[Mutable Boxes] Generic result: " + result)
      }
      using(mutSpecBoxGen) in {
        b => 
          var i = 0
          var result = 0
          while (i < 30000000) {
            result += b.add(1)
            i += 1
          }
          println("[Mutable Boxes] Specialized result: " + result)
      }
      using(mutMiniBoxGen) in {
        b => 
          var i = 0
          var result = 0
          while (i < 30000000) {
            result += b.add(1)
            i += 1
          }
          println("[Mutable Boxes] Miniboxed result: " + result)
      }
    }
  }
}