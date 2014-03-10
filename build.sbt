libraryDependencies += "com.github.axel22" %% "scalameter" % "0.4"

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

parallelExecution in Test := false
