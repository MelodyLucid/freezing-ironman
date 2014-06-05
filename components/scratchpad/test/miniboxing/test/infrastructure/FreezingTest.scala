package miniboxing.test.infrastructure

import org.scalameter.CurveData
import org.scalameter.api._
import org.scalameter.Key
import org.scalameter.utils.Tree
import org.scalameter.KeyValue
import org.scalameter.Executor.Measurer


trait FreezingTest extends PerformanceTest {

  def testSettings =
    Seq[KeyValue](
      exec.benchRuns -> 20,
      exec.minWarmupRuns -> 10,
      exec.minWarmupRuns -> 20,
      exec.independentSamples -> 2,
      exec.outliers.suspectPercent -> 50,
      exec.jvmflags -> "-Xmx4g -Xms4g  -Xss4m -XX:+CMSClassUnloadingEnabled -XX:ReservedCodeCacheSize=256m -XX:-TieredCompilation -XX:+UseNUMA"
    )

  @transient lazy val executor = SeparateJvmsExecutor(
    Executor.Warmer.Default(),
    Aggregator.average,
    new Executor.Measurer.Default
  )

  def measurer: Measurer =
    new Measurer.IgnoringGC with Measurer.OutlierElimination with Measurer.RelativeNoise

  def report(bench: String) =
    println(s"Starting $bench benchmarks. Lay back, it might take a few minutes to stabilize...")
   // HTML reporter that doesn't work right now (maybe it only generates graphs for one file?)
//  @transient lazy val reporter = Reporter.Composite(
//      new RegressionReporter(
//          RegressionReporter.Tester.OverlapIntervals(),
//          RegressionReporter.Historian.ExponentialBackoff() ),
//      HtmlReporter(true)
//  )

  // Finally a less verbose reporter!
  @transient lazy val reporter = new LoggingReporter {
    println(FreezingTest.this.getClass.getName() + ":")

    override def report(result: CurveData, persistor: Persistor) {
      for (measurement <- result.measurements)
          print(f"  ${result.context.scope}%30s: ${measurement.value}% 10.5f\n")
//        print(f"  ${result.context.scope}%30s: ${measurement.params}%-120s: ${measurement.value}% 10.5f\n")
    }

    override def report(result: Tree[CurveData], persistor: Persistor) = {
      true
    }
  }

  def persistor = Persistor.None
}
