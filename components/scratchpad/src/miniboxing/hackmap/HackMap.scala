package miniboxing.hackmap

/**
 * A HackMap is a copy of Java 7 HashMap, written in Scala, in order to compare
 * corresponding results with Generic type parameters, Specialization and
 * Miniboxing.
 *
 * Please note that the original source code and the documentation come from the
 * official Java Library.
 *
 * source: grepcode.com/file_/repository.grepcode.com/java/root/jdk/openjdk/7-b147/java/util/HashMap.java
 */


  /****************************************************************************
   ****************************************************************************
   *
   *                            Generic Version
   *
   ****************************************************************************
   ****************************************************************************/

class HackMapGeneric[K, V](initialCapacity: Int = 16, loadFactor: Float = 0.75f) extends java.io.Serializable {

  var threshold: Int = _
  var size: Int = 0
  var modCount: Int = 0
  var table: Array[GnEntry[K,V]] = _

  init()

  def init() {
    val maxCapacity = 1 << 30
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
    table = new Array[GnEntry[K,V]](cap)
  }

  /**
   * Applies a supplemental hash function to a given hashCode, which defends
   * against poor quality hash functions. This is critical because HashMap uses
   * power-of-two length hash tables, that otherwise encounter collisions for
   * hashCodes that do not differ in lower bits.
   * Note: Null keys always map to hash 0, thus index 0.
   */
  def hash(k: K): Int = {

    var h = 123 // hashSeed
    if (0 != h && k.isInstanceOf[String]) {
      return sun.misc.Hashing.stringHash32(k.asInstanceOf[String])
    }

    h = h ^ k.hashCode()

    // This function ensures that hashCodes that differ only by
    // constant multiples at each bit position have a bounded
    // number of collisions (approximately 8 at default load factor).
    h ^= (h >>> 20) ^ (h >>> 12)
    h ^ (h >>> 7) ^ (h >>> 4)
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
    val entry = getEntry(key)

    if (null == entry) null.asInstanceOf[V] else entry.value
  }

  final def getEntry(key: K): GnEntry[K, V] = {
    if (size == 0)
      return null;

    val hash2 = if (key == null) 0 else hash(key)
    var e = table(indexFor(hash2, table.length))
    while (e != null) {
      if (e.hash == hash2) {
        val k = e.key
        if (key != null && key == k)
          return e
      }
      e = e.next
    }
    null
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
    val h = hash(key)
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
    table(bucketIndex) = new GnEntry[K, V](key, value, e, h)
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
      var newTable = new Array[GnEntry[K,V]](newCapacity)
      transfer(newTable)
      table = newTable
      threshold = (newCapacity * loadFactor).toInt
    }
  }

  /**
   * Transfers all entries from current table to newTable.
   */
  def transfer(newTable: Array[GnEntry[K,V]]) {
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

/****************************************************************************
 ****************************************************************************
 *
 *                            Specialized Version
 *
 ****************************************************************************
 ****************************************************************************/

class HackMapSpecialized[@specialized K, @specialized V](initialCapacity: Int = 16, loadFactor: Float = 0.75f) extends java.io.Serializable {

  var threshold: Int = _
  var size: Int = 0
  var modCount: Int = 0
  var table: Array[SpEntry[K,V]] = _

  init()

  def init() {
    val maxCapacity = 1 << 30
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
    table = new Array[SpEntry[K,V]](cap)
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
    table(bucketIndex) = new SpEntry[K, V](key, value, e, h)
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
      var newTable = new Array[SpEntry[K,V]](newCapacity)
      transfer(newTable)
      table = newTable
      threshold = (newCapacity * loadFactor).toInt
    }
  }

  /**
   * Transfers all entries from current table to newTable.
   */
  def transfer(newTable: Array[SpEntry[K,V]]) {
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

/****************************************************************************
 ****************************************************************************
 *
 *                            Miniboxed Version
 *
 ****************************************************************************
 ****************************************************************************/

class HackMapMiniboxed[@miniboxed K, @miniboxed V](initialCapacity: Int = 16, loadFactor: Float = 0.75f) extends java.io.Serializable  {

  var threshold: Int = _
  var size: Int = 0
  var modCount: Int = 0
  var table: Array[MbEntry[K,V]] = _

  init()

  def init() {
    val maxCapacity = 1 << 30
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
    table = new Array[MbEntry[K,V]](cap)
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
    table(bucketIndex) = new MbEntry[K, V](key, value, e, h)
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
      var newTable = new Array[MbEntry[K,V]](newCapacity)
      transfer(newTable)
      table = newTable
      threshold = (newCapacity * loadFactor).toInt
    }
  }

  /**
   * Transfers all entries from current table to newTable.
   */
  def transfer(newTable: Array[MbEntry[K,V]]) {
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

class GnEntry[K, V]
    (val key: K, var value: V, var next: GnEntry[K, V], val hash: Int)
     extends java.io.Serializable

class SpEntry[@specialized K, @specialized V]
    (val key: K, var value: V, var next: SpEntry[K, V], val hash: Int)
     extends java.io.Serializable

class MbEntry[@miniboxed K, @miniboxed V]
    (val key: K, var value: V, var next: MbEntry[K, V], val hash: Int)
     extends java.io.Serializable
