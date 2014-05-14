package miniboxing.paper

trait Function1[-T, +S] {
  def apply(t: T): S
}