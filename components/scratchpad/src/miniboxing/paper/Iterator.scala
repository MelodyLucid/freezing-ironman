package miniboxing.paper

trait Iterator[T] {

  def next(): T
  def hasNext(): Boolean
}