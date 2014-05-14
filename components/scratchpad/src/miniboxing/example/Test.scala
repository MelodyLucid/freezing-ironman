package miniboxing.example

case class MyTuple2[@miniboxed T1, @miniboxed T2](t1: T1, t2: T2)

class C[@miniboxed T](t: T) {
  def foo = t
}

class B[@miniboxed S](s: S) {
  def foo[@miniboxed T](t: T) = new C[S](s).foo
}

object Test {
  def main(args: Array[String]): Unit = {
    println("MyTuple of [Double, Double] dot getClass(): " + new MyTuple2(1.1, 1.1).getClass())
    println("MyTuple of [Double, String] dot getClass(): " + new MyTuple2(1.1, "1").getClass())
    println("MyTuple of [String, Double] dot getClass(): " + new MyTuple2("1", 1.1).getClass())
    println("MyTuple of [String, String] dot getClass(): " + new MyTuple2("1", "1").getClass())
    
  }
  
  def foo[@miniboxed T](t: T) = new C[T](t).foo
}
