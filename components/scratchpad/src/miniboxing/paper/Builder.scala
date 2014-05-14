package miniboxing.paper

trait Builder[-T, +To] {

  def +=(e1: T): Unit
  def finalise: To
}

class ListBuilder[T] extends Builder[T, List[T]] {

  private var head: List[T] = Nil
  
  def +=(e1: T): Unit = {
    if (head == Nil) head = new Cons(e1, Nil)
    else head = new Cons(e1, head)
  }
  
  def finalise: List[T] = head
}