package miniboxing.example.linkedlist

class GenericLinkedList[T](implicit m: Manifest[T]) {

  private var head, tail: GenericNode[T] = null

  def add(t: T) {
    val node = new GenericNode(t, null)
    if (tail == null) {
      head = node
    } else {
      tail.next = node
    }
    tail = node
  }

  def add(t: T, i: Int) {
    if (i < 0) {
      throw new NoSuchElementException
    }
    val node = new GenericNode(t, null)
    if (i == 0) {
      if (tail == null) tail = node
      node.next = head
      head = node
    } else {
      var c = i
      var m = head
      while (c > 1 && m != null) {
        c -= 1
        m = m.next
      }
      if (m == null) {
        throw new NoSuchElementException
      }
      if (m == tail) {
        tail = node
      }
      node.next = m.next
      m.next = node
    }
  }

  def get(i: Int): T = {
    if (i < 0) {
      throw new NoSuchElementException
    }
    var c = i
    var n = head
    while (c > 0 && n != null) {
      c -= 1
      n = n.next
    }
    if (n == null) {
      throw new NoSuchElementException
    }
    n.elem
  }

  def remove(i: Int) {
    if (head == null || i < 0) {
      throw new NoSuchElementException
    }
    if (i == 0) {
      if (tail == head) {
        tail == null
      }
      head = head.next
    } else {
      var c = i
      var n = head
      while (c > 1 && n.next != null) {
        c -= 1
        n = n.next
      }
      if (n == null || n.next == null) {
        throw new NoSuchElementException
      }
      if (tail == n.next) tail = n
      n.next = n.next.next
    }
  }

  def size: Int = {
    if (head == null) {
      0
    } else {
      var c = 1
      var n = head
      while (n != tail) {
        c += 1
        n = n.next
      }
      c
    }
  }

  def clear() {
    head = null
    tail = null
  }

  def toArray: Array[T] = {
    val a = new Array[T](size)
    var i = 0
    var n = head
    while (i < a.length) {
      a(i) = n.elem
      i += 1
      n = n.next
    }
    a
  }

  override def toString: String = toArray.mkString(" ")

}

class GenericNode[T](var elem: T, var next: GenericNode[T])