package miniboxing.paper

trait Builder[@miniboxed -T, +To] {

  def +=(e1: T): Unit
  def finalise: To
}

class ListBuilder[@miniboxed T] extends Builder[T, List[T]] {

  private var head: List[T] = Nil
  
  def +=(e1: T): Unit = {
    if (head == Nil) head = new ::(e1, Nil)
    else head = new ::(e1, head)
  }
  
  def finalise: List[T] = head.reverse
}