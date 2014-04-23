package miniboxing.example

package math {
  /**
   * Trait that represents a numeric value, implemented with the
   * Miniboxing plug-in.
   */

  trait MyNumeric[@miniboxed T] {
    def minus(x: T, y: T): T
    def plus(x: T, y: T): T
    def mult(x: T, y: T): T
    def equals(x: T, y: T): Boolean
    def zero: T
    def one: T

    def toDouble(x: T): Double

  }
}

package object `math` {
  /*
   * Implicit objects of MyNumeric for every primitive type
   */

  implicit object MyNumericInt extends MyNumeric[Int] {
    def minus(x: Int, y: Int): Int = x - y
    def plus(x: Int, y: Int): Int = x + y
    def mult(x: Int, y: Int): Int = x * y
    def equals(x: Int, y: Int): Boolean = x == y
    def zero: Int = 0
    def one: Int = 1

    def toDouble(x: Int): Double = x
  }

  implicit object MyNumericByte extends MyNumeric[Byte] {
    def minus(x: Byte, y: Byte): Byte = (x - y).toByte
    def plus(x: Byte, y: Byte): Byte = (x + y).toByte
    def mult(x: Byte, y: Byte): Byte = (x * y).toByte
    def equals(x: Byte, y: Byte): Boolean = x == y
    def zero: Byte = 0
    def one: Byte = 1

    def toDouble(x: Byte): Double = x
  }

  implicit object MyNumericShort extends MyNumeric[Short] {
    def minus(x: Short, y: Short): Short = (x - y).toShort
    def plus(x: Short, y: Short): Short = (x + y).toShort
    def mult(x: Short, y: Short): Short = (x * y).toShort
    def equals(x: Short, y: Short): Boolean = x == y
    def zero: Short = 0
    def one: Short = 1

    def toDouble(x: Short): Double = x
  }

  implicit object MyNumericLong extends MyNumeric[Long] {
    def minus(x: Long, y: Long): Long = x - y
    def plus(x: Long, y: Long): Long = x + y
    def mult(x: Long, y: Long): Long = x * y
    def equals(x: Long, y: Long): Boolean = x == y
    def zero: Long = 0L
    def one: Long = 1L

    def toDouble(x: Long): Double = x
  }

   implicit object MyNumericDouble extends MyNumeric[Double] {
    def minus(x: Double, y: Double): Double = x - y
    def plus(x: Double, y: Double): Double = x + y
    def mult(x: Double, y: Double): Double = x * y
    def equals(x: Double, y: Double): Boolean = x == y
    def zero: Double = 0.00d
    def one: Double = 1.00d

    def toDouble(x: Double): Double = x
  }

  implicit object MyNumericFloat extends MyNumeric[Float] {
    def minus(x: Float, y: Float): Float = x - y
    def plus(x: Float, y: Float): Float = x + y
    def mult(x: Float, y: Float): Float = x * y
    def equals(x: Float, y: Float): Boolean = x == y
    def zero: Float = 0.00f
    def one: Float = 1.00f

    def toDouble(x: Float): Double = x
  }

  implicit object MyNumericBoolean extends MyNumeric[Boolean] {
    def minus(x: Boolean, y: Boolean): Boolean = x || !y
    def plus(x: Boolean, y: Boolean): Boolean = x || y
    def mult(x: Boolean, y: Boolean): Boolean = x && y
    def equals(x: Boolean, y: Boolean): Boolean = x == y
    def zero: Boolean = false
    def one: Boolean = true

    def toDouble(x: Boolean): Double = if (x) 1.0 else 0.0
  }

  implicit object MyNumericChar extends MyNumeric[Char] {
    def minus(x: Char, y: Char): Char = (x.toInt - y.toInt).toChar
    def plus(x: Char, y: Char): Char = (x.toInt + y.toInt).toChar
    def mult(x: Char, y: Char): Char = (x.toInt * y.toInt).toChar
    def equals(x: Char, y: Char): Boolean = x == y
    def zero: Char = 0
    def one: Char = 1

    def toDouble(x: Char): Double = x
  }
}
