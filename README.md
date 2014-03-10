<img src="http://scala-miniboxing.org/mbox2-thumbnail.png" alt="Miniboxing Logo" align="right">
#Miniboxing Example - Sbt Project

This is an example of using the miniboxing plugin with your sbt project. The changes to the normal build script are located in the
[`miniboxingSettings`](https://github.com/miniboxing/miniboxing-example/blob/master/project/Build.scala#L21-34) 
value in [`project/Build.scala`](https://github.com/miniboxing/miniboxing-example/blob/master/project/Build.scala).

For more details on the miniboxing plugin and how to use it, please visit [scala-miniboxing.org](http://scala-miniboxing.org).

Example usage:
```
$ git clone https://github.com/miniboxing/miniboxing-example.git

$ cd miniboxing-example

$ cat src/main/scala/miniboxing/example/Test.scala 
package miniboxing.example

case class MyTuple2[@miniboxed T1, @miniboxed T2](t1: T1, t2: T2)

object Test {
  def main(args: Array[String]): Unit = {
    println("MyTuple of [Double, Double] dot getClass(): " + new MyTuple2(1.1, 1.1).getClass())
    println("MyTuple of [Double, String] dot getClass(): " + new MyTuple2(1.1, "1").getClass())
    println("MyTuple of [String, Double] dot getClass(): " + new MyTuple2("1", 1.1).getClass())
    println("MyTuple of [String, String] dot getClass(): " + new MyTuple2("1", "1").getClass())
  }
}

$ sbt
...
> set scalacOptions += "-P:minibox:log"
[info] Defining *:scalac-options
[info] Reapplying settings...
[info] Set current project to miniboxing-example

> compile
...
Specializing class MyTuple2...

  // interface:
  case abstract trait MyTuple2[T1, T2] extends Product with Serializable {
    def canEqual(x$1: Any): Boolean                                       
    def copy$default$1[T1, T2](): T1 @scala.annotation.unchecked.uncheckedVariance
    def copy$default$2[T1, T2](): T2 @scala.annotation.unchecked.uncheckedVariance
    def copy[T1, T2](t1: T1,t2: T2): miniboxing.example.MyTuple2[T1,T2]   
    def productArity(): Int                                               
    def productElement(x$1: Int): Any                                     
    override def equals(x$1: Any): Boolean                                
    override def hashCode(): Int                                          
    override def productIterator(): Iterator[Any]                         
    override def productPrefix(): String                                  
    override def toString(): String                                       
    val t1(): T1                                                          
    val t1_JJ(val T1_TypeTag: Byte,val T2_TypeTag: Byte): Long            
    val t1_JL(val T1_TypeTag: Byte): Long                                 
    val t1_LJ(val T2_TypeTag: Byte): T1                                   
    val t2(): T2                                                          
    val t2_JJ(val T1_TypeTag: Byte,val T2_TypeTag: Byte): Long            
    val t2_JL(val T1_TypeTag: Byte): T2                                   
    val t2_LJ(val T2_TypeTag: Byte): Long                                 
  }

  // specialized class:
  case class MyTuple2_JJ[T1sp, T2sp] extends Product with Serializable with MyTuple2[T1sp,T2sp] {
    def <init>(val miniboxing$example$MyTuple2_JJ|T1_TypeTag: Byte,val miniboxing$example$MyTuple2_JJ|T2_TypeTag: Byte,t1: Long,t2: Long): miniboxing.example.MyTuple2_JJ[T1sp,T2sp] // is a specialized implementation of constructor MyTuple2
    private[this] val miniboxing$example$MyTuple2_JJ|T1_TypeTag: Byte      // no info
    private[this] val miniboxing$example$MyTuple2_JJ|T2_TypeTag: Byte      // no info
    private[this] val t1: Long                                             // is a specialized implementation of value t1
    private[this] val t2: Long                                             // is a specialized implementation of value t2
    val t1(): T1sp                                                         // is a forwarder to value t1_JJ
    val t1_JJ(val T1_TypeTag: Byte,val T2_TypeTag: Byte): Long             // is a setter or getter for value t1
    val t1_JL(val T1_TypeTag: Byte): Long                                  // is a forwarder to value t1_JJ
    val t1_LJ(val T2_TypeTag: Byte): T1sp                                  // is a forwarder to value t1_JJ
    val t2(): T2sp                                                         // is a forwarder to value t2_JJ
    val t2_JJ(val T1_TypeTag: Byte,val T2_TypeTag: Byte): Long             // is a setter or getter for value t2
    val t2_JL(val T1_TypeTag: Byte): T2sp                                  // is a forwarder to value t2_JJ
    val t2_LJ(val T2_TypeTag: Byte): Long                                  // is a forwarder to value t2_JJ
  }

  // specialized class:
  case class MyTuple2_LJ[T1sp, T2sp] extends Product with Serializable with MyTuple2[T1sp,T2sp] {
    def <init>(val miniboxing$example$MyTuple2_LJ|T2_TypeTag: Byte,t1: T1sp,t2: Long): miniboxing.example.MyTuple2_LJ[T1sp,T2sp] // is a specialized implementation of constructor MyTuple2
    private[this] val miniboxing$example$MyTuple2_LJ|T2_TypeTag: Byte      // no info
    private[this] val t1: T1sp                                             // is a specialized implementation of value t1
    private[this] val t2: Long                                             // is a specialized implementation of value t2
    val t1(): T1sp                                                         // is a forwarder to value t1_LJ
    val t1_JJ(val T1_TypeTag: Byte,val T2_TypeTag: Byte): Long             // is a forwarder to value t1_LJ
    val t1_JL(val T1_TypeTag: Byte): Long                                  // is a forwarder to value t1_LJ
    val t1_LJ(val T2_TypeTag: Byte): T1sp                                  // is a setter or getter for value t1
    val t2(): T2sp                                                         // is a forwarder to value t2_LJ
    val t2_JJ(val T1_TypeTag: Byte,val T2_TypeTag: Byte): Long             // is a forwarder to value t2_LJ
    val t2_JL(val T1_TypeTag: Byte): T2sp                                  // is a forwarder to value t2_LJ
    val t2_LJ(val T2_TypeTag: Byte): Long                                  // is a setter or getter for value t2
  }

  // specialized class:
  case class MyTuple2_JL[T1sp, T2sp] extends Product with Serializable with MyTuple2[T1sp,T2sp] {
    def <init>(val miniboxing$example$MyTuple2_JL|T1_TypeTag: Byte,t1: Long,t2: T2sp): miniboxing.example.MyTuple2_JL[T1sp,T2sp] // is a specialized implementation of constructor MyTuple2
    private[this] val miniboxing$example$MyTuple2_JL|T1_TypeTag: Byte      // no info
    private[this] val t1: Long                                             // is a specialized implementation of value t1
    private[this] val t2: T2sp                                             // is a specialized implementation of value t2
    val t1(): T1sp                                                         // is a forwarder to value t1_JL
    val t1_JJ(val T1_TypeTag: Byte,val T2_TypeTag: Byte): Long             // is a forwarder to value t1_JL
    val t1_JL(val T1_TypeTag: Byte): Long                                  // is a setter or getter for value t1
    val t1_LJ(val T2_TypeTag: Byte): T1sp                                  // is a forwarder to value t1_JL
    val t2(): T2sp                                                         // is a forwarder to value t2_JL
    val t2_JJ(val T1_TypeTag: Byte,val T2_TypeTag: Byte): Long             // is a forwarder to value t2_JL
    val t2_JL(val T1_TypeTag: Byte): T2sp                                  // is a setter or getter for value t2
    val t2_LJ(val T2_TypeTag: Byte): Long                                  // is a forwarder to value t2_JL
  }

  // specialized class:
  case class MyTuple2_LL[T1sp, T2sp] extends Product with Serializable with MyTuple2[T1sp,T2sp] {
    def <init>(t1: T1sp,t2: T2sp): miniboxing.example.MyTuple2_LL[T1sp,T2sp] // is a specialized implementation of constructor MyTuple2
    private[this] val t1: T1sp                                             // is a specialized implementation of value t1
    private[this] val t2: T2sp                                             // is a specialized implementation of value t2
    val t1(): T1sp                                                         // is a setter or getter for value t1
    val t1_JJ(val T1_TypeTag: Byte,val T2_TypeTag: Byte): Long             // is a forwarder to value t1
    val t1_JL(val T1_TypeTag: Byte): Long                                  // is a forwarder to value t1
    val t1_LJ(val T2_TypeTag: Byte): T1sp                                  // is a forwarder to value t1
    val t2(): T2sp                                                         // is a setter or getter for value t2
    val t2_JJ(val T1_TypeTag: Byte,val T2_TypeTag: Byte): Long             // is a forwarder to value t2
    val t2_JL(val T1_TypeTag: Byte): T2sp                                  // is a forwarder to value t2
    val t2_LJ(val T2_TypeTag: Byte): Long                                  // is a forwarder to value t2
  }




[success] Total time: 8 s, completed Aug 21, 2013 3:59:15 AM
> run
[info] Running miniboxing.example.Test 
MyTuple of [Double, Double] dot getClass(): class miniboxing.example.MyTuple2_JJ
MyTuple of [Double, String] dot getClass(): class miniboxing.example.MyTuple2_JL
MyTuple of [String, Double] dot getClass(): class miniboxing.example.MyTuple2_LJ
MyTuple of [String, String] dot getClass(): class miniboxing.example.MyTuple2_LL
[success] Total time: 0 s, completed Aug 21, 2013 3:59:16 AM
> 
```


#### Try miniboxing on your own! [Read more](http://scala-miniboxing.org) or [use miniboxing with your project](https://github.com/miniboxing/miniboxing-example).
Or have a look at an [array reverse example](https://gist.github.com/VladUreche/6891789).
