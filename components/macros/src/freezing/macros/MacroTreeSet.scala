package freezing.macros

import scala.reflect.macros.Context
import scala.language.experimental.macros

object MacroTreeSet {

  import BenchType._
  
  def benchmarkTreeSet(tpe: BenchType): Unit = macro benchmark_impl

  def benchmark_impl(c: Context)(tpe: c.Expr[BenchType]): c.Expr[Unit] = {
    import c.universe._

    val parameter: TypeDef = tpe.tree.symbol.name.toString match {
      case "Generic"     => q"type E"
      case "Miniboxed"   => q"@miniboxed type E"
      case "Specialized" => q"@specialized type E"
    }

    val ordering: Tree = q"""
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
    """
    
    val target: Tree = q"""
      import ordering.MyOrdering
      
      class TreeSet[$parameter](implicit m: Manifest[E], order: MyOrdering[E]) {
        
        private var amount: Int = 0
        private var root: TreeNode[E] = null
        
        def size: Int = amount
        
        def add(e: E): Unit = {
          if (root == null) {
            root = new TreeNode[E](null, e, null, null)
            amount += 1
          } else {
              recursiveAdd(root)
          }
          
          def recursiveAdd(node: TreeNode[E]): Unit = {
            if (order.lt(e, node.elem)) {
              if (node.left == null) {
                node.left = new TreeNode[E](node, e, null, null)
                amount += 1
              } else  {
                recursiveAdd(node.left)
              }
            } else if (order.gt(e, node.elem)) {
              if (node.right == null) {
                node.right = new TreeNode[E](node, e, null, null)
                amount += 1
              } else {
                recursiveAdd(node.right)
              }
            } // else they're equal
          }
        }
        
        def remove(e: E): Unit = {
          if (root != null) {
            recursiveRemove(root)
          }
          
          def recursiveRemove(node: TreeNode[E]): Unit = {
            if (order.gt(e,node.elem)) recursiveRemove(node.right)
            else if (order.lt(e,node.elem)) recursiveRemove(node.left)
            else removeNode(node)
          }
          
          def removeNode(node: TreeNode[E]): Unit = {
            var n: TreeNode[E] = node
            if (n.left != null && n.right != null) {
              n.elem = successor(n).elem
              n = successor(n)
            }
            
            var child: TreeNode[E] = {
              if (n.left != null) n.left
              else n.right
            }
            if (child != null) child.parent = n.parent
            if (n.parent == null) root = child
            else if (n == n.parent.left) n.parent.left = child
            else n.parent.right = child
            amount -= 1
          }
          
          def successor(node: TreeNode[E]): TreeNode[E] = {
            var successor: TreeNode[E] = node.right
            while (successor.left != null) {
              successor = successor.left
            }
            successor
          }
        }
        
        def contains(e: E): Boolean = {
          def recursiveContains(node: TreeNode[E]): Boolean = {
            if (order.eq(node.elem,e)) true
            else if (node.left != null && recursiveContains(node.left)) true
            else if (node.right != null && recursiveContains(node.right)) true
            else false
          }
          
          if (root == null) false
          else recursiveContains(root)
        }
        
        override def toString: String = if (root != null) root.toString else "."
      }
      
      class TreeNode[E](var parent: TreeNode[E], var elem: E, var left: TreeNode[E], var right: TreeNode[E]) {
      
        def contains(elem: E): Boolean = this.elem == elem
        
        override def toString: String = {
          var l = if (left != null) left.toString else "."
          var r = if (right != null) right.toString else "."
          "(" + l + " " + elem.toString + " " + r + ")"
        }
      }
    """

    val benchmarks: List[Tree] =
      for (E <- List(tq"Int", tq"String", tq"Long")) yield {
        val benchTitle = "Benchmark for " + tpe.tree.symbol.name + " TreeSet[" + E.name.toString + "]:  "
        val benchString = c.parse("\"" + benchTitle + "\"")
        
        val elements: List[Tree] = E match {
          case tq"Int"    => List(q"1", q"2", q"3")
          case tq"String" => List(c.parse("\"" + "1" + "\""), c.parse("\"" + "2" + "\""), c.parse("\"" + "3" + "\""))
          case tq"Long"   => List(q"1", q"2", q"3")
        }

        q"""
          import BenchmarkTarget._
          
          var outsider = 0.0
          val size = 300000
          
          val treeSet = new TreeSet[$E]
          val treeSetGen = Gen.single("TreeSet")(treeSet)

          performance of $benchString in {
            measure method "get" in {
              using(treeSetGen) in {
                t => 
                  var i = 0
                  var result = 0.0
                  t.add(${elements(0)})
                  t.add(${elements(1)})
                  while (i < size) {
                    assert(t.contains(${elements(1)}))
                    assert(!t.contains(${elements(2)}))
                    i += 1
                    result += 1.0
                  }
                  
                  outsider = result  // avoid in-lining
              }
            }
          }
        """
      }

    val tree = q"""
      $ordering
      $target
      ..$benchmarks
    """

    c.Expr[Unit](tree)
  }
}
