package miniboxing.example.hashmap

class MiniboxedHashMap[@miniboxed K, @miniboxed V] {

  val size = 32
  private var values = new Array[MiniboxedLinkedList[K, V]](size)
  
  /*
   *  Challenge issue63
   *  HashMap (on the map function, define MyFunction[@miniboxed -T,@miniboxed +S], then abstract it, and concretize it with an apply method, it would be called magically x(4) (see email))
   *  CanBuildFrom pattern
   */
  
  def put(key: K, value: V): Option[V] = {
    if (values(key.hashCode % size) == null) {
      values(key.hashCode % size) = new MiniboxedLinkedList[K, V]
    }
    
    values(key.hashCode % size).add(key, value)
  }
  
  def get(key: K): Option[V] = if (values(key.hashCode % size) != null) values(key.hashCode % size).get(key) else None
  
  def remove(key: K): Option[V] = if (values(key.hashCode % size) != null) values(key.hashCode % size).remove(key) else None 
  
  // apply f to each value
  def map[T](f: MiniboxedFunction[V, T]): MiniboxedHashMap[K, T] = ???
  
  def fold[T](f: MiniboxedFunction[V, T]): T = ???
}

class MiniboxedLinkedList[@miniboxed K, @miniboxed V] {

  var head: MiniboxedNode[Any, Any] = null
  var tail: MiniboxedNode[Any, Any] = null
  
  def add(key: K, value: V): Option[V] = {
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
    Some(value)
  }
  
  def get(key: K): Option[V] = {
    def get0(node: MiniboxedNode[K, V], key: K): Option[V] = {
      if (node == null) None
      else if (node.key.equals(key)) Some(node.value)
      else get0(node.next, key)
    }
    
    get0(head.asInstanceOf[MiniboxedNode[K, V]], key)
  }
  
  def remove(key: K): Option[V] = {
    def remove0(previous: MiniboxedNode[K, V], current: MiniboxedNode[K, V], key: K): Option[V] = {
      if (current == null) None
      else if (current.key.equals(key)) {
        previous.next = current.next
        Some(current.value)
      } else remove0(current, current.next, key)
    }
    
    if (head.key.equals(key)) {
      val value = (head.value).asInstanceOf[V]
      head = head.next
      Some(value)
    } else {
      remove0(head.asInstanceOf[MiniboxedNode[K, V]], head.next.asInstanceOf[MiniboxedNode[K, V]], key)
    }
  }
}

class MiniboxedNode[@miniboxed K, @miniboxed V](var key: K, var value: V) {

  var next: MiniboxedNode[K, V] = null
}

abstract class MiniboxedFunction[@miniboxed -T, @miniboxed +S] {
  def apply(t: T): S
}