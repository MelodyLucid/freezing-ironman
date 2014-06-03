package miniboxing.paper

import org.apache.commons.math3.analysis.function.Max

class HackMap[K, V](initialCapacity: Int = 16, loadFactor: Float = 0.75f) {

  val maxCapacity = 1 << 30
  var threshold: Int = 0
  var size: Int = 0
  var modCount: Int = 0
  var table: Array[Entry[K,V]] = null
  
  init()
  
  def init() {
    var capacity = initialCapacity
    if (initialCapacity < 0) throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity)
    if (initialCapacity > maxCapacity) capacity = maxCapacity
    if (loadFactor <= 0 || loadFactor.isNaN) throw new IllegalArgumentException("Illegal loadFactor: " + loadFactor)
    
    // Find a power of 2 >= initialCapacity
    var cap = 1
    while (cap < capacity) {
      cap <<= 1
    }
    
    threshold = (loadFactor * capacity).toInt
    table = new Array[Entry[K,V]](cap)
  }
  
  def hash(h: Int): Int = {
    val hash = h ^ (h >>> 20) ^ (h >>> 12)
    hash ^ (h >>> 7) ^ (h >>> 4)
  }
  
  def indexFor(h: Int, capa: Int): Int = h & (capa - 1)
  
  def get(key: K): V = {
    if (key == null) {
      return getNullKey()
    }
    val h = hash(key.hashCode)
    
    var e = table(indexFor(h, table.length))
    while (e != null) {
      if (e.key == key) {
        return e.value
      }
      e = e.next
    }
    null.asInstanceOf[V]
  }
  
  def getNullKey(): V = {
    var e = table(0)
    while (e != null) {
      if (e.key == null) {
        return e.value
      }
      e = e.next
    }
    null.asInstanceOf[V]
  }
  
  def put(key: K, value: V): V = {
    if (key == null) {
      return putNullKey(value)
    }
    val h = hash(key.hashCode)
    val i = indexFor(h, table.length)
    var e = table(i)
    while (e != null) {
      if (e.hash == h && e.key == key) {
        val oldValue = e.value
        e.value = value
        return oldValue
      }
      e = e.next
    }
    modCount += 1
    addEntry(h, key, value, i)
    null.asInstanceOf[V]
  }
  
  def putNullKey(value: V): V = {
    var e = table(0)
    while (e != null) {
      if (e.key == null) {
        val oldValue = e.value
        e.value = value
        return oldValue
      }
      e = e.next
    }
    modCount += 1
    addEntry(0, null.asInstanceOf[K], value, 0)
    null.asInstanceOf[V]
  }
  
  def addEntry(h: Int, key: K, value: V, bucketIndex: Int) {
    val e = table(bucketIndex)
    table(bucketIndex) = new Entry[K, V](key, value, e, h)
    size += 1
    if (size >= threshold) {
      resize(2 * table.length)
    }
  }
  
  def resize(newCapacity: Int) {
    val oldCapacity = table.length
    if (table.length == Integer.MAX_VALUE) {
      threshold = Integer.MAX_VALUE
    }
    else {
      var newTable = new Array[Entry[K,V]](newCapacity)
      transfer(newTable)
      table = newTable
      threshold = (newCapacity * loadFactor).toInt
    }
  }
  
  def transfer(newTable: Array[Entry[K,V]]) {
    var src = table
    val newCapacity = newTable.length
    for (j <- 0 until src.length) {
      var e = src(j)
      if (e != null) {
        src(j) = null
        do {
          var next = e.next
          val i = indexFor(e.hash, newCapacity)
          e.next = newTable(i)
          newTable(i) = e
          e = next
        } while (e != null)
      }
    }
  }
}


/**
 * Simply linked list of entries K -> V
 */
class Entry[K, V](val key: K, var value: V, var next: Entry[K, V], val hash: Int)