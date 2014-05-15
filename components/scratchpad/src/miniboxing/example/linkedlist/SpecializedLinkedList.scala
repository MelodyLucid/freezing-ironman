package miniboxing.example.linkedlist

class SpecializedLinkedList[@specialized T](implicit manifest: Manifest[T]) {

  private var amount: Int = 0
  private val first: SpecializedNode[T] = new SpecializedNode[T](null.asInstanceOf[T], null)
  private var last: SpecializedNode[T] = first

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
  
//  def map[U](f: T => U)(implicit mu: Manifest[U]) = {
//    val linkedList = new SpecializedLinkedList[U]
//    var nodeInit = first
//    var nodeNew = linkedList.first
//    while (nodeInit.next != null) {
//      nodeInit = nodeInit.next
//      nodeNew.next = new SpecializedNode[U](f(nodeInit.elem), null)
//      nodeNew = nodeNew.next
//    }
//    linkedList.last = nodeNew
//    linkedList
//  }
//  
//  def filter(f: T => Boolean) = {
//    val linkedList = new SpecializedLinkedList[T]
//    var nodeInit = first
//    var nodeNew = linkedList.first
//    while (nodeInit.next != null) {
//      nodeInit = nodeInit.next
//      if (f(nodeInit.elem)) {
//        nodeNew.next = new SpecializedNode[T](nodeInit.elem, null)
//        nodeNew = nodeNew.next
//      }
//    }
//    linkedList.last = nodeNew
//    linkedList
//  }
//  
//  def fold[U](f: (U, T) => U, z: U) = {
//    var folded = z
//    var node = first
//    while (node.next != null) {
//      node = node.next
//      folded = f(folded, node.elem)
//    }
//    folded
//  }
//
//  def clear() {
//    first.next = null
//    last = first
//  }

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
  
  private def getNode(i: Int): SpecializedNode[T] = {
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
  
  private def addAfter(t: T, n: SpecializedNode[T]): Unit = {
    val node = new SpecializedNode[T](t, n.next)
    if (last eq n) {
      last = node
    }
    node.next = n.next
    n.next = node
    amount += 1
  }

}

class SpecializedNode[@specialized T](var elem: T, var next: SpecializedNode[T])