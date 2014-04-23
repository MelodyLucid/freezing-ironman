package miniboxing.example.treeset

import miniboxing.example.math.ordering.MyOrdering

class MiniboxedTreeSet[@miniboxed E](implicit m: Manifest[E], order: MyOrdering[E]) {

  private var amount: Int = 0
  private var root: MiniboxedTreeNode[E] = null

  def size: Int = amount
  
  def add(e: E): Unit = {
    if (root == null) {
      root = new MiniboxedTreeNode[E](null, e, null, null)
      amount += 1
    } else {
        recursiveAdd(root)
    }
    
    def recursiveAdd(node: MiniboxedTreeNode[E]): Unit = {
      if (order.lt(e, node.elem)) {
        if (node.left == null) {
          node.left = new MiniboxedTreeNode[E](node, e, null, null)
          amount += 1
        } else  {
          recursiveAdd(node.left)
        }
      } else if (order.gt(e, node.elem)) {
        if (node.right == null) {
          node.right = new MiniboxedTreeNode[E](node, e, null, null)
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
    
    def recursiveRemove(node: MiniboxedTreeNode[E]): Unit = {
      if (order.gt(e,node.elem)) recursiveRemove(node.right)
      else if (order.lt(e,node.elem)) recursiveRemove(node.left)
      else removeNode(node)
    }
    
    def removeNode(node: MiniboxedTreeNode[E]): Unit = {
      var n: MiniboxedTreeNode[E] = node
      if (n.left != null && n.right != null) {
        n.elem = successor(n).elem
        n = successor(n)
      }
      
      var child: MiniboxedTreeNode[E] = {
        if (n.left != null) n.left
        else n.right
      }
      if (child != null) child.parent = n.parent
      if (n.parent == null) root = child
      else if (n == n.parent.left) n.parent.left = child
      else n.parent.right = child
      amount -= 1
    }
    
    def successor(node: MiniboxedTreeNode[E]): MiniboxedTreeNode[E] = {
      var successor: MiniboxedTreeNode[E] = node.right
      while (successor.left != null) {
        successor = successor.left
      }
      successor
    }
  }
  
  def contains(e: E): Boolean = {
    def recursiveContains(node: MiniboxedTreeNode[E]): Boolean = {
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

class MiniboxedTreeNode[E](var parent: MiniboxedTreeNode[E], var elem: E, var left: MiniboxedTreeNode[E], var right: MiniboxedTreeNode[E]) {

  def contains(elem: E): Boolean = this.elem == elem
  
  override def toString: String = {
    var l = if (left != null) left.toString else "."
    var r = if (right != null) right.toString else "."
    "(" + l + " " + elem.toString + " " + r + ")"
  }
}