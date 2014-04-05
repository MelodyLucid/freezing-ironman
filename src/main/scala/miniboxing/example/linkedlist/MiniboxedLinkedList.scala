package miniboxing.example.linkedlist

class MiniboxedLinkedList[@miniboxed K, @miniboxed V] {

  var head: MiniboxedNode[Any, Any] = null
  var tail: MiniboxedNode[Any, Any] = null
  
  def add(key: K, value: V): V = {
    var node = new MiniboxedNode[Any, Any](key, value)
    if (head == null) {
      head = node
      tail = node
    } else if (head == tail) {
      head.next = node
      tail = node
    } else {
      tail.next = node
      tail = node
    }
    value
  }
  
  def get(key: K): V = {
    def get0(node: MiniboxedNode[K, V], key: K): V = {
      if (node == null) null.asInstanceOf[V]
      else if (node.key.equals(key)) node.value
      else get0(node.next, key)
    }
    
    get0(head.asInstanceOf[MiniboxedNode[K, V]], key)
  }
  
  def remove(key: K): V = {
    def remove0(previous: MiniboxedNode[K, V], current: MiniboxedNode[K, V], key: K): V = {
      if (current == null) null.asInstanceOf[V]
      else if (current.key.equals(key)) {
        previous.next = current.next
        current.value
      } else remove0(current, current.next, key)
    }
    
    if (head.key.equals(key)) {
      val value = (head.value).asInstanceOf[V]
      head = head.next
      value
    } else {
      remove0(head.asInstanceOf[MiniboxedNode[K, V]], head.next.asInstanceOf[MiniboxedNode[K, V]], key)
    }
  }
}

class MiniboxedNode[@miniboxed K, @miniboxed V](var key: K, var value: V) {

  var next: MiniboxedNode[K, V] = null
}