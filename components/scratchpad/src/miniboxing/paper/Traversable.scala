package miniboxing.paper

trait Traversable[@miniboxed +T] {
  
  def mapTo[@miniboxed U, To](f: Function1[T, U])(b: Builder[U, To]): To = {
    val buff = b
    
    foreach(new Function1[T,Unit] { def apply(t: T): Unit = buff += f(t) })
    
    buff.finalise
  }
  
  def map[@miniboxed U](f: Function1[T, U]): List[U] = mapTo[U, List[U]](f)(new ListBuilder)
  
  def sum[B >: T](implicit n : Numeric[B]): B = {
    var buff = n.zero
    
    foreach(new Function1[B,Unit] { def apply(b: B): Unit = buff = n.plus(buff,b) })
    
    buff
  }
  
  def foreach[@miniboxed U](f: Function1[T, U]): Unit
}
