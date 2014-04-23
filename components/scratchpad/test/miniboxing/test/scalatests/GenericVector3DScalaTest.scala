package miniboxing.test.scalatests

import org.scalatest.FunSuite
import miniboxing.example.vector3D.GenericVector3D

/**
 * Checks the well behavior of the GenericVector3D[T] class.
 * 
 */
class GenericVector3DScalaTest extends FunSuite {

  test("Equals operator (==)") {
    val v: GenericVector3D[Int] = new GenericVector3D[Int](1,2,3)
    
    assert(v == v)
  }
  
  test("Plus operator (+)") {
    val v: GenericVector3D[Int] = new GenericVector3D[Int](1,2,3)
    val u: GenericVector3D[Int] = new GenericVector3D[Int](4,5,6)
    
    val w: GenericVector3D[Int] = new GenericVector3D[Int](5,7,9)
    
    assert(u + v == w)
  }
  
  test("Minus operator (-)") {
    val v: GenericVector3D[Int] = new GenericVector3D[Int](1,2,3)
    val u: GenericVector3D[Int] = new GenericVector3D[Int](4,5,6)
    
    val w: GenericVector3D[Int] = new GenericVector3D[Int](3,3,3)
    
    assert(u - v == w)
  }
}