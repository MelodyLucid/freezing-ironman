package freezing.benchmarks
import freezing.macros._

// to run this:
// in console:
//   $ sbt 'freezing-benchmarks/run-main freezing.benchmarks.BenchTest'
// on in sbt:
//   > freezing-benchmarks/run-main freezing.benchmarks.BenchTest
object BenchTest {
  def main(args: Array[String]): Unit = {

    import Benchmark._
    import BenchType._

    benchmark(Generic)
    benchmark(Specialized)
    benchmark(Miniboxed)
  }
}
