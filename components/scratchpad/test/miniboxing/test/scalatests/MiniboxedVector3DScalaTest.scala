package miniboxing.test.scalatests

import org.scalatest.FunSuite
import miniboxing.example.vector3D.MiniboxedVector3D

/**
 * Checks the well behavior of the MiniboxedVector3D[T] class.
 * 
 */
class MiniboxedVector3DScalaTest extends FunSuite {

  test("Equals operator (==)") {
    val v: MiniboxedVector3D[Int] = new MiniboxedVector3D[Int](1,2,3)
    
    assert(v == v)
  }
  
  test("Plus operator (+)") {
    val v: MiniboxedVector3D[Int] = new MiniboxedVector3D[Int](1,2,3)
    val u: MiniboxedVector3D[Int] = new MiniboxedVector3D[Int](4,5,6)
    
    val w: MiniboxedVector3D[Int] = new MiniboxedVector3D[Int](5,7,9)
    
    assert(u + v == w)
  }
  
  test("Minus operator (-)") {
    val v: MiniboxedVector3D[Int] = new MiniboxedVector3D[Int](1,2,3)
    val u: MiniboxedVector3D[Int] = new MiniboxedVector3D[Int](4,5,6)
    
    val w: MiniboxedVector3D[Int] = new MiniboxedVector3D[Int](3,3,3)
    
    assert(u - v == w)
  }
}