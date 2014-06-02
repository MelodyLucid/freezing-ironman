package miniboxing.paper

object BenchmarkTest extends App {

//  val xx = List(1.2, 2.3, 3.0, 3.8, 4.7, 5.9)
//  val yy = List(1.1, 2.1, 3.1, 4.0, 4.9, 5.9)
  val xx = List(0,100000,200000,300000,400000,500000)
  val yy = List(0,40.29115,85.81614,139.79412,200.15144,308.4586)
  val points = xx zip yy
  
  val size: Double = xx.size
  
  val sumx: Double = xx.sum
  val sumy: Double = yy.sum
  
  println("sum x = " + sumx)
  println("sum y = " + sumy)
  
  val squarex: Double = (xx.map(x => x*x)).sum
  
  println("square x = " + squarex)
  
  val sumxy: Double = points.map(tuple => tuple._1 * tuple._2).sum
  
  println("sumxy = " + sumxy)
  
  val m = (size*sumxy - sumx*sumy) / (size*squarex - sumx*sumx)
  val b = (sumy*squarex - sumx*sumxy) / (size*squarex - sumx*sumx)
  
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