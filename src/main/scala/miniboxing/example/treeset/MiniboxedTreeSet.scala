package miniboxing.example.treeset

class MiniboxedTreeSet[E] {

  private var amount: Int = 0
  private var root: MiniboxedTreeNode[E] = null

  def size: Int = amount
  
  def add(e: E): Unit = {
    if (root == null) {
      root = new MiniboxedTreeNode[E](e, null, null)
      amount += 1
    } else {
        recursiveAdd(root)
    }
    
    def recursiveAdd(node: MiniboxedTreeNode[E]): Unit = {
      if (e.hashCode < node.elem.hashCode) {
        if (node.left == null) {
          node.left = new MiniboxedTreeNode[E](e, null, null)
          amount += 1
        } else  {
          recursiveAdd(node.left)
        }
      } else if (e.hashCode > node.elem.hashCode) {
        if (node.right == null) {
          node.right = new MiniboxedTreeNode[E](e, null, null)
          amount += 1
        } else {
          recursiveAdd(node.right)
        }
      } // else it's already there
    }
  }
  
  def remove(e: E): Unit = {
    if (root != null) {
      recursiveRemove(root, e)
    }
    
    def recursiveRemove(node: MiniboxedTreeNode[E], elem: E): Unit = {
      if (elem == e) node.elem = replacingElem(node.right)
    }
    
    def replacingElem(node: MiniboxedTreeNode[E]): E = {
      if (node.left != null) {
        replacingElem(node.left)
      }
      else {
        val result: E = node.left.elem
        node.left = null
        result
      }
    }
  }
  
  def contains(e: E): Boolean = {
    def recursiveContains(node: MiniboxedTreeNode[E]): Boolean = {
      if (node.elem == e) true
      else if (node.left != null && recursiveContains(node.left)) true
      else if (node.right != null && recursiveContains(node.right)) true
      else false
    }
    
    if (root == null) false
    else recursiveContains(root)
  }
}

class MiniboxedTreeNode[E](var elem: E, var left: MiniboxedTreeNode[E], var right: MiniboxedTreeNode[E]) {

  def contains(elem: E): Boolean = this.elem == elem
}