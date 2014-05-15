package miniboxing.paper

trait Traversable[+T] {
  
  def mapTo[U, To](f: Function1[T, U])(b: Builder[U, To]): To = {
    val buff = b
    
    foreach(new Function1[T,Unit] { def apply(t: T): Unit = buff += f(t) })
    
    buff.finalise
  }
  
  def map[U, To](f: Function1[T, U])(b: Builder[U, To]): To = mapTo[U, To](f)(b)
  
  def sum[B >: T](implicit n : Numeric[B]): B = {
    var buff = n.zero
    
    foreach(new Function1[B,Unit] { def apply(b: B): Unit = buff = n.plus(buff,b) })
    
    buff
  }
  
  def foreach[U](f: Function1[T, U]): Unit
}
