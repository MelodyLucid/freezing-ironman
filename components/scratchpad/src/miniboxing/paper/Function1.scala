package miniboxing.paper

trait Function1[@miniboxed -T, @miniboxed +S] {
  def apply(t: T): S
}