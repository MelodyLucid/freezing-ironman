package freezing.macros

import scala.reflect.macros.Context
import scala.language.experimental.macros

object MacroLinkedList {

  import BenchType._
  
  def benchmarkLinkedList(tpe: BenchType): Unit = macro benchmark_impl

  def benchmark_impl(c: Context)(tpe: c.Expr[BenchType]): c.Expr[Unit] = {
    import c.universe._

    val parameter: TypeDef = tpe.tree.symbol.name.toString match {
      case "Generic"     => q"type T"
      case "Miniboxed"   => q"@miniboxed type T"
      case "Specialized" => q"@specialized type T"
    }

    val target: Tree = q"""
      object BenchmarkTarget {
        class LinkedList[$parameter](implicit manifest: Manifest[T]) {
        
          private var amount: Int = 0
          private val first: Node[T] = new Node[T](null.asInstanceOf[T], null)
          private var last: Node[T] = first
        
          def add(t: T): Unit = addAfter(t, last)
          
          def addAt(t: T, i: Int): Unit = addAfter(t, getNode(i))
          
          def get(i: Int): T = getNode(i).next.elem
          
          def size: Int = amount
          
          def remove(t: T): Unit = {
            var node = first
            while (node != null && node.elem != t) {
              node = node.next
            }
            if (node == null) {
              throw new NoSuchElementException
            }
            if (node.next eq last) {
              last = node
            }
            node.next = node.next.next
            amount -= 1
          }
          
          def removeAt(i: Int): Unit = {
            val node = getNode(i)
            if (node.next eq last) {
              last = node
            }
            node.next = node.next.next
            amount -= 1
          }
          
//          def map[U](f: T => U)(implicit mu: Manifest[U]) = {
//            val linkedList = new LinkedList[U]
//            var nodeInit = first
//            var nodeNew = linkedList.first
//            while (nodeInit.next != null) {
//              nodeInit = nodeInit.next
//              nodeNew.next = new Node[U](f(nodeInit.elem), null)
//              nodeNew = nodeNew.next
//            }
//            linkedList.last = nodeNew
//            linkedList
//          }
//          
//          def filter(f: T => Boolean) = {
//            val linkedList = new LinkedList[T]
//            var nodeInit = first
//            var nodeNew = linkedList.first
//            while (nodeInit.next != null) {
//              nodeInit = nodeInit.next
//              if (f(nodeInit.elem)) {
//                nodeNew.next = new Node[T](nodeInit.elem, null)
//                nodeNew = nodeNew.next
//              }
//            }
//            linkedList.last = nodeNew
//            linkedList
//          }
//          
//          def fold[U](f: (U, T) => U, z: U) = {
//            var folded = z
//            var node = first
//            while (node.next != null) {
//              node = node.next
//              folded = f(folded, node.elem)
//            }
//            folded
//          }
        
          def clear() {
            first.next = null
            last = first
          }
        
          def toArray: Array[T] = {
            val array = new Array[T](amount)
            var node = first
            var i = 0
            while (i < amount) {
              node = node.next
              array(i) = node.elem
              i += 1
            }
            array
          }
          
          override def toString: String = "(" + toArray.mkString(" ") + ")"
          
          def getNode(i: Int): Node[T] = {
            if (i < 0) {
              throw new NoSuchElementException
            }
            var node = first
            var count = i
            while (count > 0 && node.next != null) {
              count -= 1
              node = node.next
            }
            if (node.next == null) {
              throw new NoSuchElementException
            }
            node
          }
          
          def addAfter(t: T, n: Node[T]): Unit = {
            val node = new Node[T](t, n.next)
            if (last eq n) {
              last = node
            }
            node.next = n.next
            n.next = node
            amount += 1
          }
        }
        
        class Node[T](var elem: T, var next: Node[T])
      }
    """

    val benchmarks: List[Tree] =
      for (T <- List(tq"Int", tq"String", tq"Long")) yield {
        val benchTitle = "Benchmark for " + tpe.tree.symbol.name + " LinkedList[" + T.name.toString + "]:  "
        val benchString = c.parse("\"" + benchTitle + "\"")
        
        val elements: List[Tree] = T match {
          case tq"Int"    => List(q"1", q"2", q"3")
          case tq"String" => List(c.parse("\"" + "1" + "\""), c.parse("\"" + "2" + "\""), c.parse("\"" + "3" + "\""))
          case tq"Long"   => List(q"1", q"2", q"3")
        }

        q"""
          import BenchmarkTarget._
          
          var outsider = 0.0
          val size = 300000
          
          val linkedList = new LinkedList[$T]
          val linkedListGen = Gen.single("LinkedList")(linkedList)

          performance of $benchString in {
            measure method "get" in {
              using(linkedListGen) in {
                l => 
                  var i = 0
                  var result = 0.0
                  l.add(${elements(0)})
                  l.add(${elements(1)})
                  l.add(${elements(2)})
                  while (i < size) {
                    result += l.get(1).toDouble
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
