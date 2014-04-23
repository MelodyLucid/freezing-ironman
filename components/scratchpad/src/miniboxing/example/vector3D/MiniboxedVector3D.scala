package miniboxing.example.vector3D

import miniboxing.example.math.MyNumeric

/* 
 * create a simple Vector (or try HashMap instead), add methods, and then add the
 * builder pattern, then the miniboxing to methods (especially map and fold, and to[List])
 *
 * try to plot graphs with Scalameter 
 */

class MiniboxedVector3D[@miniboxed T : MyNumeric](val x: T, val y: T, val z: T) {

  def +(that: MiniboxedVector3D[T]): MiniboxedVector3D[T] = {
    val nm = implicitly[MyNumeric[T]]
    new MiniboxedVector3D[T](nm.plus(this.x, that.x), nm.plus(this.y, that.y), nm.plus(this.z, that.z))
  }
  
  def -(that: MiniboxedVector3D[T]): MiniboxedVector3D[T] = {
    val nm = implicitly[MyNumeric[T]]
    new MiniboxedVector3D[T](nm.minus(this.x, that.x), nm.minus(this.y, that.y), nm.minus(this.z, that.z))
  }
  
  def ==(that: MiniboxedVector3D[T]): Boolean = {
    val nm = implicitly[MyNumeric[T]]
    nm.equals(this.x, that.x) && nm.equals(this.y, that.y) && nm.equals(this.z, that.z)
  }
  
  def dist: Double = {
    val nm = implicitly[MyNumeric[T]]
    Math.sqrt(nm.toDouble(nm.mult(this.x, this.x))
        + nm.toDouble(nm.mult(this.y, this.y))
        + nm.toDouble(nm.mult(this.z, this.z)))
  }
}