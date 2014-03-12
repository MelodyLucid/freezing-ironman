package miniboxing.example.triple

class SpecializedTriple[@specialized T, @specialized R, @specialized S] (private val t: T, private val r: R, private val s: S) {
  def getT: T = t
  def getR: R = r
  def getS: S = s
}