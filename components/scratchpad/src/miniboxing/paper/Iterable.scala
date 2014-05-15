package miniboxing.paper

trait Iterable[@miniboxed +T] extends Traversable[T] {
  def iterator: Iterator[T]
  
  def zipTo[@miniboxed U, To](that: Iterable[U])(b: Builder[Tuple2[T, U], To]): To = {
    val buff = b
    val these = this.iterator
    val those = that.iterator
    
    while (these.hasNext() && those.hasNext()) {
      buff += new Tuple2[T,U](these.next(),those.next())
    }
    
    buff.finalise
  }
  
  def zip[@miniboxed U](that: Iterable[U]): List[Tuple2[T, U]] = zipTo[U, List[Tuple2[T, U]]](that)(new ListBuilder[Tuple2[T, U]])
}
