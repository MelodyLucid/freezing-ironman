package miniboxing.test

import org.scalameter.api._
import miniboxing.example.MiniBox
import miniboxing.example.SpecializedBox
import miniboxing.example.GenericBox
import miniboxing.example.MutableSpecializedBox
import miniboxing.example.MutableMiniBox
import miniboxing.example.MutableGenericBox

object BoxBenchmark
extends PerformanceTest.Quickbenchmark {

  // Immutable boxes
  val specBox: SpecializedBox[Int] = new SpecializedBox[Int](0)
  val genericBox: GenericBox[Int] = new GenericBox[Int](1)
  val miniBox: MiniBox[Int] = new MiniBox[Int](2)
  
  val genericBoxGen: Gen[GenericBox[Int]] = Gen.single("genericBox")(genericBox)
  val specBoxGen: Gen[SpecializedBox[Int]] = Gen.single("specBox")(specBox)
  val miniBoxGen: Gen[MiniBox[Int]] = Gen.single("miniBox")(miniBox)
  
  // Mutable boxes
  val mutSpecBox: MutableSpecializedBox[Int] = new MutableSpecializedBox[Int](0)
  val mutGenericBox: MutableGenericBox[Int] = new MutableGenericBox[Int](1)
  val mutMiniBox: MutableMiniBox[Int] = new MutableMiniBox[Int](2)
  
  val mutGenericBoxGen: Gen[MutableGenericBox[Int]] = Gen.single("mutGenericBox")(mutGenericBox)
  val mutSpecBoxGen: Gen[MutableSpecializedBox[Int]] = Gen.single("mutSpecBox")(mutSpecBox)
  val mutMiniBoxGen: Gen[MutableMiniBox[Int]] = Gen.single("mutMiniBox")(mutMiniBox)

  performance of "ImmutableBoxes" in {
    measure method "retrieve" in {
      using(genericBoxGen) in {
        b => b.retrieve + 1
      }
      using(specBoxGen) in {
        b => b.retrieve + 1
      }
      using(miniBoxGen) in {
        b => b.retrieve + 1
      }
    }
  }
  
  performance of "MutableBoxes" in {
    measure method "retrieve" in {
      using(mutGenericBoxGen) in {
        b => b.add(1) + 1
      }
      using(mutSpecBoxGen) in {
        b => b.add(1) + 1
      }
      using(mutMiniBoxGen) in {
        b => b.add(1) + 1
      }
    }
  }
}