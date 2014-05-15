package miniboxing.test

import org.scalameter.api._
import miniboxing.example.vector3D.MiniboxedVector3D
import miniboxing.example.vector3D.SpecializedVector3D
import miniboxing.example.vector3D.GenericVector3D
import miniboxing.test.infrastructure.FreezingTest

object Vector3DBenchmark extends FreezingTest {

  var outsider = 0.0
  val size = 3000000

  // Vectors
  val mbVec3D = new MiniboxedVector3D[Double](3.5,4.5,6.0)
  val spVec3D = new SpecializedVector3D[Double](3.5,4.5,6.0)
  val gnVec3D = new GenericVector3D[Double](3.5,4.5,6.0)

  assert(mbVec3D.getClass.getSimpleName() == "MiniboxedVector3D_J")

  val mbVec3DGen = Gen.single("Miniboxed Vector 3D")(mbVec3D)
  val spVec3DGen = Gen.single("Specialized Vector 3D")(spVec3D)
  val gnVec3DGen = Gen.single("Generic Vector 3D")(gnVec3D)

  performance of "Vector3D" in {
    measure method "+" in {
      using(gnVec3DGen) in {
        u =>
          val v = new GenericVector3D[Double](-2.5,-4.5,-6.0)
          var i = 0
          var result = 0.0
          while (i < size) {
            result += (u + v).dist
            i += 1
          }

          outsider = result  // avoid in-lining
      }

      using(spVec3DGen) in {
        u =>
          val v = new SpecializedVector3D[Double](-2.5,-4.5,-6.0)
          var i = 0
          var result = 0.0
          while (i < size) {
            result += (u + v).dist
            i += 1
          }

          outsider = result  // avoid in-lining
      }

      using(mbVec3DGen) in {
        u =>
          val v = new MiniboxedVector3D[Double](-2.5,-4.5,-6.0)
          var i = 0
          var result = 0.0
          while (i < size) {
            result += (u + v).dist
            i += 1
          }

          outsider = result  // avoid in-lining
      }
    }
  }
}
