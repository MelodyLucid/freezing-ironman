package miniboxing.test.paper

import miniboxing.paper._
import java.util.Random
import org.scalameter.api._
import miniboxing.test.infrastructure.FreezingTest

object LeastSquaresBenchmark extends FreezingTest {

  val random = new Random(0)
  
  // Random between (-1.0, 1.0), mean = 0
  def rand = random.nextDouble - random.nextDouble
  
  // Function to approximate = 5x + 3
  val step = 5.0
  val zero = 3.0
  val func = new Function1[Int, Double] {
    def apply(x: Int): Double = step*x + zero
  }
  
  val sizes = Gen.range("size")(30000, 150000, 30000)
  
  implicit object Num_D extends Numeric[Double] {
    def plus(x: Double, y: Double): Double = x + y
    
    def zero: Double = 0.0
  }
  
  // Method approximates func with the Least Squares Method
  //   approximation function = m*x + b
  performance of "List[Double]" in {
    measure method "Least Squares Method" in {
      using(sizes) in {
        size => 
          // generates random points from original function
          var listx: List[Double] = Nil
          var listy: List[Double] = Nil
          var i = 0
          while (i < size) {
            listx = (i + rand) :: listx
            listy = (func(i) + rand) :: listy
            i += 1
          }
          
          val listxy = listx.zip(listy)
          
          val sumx  = listx.sum
          val sumy  = listy.sum
          
          // function (x, y) => x * y
          val fxy = new Function1[Tuple2[Double,Double], Double] {
            def apply(t: Tuple2[Double, Double]): Double = t._1 * t._2
          }
          val sumxy = listxy.map(fxy).sum
          
          // function x => x * x
          val fxx = new Function1[Double, Double] {
            def apply(x: Double): Double = x * x
          }
          val squarex = listx.map(fxx).sum
          
          val m = (size*sumxy - sumx*sumy) / (size*squarex - sumx*sumx)
          val b = (sumy*squarex - sumx*sumxy) / (size*squarex - sumx*sumx)
          
          // was it a good approximation?
          assert(m - step < 0.1, "m exceeded 10% of error : " + m + " instead of " + step)
          assert(b - zero < 0.1, "b exceeded 10% of error : " + b + " instead of " + zero)
      }
    }
  }
}