package miniboxing.paper

trait Iterator[+T] {
  def hasNext(): Boolean
  def next(): T
}