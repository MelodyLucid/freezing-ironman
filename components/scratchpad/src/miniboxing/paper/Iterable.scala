package miniboxing.paper

trait Iterable[+T] extends Traversable[T] {
  def iterator: Iterator[T]
  
  def zipTo[U, To](that: Iterable[U])(b: Builder[Tuple2[T, U], To]): To = {
    val buff = b
    val these = this.iterator
    val those = that.iterator
    
    while (these.hasNext() && those.hasNext()) {
      buff += new Tuple2[T,U](these.next(),those.next())
    }
    
    buff.finalise
  }
  
  def zip[U, To](that: Iterable[U])(b: Builder[Tuple2[T, U], To]): To = zipTo[U, To](that)(b)
}

