package miniboxing.test.infrastructure

import org.scalameter.CurveData
import org.scalameter.api._
import org.scalameter.Key
import org.scalameter.utils.Tree
import org.scalameter.execution.LocalExecutor

trait FreezingTest extends PerformanceTest {


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

  @transient lazy val executor = LocalExecutor.apply( //SeparateJvmsExecutor(
    Executor.Warmer.Default(),
    Aggregator.complete(Aggregator.average),
    new Executor.Measurer.Default
  )

  def persistor = Persistor.None
}
