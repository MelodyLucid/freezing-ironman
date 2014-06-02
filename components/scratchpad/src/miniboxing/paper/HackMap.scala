package miniboxing.paper

import org.apache.commons.math3.analysis.function.Max

class HackMap[K, V](var capacity: Int = 16, var loadFactor: Float = 0.75f) {

  init()
  
  val maxCapacity = 1 << 30
  var threshold: Int = (loadFactor * capacity).toInt
  
  var table: Array[Entry[K,V]] = new Array[Entry[K,V]](capacity)
  
  def init() {
    if (capacity < 0) throw new IllegalArgumentException
    if (capacity > maxCapacity) capacity = maxCapacity
    var cap = 1
    while (cap < capacity)
      cap <<= 1
    
    capacity = cap
  }
  /**
   * Applies supplemental hash function to poor hashCode.
   */
  def hash(h: Int): Int = {
    val hash = h ^ (h >>> 20) ^ (h >>> 12)
    hash ^ (h >>> 7) ^ (h >>> 4)
  }
  
  def indexFor(h: Int): Int = h & (capacity - 1)
  
  def get(key: K): V = {
    if (key == null) {
      getNullKey()
    }
    val h = hash(key.hashCode)
    
    var entry = table(indexFor(h))
    if (entry.key == key) {
      entry.value
    }
    while (entry.hasNext) {
      entry = entry.next
      if (entry.key == key) {
        entry.value
      } 
    }
    null.asInstanceOf[V]
  }
  
  def getNullKey(): V = {
    var entry = table(0)
    if (entry.key == null) {
      entry.value
    }
    while (entry.hasNext) {
      entry = entry.next
      if (entry.key == null) {
        entry.value
      } 
    }
    null.asInstanceOf[V]
  }
  
  def put(key: K, value: V): V = {
    if (key == null) {
      putNullKey(value)
    }
    val h = hash(key.hashCode)
    var i = indexFor(h)
    var e = table(i)
    if (e.hash == h && e.key == key) {
      val oldValue = e.value
      e.value = value
      oldValue
    }
    while (e.hasNext) {
      e = e.next
      if (e.hash == h && e.key == key) {
        val oldValue = e.value
        e.value = value
        oldValue
      }
    }
    
    addEntry(h, key, value, i)
    return null.asInstanceOf[V]
  }
  
  def addEntry(h: Int, key: K, value: V, bucketIndex: Int) {
    val e = table(bucketIndex)
    table(bucketIndex) = new Entry[K, V](h, key, value, e)
    if (size++ => threshold)
      resize(2 * table.length)
  }
  
  def resize(newCapacity: Int) {
    val oldCapacity = table.length
    if (table.length == Integer.MAX_VALUE)
      threshold = Integer.MAX_VALUE
  }
}


/**
 * Simply linked list of entries K -> V
 */
class Entry[K, V](val key: K, var value: V, var next: Entry[K, V], val hash: Int) {
  
  def hasNext: Boolean = next != null
}