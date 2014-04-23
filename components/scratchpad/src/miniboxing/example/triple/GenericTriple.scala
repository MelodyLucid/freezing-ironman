package miniboxing.example.triple

class GenericTriple[T, R, S](private val t: T, private val r: R, private val s: S) {
  def getT: T = t
  def getR: R = r
  def getS: S = s
}