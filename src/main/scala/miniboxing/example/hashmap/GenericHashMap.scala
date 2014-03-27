package miniboxing.example.hashmap

class GenericHashMap[K, V] {

  val size = 32
  private var values = new Array[GenericLinkedList[K, V]](size)
  
  def put(key: K, value: V): V = {
    if (values(key.hashCode % size) == null) {
      values(key.hashCode % size) = new GenericLinkedList[K, V]
    }
    
    values(key.hashCode % size).add(key, value)
  }
  
  def get(key: K): V = if (values(key.hashCode % size) != null) values(key.hashCode % size).get(key) else null.asInstanceOf[V]
  
  def remove(key: K): V = if (values(key.hashCode % size) != null) values(key.hashCode % size).remove(key) else null.asInstanceOf[V] 
  
  // apply f to each value
  def map[T](f: V => T): GenericHashMap[K, T] = ???
  
  def fold[T](f: V => T): T = ???
}

class GenericLinkedList[K, V] {

  var head: GenericNode[Any, Any] = null
  var tail: GenericNode[Any, Any] = null
  
  def add(key: K, value: V): V = {
    var node = new GenericNode[Any, Any](key, value)
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
    def get0(node: GenericNode[K, V], key: K): V = {
      if (node == null) null.asInstanceOf[V]
      else if (node.key.equals(key)) node.value
      else get0(node.next, key)
    }
    
    get0(head.asInstanceOf[GenericNode[K, V]], key)
  }
  
  def remove(key: K): V = {
    def remove0(previous: GenericNode[K, V], current: GenericNode[K, V], key: K): V = {
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
      remove0(head.asInstanceOf[GenericNode[K, V]], head.next.asInstanceOf[GenericNode[K, V]], key)
    }
  }
}

class GenericNode[K, V](var key: K, var value: V) {

  var next: GenericNode[K, V] = null
}