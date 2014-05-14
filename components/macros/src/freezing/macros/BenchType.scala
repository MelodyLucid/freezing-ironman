package freezing.macros

object BenchType extends Enumeration {
  type BenchType = Value
  val Generic, Miniboxed, Specialized = Value
}