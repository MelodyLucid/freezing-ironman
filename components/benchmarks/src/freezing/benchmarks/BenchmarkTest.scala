package freezing.benchmarks

import freezing.macros._
import org.scalameter._
import freezing.infrastructure.FreezingTest
import BenchType._

object BenchmarkTest extends FreezingTest {

  import MacroHashMap._

  benchmarkHashMap(Generic)
  benchmarkHashMap(Specialized)
  benchmarkHashMap(Miniboxed)
  
  import MacroLinkedList._
  
  benchmarkLinkedList(Generic)
  benchmarkLinkedList(Specialized)
  benchmarkLinkedList(Miniboxed)
  
//  import MacroVector3D._
//  
//  benchmarkVector3D(Generic)
//  benchmarkVector3D(Specialized)
//  benchmarkVector3D(Miniboxed)
  
//  import MacroTreeSet._
//  
//  benchmarkTreeSet(Generic)
//  benchmarkTreeSet(Specialized)
//  benchmarkTreeSet(Miniboxed)
}