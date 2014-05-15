package miniboxing.test

import org.scalameter.api._
import miniboxing.example.box.MiniBox
import miniboxing.example.box.GenericBox
import miniboxing.example.box.SpecializedBox
import miniboxing.example.box.MutableMiniBox
import miniboxing.example.box.MutableGenericBox
import miniboxing.example.box.MutableSpecializedBox
import miniboxing.test.infrastructure.FreezingTest

object BoxBenchmark extends FreezingTest {

  var outsider: Double = 0.0
  val size: Int = 30000000

  // Immutable boxes
  val specBox = new SpecializedBox[Int](1)
  val genericBox = new GenericBox[Int](1)
  val miniBox = new MiniBox[Int](1)

  assert(miniBox.getClass.getSimpleName() == "MiniBox_J")

  val genericBoxGen = Gen.single("Generic Box")(genericBox)
  val specBoxGen = Gen.single("Specialized Box")(specBox)
  val miniBoxGen = Gen.single("Miniboxed Box")(miniBox)

  // Mutable boxes
  val mutSpecBox = new MutableSpecializedBox[Int](1)
  val mutGenericBox = new MutableGenericBox[Int](1)
  val mutMiniBox = new MutableMiniBox[Int](1)

  assert(mutMiniBox.getClass.getSimpleName() == "MutableMiniBox_J")

  val mutGenericBoxGen = Gen.single("Mutable Generic Box")(mutGenericBox)
  val mutSpecBoxGen = Gen.single("Mutable Specialized Box")(mutSpecBox)
  val mutMiniBoxGen = Gen.single("Mutable Miniboxed Box")(mutMiniBox)

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
