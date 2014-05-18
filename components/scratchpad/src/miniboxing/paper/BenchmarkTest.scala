package miniboxing.paper

object BenchmarkTest extends App {

//  val xx = List(1.2, 2.3, 3.0, 3.8, 4.7, 5.9)
//  val yy = List(1.1, 2.1, 3.1, 4.0, 4.9, 5.9)
  val xx = List(8,2,11,6,5,4,12,9,6,1)
  val yy = List(3,10,3,6,8,12,1,4,9,14)
  val points = xx zip yy
  
  val size = xx.size
  
  val sumx = xx.sum
  val sumy = yy.sum
  
  println("sum x = " + sumx)
  println("sum y = " + sumy)
  
  val squarex = (xx.map(x => x*x)).sum
  
  println("square x = " + squarex)
  
  val sumxy = points.map(tuple => tuple._1 * tuple._2).sum
  
  println("sumxy = " + sumxy)
  
  val m = (size*sumxy - sumx*sumy) / (size*squarex - sumx*sumx)
  
  val b = (sumy*squarex - sumx*sumxy)/(size*squarex - sumx*sumx)
  
  println("M = " + m + " b = " + b)
}

object BenchiTest extends App {
  
  implicit object Num_I extends Numeric[Double] {
    def plus(x: Double, y: Double): Double = x + y
    
    def zero: Double = 0.0
  }
  
  val xx = 17.0 :: 1.0 :: 2.0 :: 3.0 :: 4.0 :: 5.0 :: Nil
    
  val yy = 1 :: 2 :: 3 :: Nil
  val zz = "Hello" :: "World" :: "!" :: Nil
  
  val zip = yy.zip(zz)
  
  val ww = 1 :: 2 :: 3 :: Nil
  
  val func = new Function1[Int, Double] { 
    def apply(t: Int): Double = t + 1.0
  }
  
  val map = ww.map(func)
  
  println("(" + xx + ") summed = (" + xx.sum + ")")
  println("(" + yy + ") zip " + zz + " = " + zip + ")")
  println("(" + ww + ") mapped (t => t + 1) = " + map + ")")
}