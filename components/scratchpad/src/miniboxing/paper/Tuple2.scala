package miniboxing.paper

case class Tuple2[@miniboxed +T1, @miniboxed +T2](_1: T1, _2: T2) {

  override def toString() = "(" + _1.toString + "," + _2.toString + ")"
}