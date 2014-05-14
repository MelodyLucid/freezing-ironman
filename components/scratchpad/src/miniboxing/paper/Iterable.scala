package miniboxing.paper

trait Iterable[+T] extends Traversable[T] {

  def hasNext(): Boolean
  def next(): T
}