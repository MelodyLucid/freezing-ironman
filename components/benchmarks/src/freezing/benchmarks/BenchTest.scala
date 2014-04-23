package freezing.benchmarks
import freezing.macros._

object BenchmarkTest {
  def main(args: Array[String]): Unit = {

    import Benchmark._
    import BenchType._

    benchmark(Specialized)
  }
}
