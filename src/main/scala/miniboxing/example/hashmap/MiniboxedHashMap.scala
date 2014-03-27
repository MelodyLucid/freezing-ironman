package miniboxing.example.hashmap

class MiniboxedHashMap[@miniboxed K, @miniboxed V] {

  val size = 32
  private var values = new Array[MiniboxedLinkedList[K, V]](size)
  
  /*
   *  Challenge issue63
   *  HashMap (on the map function, define MyFunction[@miniboxed -T,@miniboxed +S], then abstract it, and concretize it with an apply method, it would be called magically x(4) (see email))
   *  CanBuildFrom pattern
   */
  
  def put(key: K, value: V): V = {
    if (values(key.hashCode % size) == null) {
      values(key.hashCode % size) = new MiniboxedLinkedList[K, V]
    }
    
    values(key.hashCode % size).add(key, value)
  }
  
  def get(key: K): V = if (values(key.hashCode % size) != null) values(key.hashCode % size).get(key) else null.asInstanceOf[V]
  
  def remove(key: K): V = if (values(key.hashCode % size) != null) values(key.hashCode % size).remove(key) else null.asInstanceOf[V] 
  
  // apply f to each value
  def map[T](f: MiniboxedFunction[V, T]): MiniboxedHashMap[K, T] = ???
  
  def fold[T](f: MiniboxedFunction[V, T]): T = ???
}

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

abstract class MiniboxedFunction[@miniboxed -T, @miniboxed +S] {
  def apply(t: T): S
}

// using these make the compiler crashes
abstract class MiniboxedOption[@miniboxed +T]

case class MiniboxedSome[@miniboxed +T](t: T) extends MiniboxedOption[T]
case object MiniboxedNone extends MiniboxedOption[Nothing]