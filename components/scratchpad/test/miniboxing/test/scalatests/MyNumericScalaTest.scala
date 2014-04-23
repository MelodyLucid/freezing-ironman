package miniboxing.test.scalatests

import org.scalatest.FunSuite
import miniboxing.example.math.MyNumeric

/**
 * Checks the well behavior of the MyNumeric[T] implicit objects.
 * 
 */
class MyNumericScalaTest extends FunSuite {

  test("Implicit object MyNumericChar") {
    val nm = implicitly[MyNumeric[Char]]
    
    assert(nm.plus(' ', '!') == 'A')
    assert(nm.minus('A', '!') == ' ')
    assert(nm.mult(' ', ' ') == 'Ѐ')
    assert(nm.equals('A', 'A'))
    
    assert(nm.plus(nm.zero,nm.zero) == nm.zero)
    assert(nm.mult(nm.one,nm.one) == nm.one)
    
    assert(nm.toDouble('A') == 65.0d)
  }
  
  test("Implicit object MyNumericBoolean") {
    val nm = implicitly[MyNumeric[Boolean]]
    
    assert(nm.plus(true, false))
    assert(nm.minus(false, false))
    assert(!nm.mult(true, false))
    assert(nm.equals(true, true))
    
    assert(!nm.plus(nm.zero,nm.zero))
    assert(nm.mult(nm.one,nm.one))
    
    assert(nm.toDouble(true) == 1.0d)
  }
  
  test("Implicit object MyNumericDouble") {
    val nm = implicitly[MyNumeric[Double]]
    
    assert(nm.plus(4.5d, 1.5d) == 6.0d)
    assert(nm.minus(4.5d, 1.5d) == 3.0d)
    assert(nm.mult(3.0d, 4.0d) == 12.0d)
    assert(nm.equals(5.76d, 5.76d))
    
    assert(nm.plus(nm.zero,nm.zero) == nm.zero)
    assert(nm.mult(nm.one,nm.one) == nm.one)
    
    assert(nm.toDouble(1.0d) == 1.0d)
  }
  
  test("Implicit object MyNumericFloat") {
    val nm = implicitly[MyNumeric[Float]]
    
    assert(nm.plus(4.5f, 1.5f) == 6.0f)
    assert(nm.minus(4.5f, 1.5f) == 3.0f)
    assert(nm.mult(3.0f, 4.0f) == 12.0f)
    assert(nm.equals(5.76f, 5.76f))
    
    assert(nm.plus(nm.zero,nm.zero) == nm.zero)
    assert(nm.mult(nm.one,nm.one) == nm.one)
    
    assert(nm.toDouble(1.0f) == 1.0d)
  }
  
  test("Implicit object MyNumericInt") {
    val nm = implicitly[MyNumeric[Int]]
    
    assert(nm.plus(4, 2) == 6)
    assert(nm.minus(4, 2) == 2)
    assert(nm.mult(3, 4) == 12)
    assert(nm.equals(7, 7))
    
    assert(nm.plus(nm.zero,nm.zero) == nm.zero)
    assert(nm.mult(nm.one,nm.one) == nm.one)
    
    assert(nm.toDouble(1) == 1.0d)
  }
  
  test("Implicit object MyNumericByte") {
    val nm = implicitly[MyNumeric[Byte]]
    
    assert(nm.plus(4, 2) == 6)
    assert(nm.minus(4, 2) == 2)
    assert(nm.mult(3, 4) == 12)
    assert(nm.equals(7, 7))
    
    assert(nm.plus(nm.zero,nm.zero) == nm.zero)
    assert(nm.mult(nm.one,nm.one) == nm.one)
    
    assert(nm.toDouble(1) == 1.0d)
  }
  
  test("Implicit object MyNumericShort") {
    val nm = implicitly[MyNumeric[Short]]
    
    assert(nm.plus(4, 2) == 6)
    assert(nm.minus(4, 2) == 2)
    assert(nm.mult(3, 4) == 12)
    assert(nm.equals(7, 7))
    
    assert(nm.plus(nm.zero,nm.zero) == nm.zero)
    assert(nm.mult(nm.one,nm.one) == nm.one)
    
    assert(nm.toDouble(1) == 1.0d)
  }
  
  test("Implicit object MyNumericLong") {
    val nm = implicitly[MyNumeric[Long]]
    
    assert(nm.plus(4L, 2L) == 6L)
    assert(nm.minus(4L, 2L) == 2L)
    assert(nm.mult(3L, 4L) == 12L)
    assert(nm.equals(7L, 7L))
    
    assert(nm.plus(nm.zero,nm.zero) == nm.zero)
    assert(nm.mult(nm.one,nm.one) == nm.one)
    
    assert(nm.toDouble(1L) == 1.0d)
  }
}