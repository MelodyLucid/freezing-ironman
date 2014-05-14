package miniboxing.paper

abstract class List[+T] extends Iterable[T] {

  def iterator = this
  def head: T
  def tail: List[T]
  def size: Double
  
  def ::[S >: T](e1: S) : List[S]
}

case class Cons[T](var _head: T, var _tail: List[T]) extends List[T] {
  var empty: Boolean = false
  
  def head = _head
  def tail = _tail
  
  def hasNext() = !empty
  def next() = {
    val head = _head
    
    if (tail == Nil) {
      empty = true
      head
    } else {
      _head = _tail.head
      _tail = _tail.tail
      
      head
    }
  }
  
  def ::[S >: T](e1: S) = new Cons[S](e1, this)
  
  def size = 1 + tail.size
}

case object Nil extends List[Nothing] {
  def head = ???
  def tail = ???
  
  def hasNext() = false
  def next() = ???
  
  def ::[S](e1: S) = ???
  
  def size = 0
}