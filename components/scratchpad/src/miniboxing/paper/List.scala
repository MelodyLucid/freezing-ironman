package miniboxing.paper

abstract class List[+T] extends Iterable[T] {

  def iterator = new Iterator[T] {
    var current = List.this
    def hasNext = current != Nil
    def next() = {
      val t = current.head
      current = current.tail
      t
    }
  }
  def foreach[U](f: Function1[T, U]) {
    val it = iterator
    while (it.hasNext) f(it.next())
  }
  
  def head: T
  def tail: List[T]
  def size: Int
  
  def ::[S >: T](e1: S) : List[S] = new ::[S](e1, this)
}

case class ::[T](head: T, tail: List[T]) extends List[T] {
  def size = 1 + tail.size
}

case object Nil extends List[Nothing] {
  def head = throw new NoSuchElementException("head of empty list")
  def tail = throw new NoSuchElementException("tail of empty list")
  
  def size = 0
}