libraryDependencies += "com.github.axel22" %% "scalameter" % "0.4"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.1.0" % "test"

libraryDependencies ++= Seq(
   "junit" % "junit" % "4.8.1" % "test"
)

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

parallelExecution in Test := false
