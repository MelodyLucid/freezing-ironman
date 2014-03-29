package miniboxing.text
package infrastructure

import org.scalameter.CurveData
import org.scalameter.api._
import org.scalameter.Key
import org.scalameter.utils.Tree

trait FreezingTest extends PerformanceTest.Quickbenchmark {
  // Finally a less verbose reporter!
  override def reporter = new LoggingReporter {
    override def report(result: CurveData, persistor: Persistor) {
      var output = ""

      for (measurement <- result.measurements)
        output += f"${result.context.scope}%30s: ${measurement.params}%-120s: ${measurement.value}% 10.5f\n"

      print(output)
    }

    override def report(result: Tree[CurveData], persistor: Persistor) = {
      true
    }
  }
}
