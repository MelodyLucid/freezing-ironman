package freezing.macros

import scala.reflect.macros.Context
import scala.language.experimental.macros

object MacroHashMap {

  import BenchType._
  
  def benchmarkHashMap(tpe: BenchType): Unit = macro benchmark_impl

  def benchmark_impl(c: Context)(tpe: c.Expr[BenchType]): c.Expr[Unit] = {
    import c.universe._

    val keyParameter: TypeDef = tpe.tree.symbol.name.toString match {
      case "Generic"     => q"type K"
      case "Miniboxed"   => q"@miniboxed type K"
      case "Specialized" => q"@specialized type K"
    }
    
    val valParameter: TypeDef = tpe.tree.symbol.name.toString match {
      case "Generic"     => q"type V"
      case "Miniboxed"   => q"@miniboxed type V"
      case "Specialized" => q"@specialized type V"
    }

    val target: Tree = q"""
      object BenchmarkTarget {
        class HashMap[$keyParameter, $valParameter](implicit keyManifest: Manifest[K], valueManifest: Manifest[V]) {
          val size = 128
          private var values = new Array[V](size)
          private var keys = new Array[K](size)
          private var count = new Array[Int](size)
          
          def put(key: K, value: V): V = {
            val keyHashCode = {
              val k = key.hashCode % size
              if (k <= 0) k + size
              else k
            }
            
            def recursivePut(index: Int): V = {
              if (index == keyHashCode - 1 && count(index) != 0) {
                throw new Error("HashMap is full.")
              }
              else if (count(index) == 0) {
                keys(index) = key
                values(index) = value
                count(index) = 1
                value
              } else {
                count(index) += 1
                recursivePut((index + 1) % size)
              }
            }
            
            recursivePut(keyHashCode % size)
          }
          
          def get(key: K): V = {
            val keyHashCode = {
              val k = key.hashCode % size
              if (k <= 0) k + size
              else k
            }
            
            def recursiveGet(index: Int): V = {
              if (index == keyHashCode - 1 && keys(index) != key) {
                null.asInstanceOf[V]
              } else if (keys(index) == key) {
                values(index)
              } else {
                recursiveGet((index + 1) % size)
              }
            }
            
            if (contains(key)) {
              recursiveGet(keyHashCode % size)
            } else {
              null.asInstanceOf[V]
            }
          }
          
          def contains(key: K): Boolean = {
            val keyHashCode = {
              val k = key.hashCode % size
              if (k <= 0) k + size
              else k
            }
            
            def recursiveContains(index: Int): Boolean = {
              if (index == keyHashCode - 1 && (count(index) == 0 || keys(index) != key)) {
                false
              } else if (count(index) == 0) {
                false
              } else if (keys(index) == key) {
                true
              } else recursiveContains((index + 1) % size)
            }
            
            recursiveContains(keyHashCode % size)
          }
          
          def remove(key: K): Unit = {
            val keyHashCode = {
              val k = key.hashCode % size
              if (k <= 0) k + size
              else k
            }
            
            def recursiveRemove(index: Int): Boolean = {
              if (index == keyHashCode - 1 && keys(index) != key) {
                false
              } else if (keys(index) == key) {
                val value = values(index)
                count(index) -= 1
                values(index) = null.asInstanceOf[V] // unnecessary, avoid garbage
                keys(index) = null.asInstanceOf[K] // unnecessary, avoid garbage
                true
              } else {
                val isRemoved = recursiveRemove((index + 1) % size)
                if (isRemoved) { 
                  count(index) -= 1
                }
                isRemoved
              }
            }
            
            if (contains(key)) {
              recursiveRemove(keyHashCode % size)
            } else {
              null.asInstanceOf[V]
            }
          }
        }
      }
    """

    val benchmarks: List[Tree] =
      for (K <- List(tq"Int", tq"String", tq"Long"); V <- List(tq"Int", tq"String", tq"Long")) yield {
        val benchTitle = "Benchmark for " + tpe.tree.symbol.name + " HashMap[" + K.name.toString + ", " + V.name.toString + "]:  "
        val benchString = c.parse("\"" + benchTitle + "\"")
        
        val key: Tree = K match {
          case tq"Int"    => q"8"
          case tq"String" => c.parse("\"" + "8" + "\"")
          case tq"Long"   => q"8"
        }
        val value: Tree = V match {
          case tq"Int"    => q"1"
          case tq"String" => c.parse("\"" + "1" + "\"")
          case tq"Long"   => q"1"
        }

        q"""
          import BenchmarkTarget._
          
          var outsider = 0.0
          val size = 300000
          
          val hashMap = new HashMap[$K, $V]
          val hashMapGen = Gen.single("HashMap")(hashMap)

          performance of $benchString in {
            measure method "get" in {
              using(hashMapGen) in {
                h =>
                  var i = 0
                  var result = 0.0
                  h.put($key, $value)
                  while (i < size) {
                    result += h.get($key).toDouble
                    i += 1
                  }
        
                  outsider = result  // avoid in-lining
              }
            }
          }
        """
      }

    val tree = q"""
      $target
      ..$benchmarks
    """

    c.Expr[Unit](tree)
  }
}
