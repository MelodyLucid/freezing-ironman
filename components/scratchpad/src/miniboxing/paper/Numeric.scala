package miniboxing

package paper {
  trait Numeric[@miniboxed T] {
  
    def plus(x: T, y: T): T
    
    def zero: T
  }
}
//
//package object `math` {
//  
//  implicit object Num_I extends Numeric[Double] {
//    def plus(x: Double, y: Double): Double = x + y
//    
//    def zero: Double = 0.0
//  }
//}