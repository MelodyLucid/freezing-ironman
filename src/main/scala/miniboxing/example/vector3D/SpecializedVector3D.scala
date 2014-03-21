package miniboxing.example.vector3D

import miniboxing.example.math.MyNumeric

class SpecializedVector3D[@specialized T : MyNumeric](val x: T, val y: T, val z: T) {

  def +(that: SpecializedVector3D[T]): SpecializedVector3D[T] = {
    val mn = implicitly[MyNumeric[T]]
    new SpecializedVector3D[T](mn.plus(this.x, that.x), mn.plus(this.y, that.y), mn.plus(this.z, that.z))
  }
  
  def -(that: SpecializedVector3D[T]): SpecializedVector3D[T] = {
    val mn = implicitly[MyNumeric[T]]
    new SpecializedVector3D[T](mn.minus(this.x, that.x), mn.minus(this.y, that.y), mn.minus(this.z, that.z))
  }
  
  def ==(that: SpecializedVector3D[T]): Boolean = {
    val mn = implicitly[MyNumeric[T]]
    mn.equals(this.x, that.x) && mn.equals(this.y, that.y) && mn.equals(this.z, that.z)
  }
  
  def dist: Double = {
    val mn = implicitly[MyNumeric[T]]
    Math.sqrt(mn.toDouble(mn.mult(this.x, this.x))
        + mn.toDouble(mn.mult(this.y, this.y))
        + mn.toDouble(mn.mult(this.z, this.z)))
  }
}