package miniboxing.example.box

class GenericBox[T](private val boxed: T) {
  def retrieve: T = boxed
}

class MutableGenericBox[T](private var boxed: T) {
  def add(elementToBox: T): T = { boxed = elementToBox; boxed }
}