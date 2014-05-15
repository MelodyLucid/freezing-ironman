package miniboxing.test.paper

import org.scalameter.api._
import miniboxing.test.infrastructure.FreezingTest
import miniboxing.paper._
import java.util.Random

object LeastSquaresBenchmark extends FreezingTest {

  val random = new Random(0)
  
  // Random between (-1.0, 1.0), mean = 0
  def rand = random.nextDouble - random.nextDouble
  
  val step = 5.0
  val zero = 3.0
  val func = new Function1[Int, Double] { def apply(x: Int): Double = step*x + zero }
  
  val sizes = Gen.range("size")(30000, 150000, 30000)
  
  implicit object Num_I extends Numeric[Double] {
    def plus(x: Double, y: Double): Double = x + y
    
    def zero: Double = 0.0
  }
  
  // Method approximates func with the Least Squares Method
  //   approximation function = m*x + b
  performance of "List[Double]" in {
    measure method "Least Squares Method" in {
     using(sizes) config (exec.benchRuns -> 1) in {
        size => 
          var listx: List[Double] = Nil
          var listy: List[Double] = Nil
          var i = 0
          while (i < size) {
            listx = (i + rand) :: listx
            listy = (func(i) + rand) :: listy
            i += 1
          }
          
          val listxy = listx.zip(listy)(new ListBuilder)
          
          val sumx  = listx.sum
          val sumy  = listy.sum
          val sumxy = listxy.map(new Function1[Tuple2[Double,Double], Double] { def apply(t: Tuple2[Double, Double]): Double = t._1 * t._2 })(new ListBuilder).sum
          
          val squarex = listx.map(new Function1[Double, Double] { def apply(x: Double): Double = x * x })(new ListBuilder).sum
          
          val m = (size*sumxy - sumx*sumy) / (size*squarex - sumx*sumx)
          val b = (sumy*squarex - sumx*sumxy)/(size*squarex - sumx*sumx)
          
          // was it a good approximation?
          assert(m - step < 0.05)
          assert(b - zero < 0.05)
      }
    }
  }
}