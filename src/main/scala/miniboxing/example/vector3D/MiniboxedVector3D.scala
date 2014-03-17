package miniboxing.example.vector3D

/* 
 * MyNumeric trait with methods add, minus, etc.
 * implicit object MyNumericInt[Int] overriding the methods
 *
 * create a simple Vector (or try HashMap instead), add methods, and then add the
 * builder pattern, then the miniboxing to methods (especially map and fold, and to[List])
 *
 * try to plot graphs with Scalameter 
 */

//class MiniboxedVector3D[@miniboxed T : Numeric](val x: T, val y: T, val z: T) {
//
//  def +(mbVec3D: MiniboxedVector3D[T]): MiniboxedVector3D[T] = {
//    val n = implicitly[Numeric[T]]
//    n.
//    new MiniboxedVector3D[T](x + mbVec3D.x, y + mbVec3D.y, z + mbVec3D.z)
//  }
//}
//
//trait MyNumeric[@miniboxed T] {
//  
//}
//
//implicit object MyNumericInt extends MyNumeric[Int] {
//  
//}