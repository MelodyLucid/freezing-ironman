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

  var outsider: Double = 0.
  val size: Int = 30000000

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

  performance of "ImmutableBox" in {
    measure method "retrieve" in {
      using(genericBoxGen) in {
        b => 
          var i = 0
          var result = 0
          while (i < size) {
            result += b.retrieve
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      using(specBoxGen) in {
        b => 
          var i = 0
          var result = 0
          while (i < size) {
            result += b.retrieve
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
      using(miniBoxGen) in {
        b =>
          var i = 0
          var result = 0
          while (i < size) {
            result += b.retrieve
            i += 1
          }
          
          outsider = result  // avoid in-lining
      }
    }
  }
  
  performance of "MutableBox" in {
    measure method "add" in {
      using(mutGenericBoxGen) in {
        b => 
          var i = 0
          var result = 0
          while (i < size) {
            result += b.add(1)
            i += 1
          }
          
          outsider = result	// avoid in-lining
      }
      using(mutSpecBoxGen) in {
        b => 
          var i = 0
          var result = 0
          while (i < size) {
            result += b.add(1)
            i += 1
          }
          
          outsider = result	// avoid in-lining
      }
      using(mutMiniBoxGen) in {
        b => 
          var i = 0
          var result = 0
          while (i < size) {
            result += b.add(1)
            i += 1
          }
          
          outsider = result	// avoid in-lining
      }
    }
  }
}