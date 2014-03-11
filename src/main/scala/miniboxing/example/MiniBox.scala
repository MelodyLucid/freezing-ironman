package miniboxing.example

class MiniBox[@miniboxed T](private val boxed: T) {
  def retrieve: T = boxed
}

class MutableMiniBox[@miniboxed T](private var boxed: T) {
  def add(elementToBox: T): T = { boxed = elementToBox; boxed }
}