package freezing.macros

import scala.reflect.macros.Context
import scala.language.experimental.macros

object MacroVector3D {

  import BenchType._
  
  def benchmarkVector3D(tpe: BenchType): Unit = macro benchmark_impl

  def benchmark_impl(c: Context)(tpe: c.Expr[BenchType]): c.Expr[Unit] = {
    import c.universe._

    val parameter: TypeDef = tpe.tree.symbol.name.toString match {
      case "Generic"     => q"type T"
      case "Miniboxed"   => q"@miniboxed type T"
      case "Specialized" => q"@specialized type T"
    }
    
    val numeric: Tree = q"""
      object Math {
        trait MyNumeric[$parameter] {
          def minus(x: T, y: T): T
          def plus(x: T, y: T): T
          def mult(x: T, y: T): T
          def equals(x: T, y: T): Boolean
          def zero: T
          def one: T
          
          def toDouble(x: T): Double
        }
      }
    """

    val target: Tree = q"""
      object BenchmarkTarget {
        
        import Math.MyNumeric
        
        class Vector3D[$parameter : MyNumeric](val x: T, val y: T, val z: T) {
        
          def +(that: Vector3D[T]): Vector3D[T] = {
            val nm = implicitly[MyNumeric[T]]
            new Vector3D[T](nm.plus(this.x, that.x), nm.plus(this.y, that.y), nm.plus(this.z, that.z))
          }
          
          def -(that: Vector3D[T]): Vector3D[T] = {
            val nm = implicitly[MyNumeric[T]]
            new Vector3D[T](nm.minus(this.x, that.x), nm.minus(this.y, that.y), nm.minus(this.z, that.z))
          }
          
          def ==(that: Vector3D[T]): Boolean = {
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
      }
    """

    val benchmarks: List[Tree] =
      for (T <- List(tq"Int", tq"Double", tq"Long")) yield {
        val benchTitle = "Benchmark for " + tpe.tree.symbol.name + " Vector3D[" + T.name.toString + "]:  "
        val benchString = c.parse("\"" + benchTitle + "\"")
        
        val elementsThis: List[Tree] = T match {
          case tq"Int"    => List(q"1", q"2", q"3")
          case tq"Double" => List(q"1.0", q"2.0", q"3.0")
          case tq"Long"   => List(q"1", q"2", q"3")
        }
        val elementsThat: List[Tree] = T match {
          case tq"Int"    => List(q"-1", q"-2", q"-2")
          case tq"Double" => List(q"-1.0", q"-2.0", q"-2.0")
          case tq"Long"   => List(q"-1", q"-2", q"-2")
        }

        q"""
          import Math.MyNumeric
          import BenchmarkTarget._
        
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
            def zero: Double = 0.0d
            def one: Double = 1.0d
            
            def toDouble(x: Double): Double = x
          }
        
          implicit object MyNumericFloat extends MyNumeric[Float] {
            def minus(x: Float, y: Float): Float = x - y
            def plus(x: Float, y: Float): Float = x + y
            def mult(x: Float, y: Float): Float = x * y
            def equals(x: Float, y: Float): Boolean = x == y
            def zero: Float = 0.0f
            def one: Float = 1.0f
            
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
          
          var outsider = 0.0
          val size = 300000
          
          val vector3D = new Vector3D[$T](${elementsThis(0)}, ${elementsThis(1)}, ${elementsThis(2)})
          val vector3DGen = Gen.single("Vector3D")(vector3DGen)
          
          performance of $benchString in {
            measure method "+" in {
              using(vector3DGen) in {
                u =>
                  val v = new Vector3D[$T](${elementsThat(0)}, ${elementsThat(1)}, ${elementsThat(2)})
                  var i = 0
                  var result = 0.0
                  while (i < size) {
                    result += (u + v).dist
                    i += 1
                  }
                  
                  outsider = result  // avoid in-lining
              }
            }
          }
        """
      }

    val tree = q"""
      $numeric
      $target
      ..$benchmarks
    """

    c.Expr[Unit](tree)
  }
}
