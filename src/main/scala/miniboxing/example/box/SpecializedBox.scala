package miniboxing.example.box

class SpecializedBox[@specialized T](private val boxed: T) {
  def retrieve: T = boxed
}

class MutableSpecializedBox[@specialized T](private var boxed: T) {
  def add(elementToBox: T): T = { boxed = elementToBox; boxed }
}