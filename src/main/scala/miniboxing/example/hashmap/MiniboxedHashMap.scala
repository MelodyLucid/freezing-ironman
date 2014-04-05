package miniboxing.example.hashmap

class MiniboxedHashMap[@miniboxed K, @miniboxed V](implicit keyManifest: Manifest[K], valueManifest: Manifest[V]) {

  val size = 128
  private var values = new Array[V](size)
  private var keys = new Array[K](size)
  private var count = new Array[Int](size)
  
  /*
   *  Challenge issue63
   *  HashMap (on the map function, define MyFunction[@miniboxed -T,@miniboxed +S], then abstract it, and concretize it with an apply method, it would be called magically x(4) (see email))
   *  CanBuildFrom pattern
   */
  
  def put(key: K, value: V): V = {
    val keyHashCode = key.hashCode % size
    
    def recursivePut(index: Int): V = {
      if (index == keyHashCode - 1) {
        throw new Error("HashMap is full.")
      }
      if (count(index) == 0) {
        keys(index) = key
        values(index) = value
        count(index) = 1
        value
      } else {
        count(index) += 1
        recursivePut((index + 1) % size)
      }
    }
    
    recursivePut(keyHashCode)
  }
  
  def get(key: K): V = {
    val keyHashCode = key.hashCode % size
    
    def recursiveGet(index: Int): V = {
      if (index == keyHashCode - 1) {
        null.asInstanceOf[V]
      } else if (keys(index) == key) {
        values(index)
      } else {
        recursiveGet((index + 1) % size)
      }
    }
    
    if (contains(key)) {
      recursiveGet(keyHashCode)
    } else {
      null.asInstanceOf[V]
    }
  }
  
  def contains(key: K): Boolean = {
    val keyHashCode = key.hashCode % size
    
    def recursiveContains(index: Int): Boolean = {
      if (index == keyHashCode - 1) {
        false
      } else if (count(key.hashCode % size) == 0) {
        false
      } else if (keys(index) == key) {
        true
      } else recursiveContains((index + 1) % size)
    }
    
    recursiveContains(keyHashCode)
  }
  
  def remove(key: K): V = {
    val keyHashCode = key.hashCode % size
    
    def recursiveRemove(index: Int): (Boolean, V) = {
      if (index == keyHashCode - 1) {
        (false, null.asInstanceOf[V])
      } else if (keys(index) == key) {
        val value = values(index)
        count(index) -= 1
        values(index) = null.asInstanceOf[V] // unnecessary, avoid garbage
        keys(index) = null.asInstanceOf[K] // unnecessary, avoid garbage
        (true, value)
      } else {
        val (isRemoved, value) = recursiveRemove((index + 1) % size)
        if (isRemoved) { 
          count(index) -= 1
        }
        (isRemoved, value)
      }
    }
    
    if (contains(key)) {
      val (isRemoved, value) = recursiveRemove(keyHashCode)
      value
    } else {
      null.asInstanceOf[V]
    }
  }
  
  // apply f to each value
  def map[T](f: MiniboxedFunction[V, T]): MiniboxedHashMap[K, T] = ???
  
  def fold[T](f: MiniboxedFunction[V, T]): T = ???
}

// still unused

abstract class MiniboxedFunction[@miniboxed -T, @miniboxed +S] {
  def apply(t: T): S
}

// using these make the compiler crashes
abstract class MiniboxedOption[@miniboxed +T]

case class MiniboxedSome[@miniboxed +T](t: T) extends MiniboxedOption[T]
case object MiniboxedNone extends MiniboxedOption[Nothing]