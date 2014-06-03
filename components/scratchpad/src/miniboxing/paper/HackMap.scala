package miniboxing.paper

import org.apache.commons.math3.analysis.function.Max

/**
 * HackMap is a copy of Java 7 HashMap, written in Scala, in order to compare
 * corresponding results with Miniboxing.
 * 
 * Please note that the original source code and the documentation come from the
 * official Java Library.
 * 
 * source: grepcode.com/file_/repository.grepcode.com/java/root/jdk/openjdk/7-b147/java/util/HashMap.java
 */

class HackMap[K, V](initialCapacity: Int = 16, loadFactor: Float = 0.75f) {

  val maxCapacity = 1 << 30
  var threshold: Int = 0
  var size: Int = 0
  var modCount: Int = 0
  var table: Array[Entry[K,V]] = null
  
  init()
  
  def init() {
    var capacity = initialCapacity
    if (initialCapacity < 0) {
      throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity)
    }
    if (initialCapacity > maxCapacity) {
      capacity = maxCapacity
    }
    if (loadFactor <= 0 || loadFactor.isNaN) {
      throw new IllegalArgumentException("Illegal loadFactor: " + loadFactor)
    }
    
    // Find a power of 2 >= initialCapacity
    var cap = 1
    while (cap < capacity) {
      cap <<= 1
    }
    
    threshold = (loadFactor * capacity).toInt
    table = new Array[Entry[K,V]](cap)
  }
  
  /**
   * Applies a supplemental hash function to a given hashCode, which defends
   * against poor quality hash functions. This is critical because HashMap uses
   * power-of-two length hash tables, that otherwise encounter collisions for
   * hashCodes that do not differ in lower bits.
   * Note: Null keys always map to hash 0, thus index 0.
   */
  def hash(h: Int): Int = {
    // This function ensures that hashCodes that differ only by constant multiples
    // at each bit position have a bounded number of collisions
    // (approximately 8 at default load factor).
    val hash = h ^ (h >>> 20) ^ (h >>> 12)
    hash ^ (h >>> 7) ^ (h >>> 4)
  }
  
  /**
   * Returns index for hash code h.
   */
  def indexFor(h: Int, capa: Int): Int = h & (capa - 1)
  
  /**
   * Returns the value to which the specified key is mapped, or null if this map
   * contains no mapping for the key.
   *
   * More formally, if this map contains a mapping from a key k to a value v such
   * that (key==null ? k==null : key.equals(k)), then this method returns v;
   * otherwise it returns {@code null}.  (There can be at most one such mapping.)
   *
   * A return value of null does not necessarily indicate that the map contains
   * no mapping for the key; it's also possible that the map explicitly maps the
   * key to null.
   */
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
  
  /**
   * Offloaded version of get() to look up null keys.  Null keys map to index 0.
   * This null case is split out into separate methods for the sake of
   * performance in the two most commonly used operations (get and put), but
   * incorporated with conditionals in others.
   */
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
  
  /**
   * Associates the specified value with the specified key in this map. If the
   * map previously contained a mapping for the key, the old value is replaced.
   */
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
  
  /**
   * Offloaded version of put for null keys
   */
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
  
  /**
   * Adds a new entry with the specified key, value and hash code to the
   * specified bucket.  It is the responsibility of this method to resize
   * the table if appropriate.
   */
  def addEntry(h: Int, key: K, value: V, bucketIndex: Int) {
    val e = table(bucketIndex)
    table(bucketIndex) = new Entry[K, V](key, value, e, h)
    size += 1
    if (size >= threshold) {
      resize(2 * table.length)
    }
  }
  
  /**
   * Rehashes the contents of this map into a new array with a larger capacity.
   * This method is called automatically when the number of keys in this map
   * reaches its threshold.
   *
   * If current capacity is MAXIMUM_CAPACITY, this method does not resize the
   * map, but sets threshold to Integer.MAX_VALUE. This has the effect of
   * preventing future calls.
   */
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
  
  /**
   * Transfers all entries from current table to newTable.
   */
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
 * This is a simply linked list of entries K -> V, that allows to store 
 * (key, value) pairs in the HashMap.
 */

class Entry[K, V](val key: K, var value: V, var next: Entry[K, V], val hash: Int)