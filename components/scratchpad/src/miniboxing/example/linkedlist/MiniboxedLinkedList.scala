package miniboxing.example.linkedlist

class MiniboxedLinkedList[@miniboxed T](implicit manifest: Manifest[T]) {

  private var amount: Int = 0
  lazy private val first: MiniboxedNode[T] = new MiniboxedNode[T](null.asInstanceOf[T], null)
  private var last: MiniboxedNode[T] = first

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
  
//  TODO solve issue + use MiniboxedFunction
//  def map[U](f: T => U)(implicit mu: Manifest[U]) = {
//    val linkedList = new MiniboxedLinkedList[U]
//    var nodeInit = first
//    var nodeNew = linkedList.first
//    while (nodeInit.next != null) {
//      nodeInit = nodeInit.next
//      nodeNew.next = new MiniboxedNode[U](f(nodeInit.elem), null)
//      nodeNew = nodeNew.next
//    }
//    linkedList.last = nodeNew
//    linkedList
//  }
//  
//  def filter(f: T => Boolean) = {
//    val linkedList = new MiniboxedLinkedList[T]
//    var nodeInit = first
//    var nodeNew = linkedList.first
//    while (nodeInit.next != null) {
//      nodeInit = nodeInit.next
//      if (f(nodeInit.elem)) {
//        nodeNew.next = new MiniboxedNode[T](nodeInit.elem, null)
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
  
  // TODO private is illegal
  def getNode(i: Int): MiniboxedNode[T] = {
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
  
  // TODO private is illegal
  def addAfter(t: T, n: MiniboxedNode[T]): Unit = {
    val node = new MiniboxedNode[T](t, n.next)
    if (last eq n) {
      last = node
    }
    node.next = n.next
    n.next = node
    amount += 1
  }

}

class MiniboxedNode[@miniboxed T](var elem: T, var next: MiniboxedNode[T])