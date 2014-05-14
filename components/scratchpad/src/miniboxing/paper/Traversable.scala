package miniboxing.paper

trait Traversable[+T] {

  def iterator: Iterable[T]
  
  def mapTo[U, To](f: Function1[T, U])(b: Builder[U, To]): To = {
    val buff = b
    val elems = iterator
    
    while (elems.hasNext()) buff += f(elems.next())
    
    buff.finalise
  }
  
  def zipTo[S >: T, U, To](that: Traversable[U])(b: Builder[Tuple2[T, U], To]): To = {
    val buff = b
    val these = this.iterator
    val those = that.iterator
    
    while (these.hasNext() && those.hasNext()) {
      buff += new Tuple2[T,U](these.next(),those.next())
    }
    
    buff.finalise
  }
  
  def map[U, To](f: Function1[T, U])(b: Builder[U, To]): To = mapTo[U, To](f)(b)
  def zip[U, To](that: Traversable[U])(b: Builder[Tuple2[T, U], To]): To = zipTo[T, U, To](that)(b)
  
  def sum[B >: T](implicit n : Numeric[B]): B = {
    val elems = iterator
    var buff = n.zero
    
    while (elems.hasNext()) buff = n.plus(buff,elems.next())
    
    buff
  }
  
  def foreach[U](f: Function1[T, U]): Unit = {
    val elems = iterator
    
    while (elems.hasNext()) f(elems.next())
  }
}
