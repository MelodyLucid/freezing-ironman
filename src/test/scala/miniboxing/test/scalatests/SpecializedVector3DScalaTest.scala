package miniboxing.test.scalatests

import org.scalatest.FunSuite
import miniboxing.example.vector3D.SpecializedVector3D

/**
 * Checks the well behavior of the MiniboxedVector3D[T] class.
 * 
 */
class SpecializedVector3DScalaTest extends FunSuite {

  test("Equals operator (==)") {
    val v: SpecializedVector3D[Int] = new SpecializedVector3D[Int](1,2,3)
    
    assert(v == v)
  }
  
  test("Plus operator (+)") {
    val v: SpecializedVector3D[Int] = new SpecializedVector3D[Int](1,2,3)
    val u: SpecializedVector3D[Int] = new SpecializedVector3D[Int](4,5,6)
    
    val w: SpecializedVector3D[Int] = new SpecializedVector3D[Int](5,7,9)
    
    assert(u + v == w)
  }
  
  test("Minus operator (-)") {
    val v: SpecializedVector3D[Int] = new SpecializedVector3D[Int](1,2,3)
    val u: SpecializedVector3D[Int] = new SpecializedVector3D[Int](4,5,6)
    
    val w: SpecializedVector3D[Int] = new SpecializedVector3D[Int](3,3,3)
    
    assert(u - v == w)
  }
}