package miniboxing.example.math

package ordering {
  trait MyOrdering[@miniboxed T] {

    def gt(x: T, y: T): Boolean
    def lt(x: T, y: T): Boolean
    def eq(x: T, Y: T): Boolean
  }
}

package object `ordering` {

  implicit object MyOrderingInt extends MyOrdering[Int] {

    def gt(x: Int, y: Int): Boolean = x > y
    def lt(x: Int, y: Int): Boolean = x < y
    def eq(x: Int, y: Int): Boolean = x == y
  }
}
