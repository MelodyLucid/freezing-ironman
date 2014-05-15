package miniboxing.paper

trait Iterator[@miniboxed +T] {
  def hasNext(): Boolean
  def next(): T
}