import sbt._
import Keys._
import Process._

object FreezingIronmanBuild extends Build {

  val scalaVer = "2.10.4"
  val paradiseVersion = "2.0.0"

  val defaults = Defaults.defaultSettings ++ Seq(
    scalaVersion := scalaVer,
    scalaSource in Compile := baseDirectory.value / "src",
    javaSource in Compile := baseDirectory.value / "src",
    scalaSource in Test := baseDirectory.value / "test",
    javaSource in Test := baseDirectory.value / "test",
    resourceDirectory in Compile := baseDirectory.value / "resources",
    compileOrder := CompileOrder.JavaThenScala,

    unmanagedSourceDirectories in Compile := Seq((scalaSource in Compile).value),
    unmanagedSourceDirectories in Test := Seq((scalaSource in Test).value),
    //http://stackoverflow.com/questions/10472840/how-to-attach-sources-to-sbt-managed-dependencies-in-scala-ide#answer-11683728
    com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys.withSource := true,

    resolvers in ThisBuild ++= Seq(
      Resolver.sonatypeRepo("releases"),
      Resolver.sonatypeRepo("snapshots")
    ),

    libraryDependencies ++= Seq(
      // "org.scalacheck" %% "scalacheck" % "1.10.0" % "test",
      "com.novocode" % "junit-interface" % "0.10-M2" % "test"
    ),

    scalacOptions ++= Seq("-feature", "-deprecation", "-unchecked", "-Xlint"),

    publishArtifact in packageDoc := false,

    parallelExecution in Test := false,
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v"),

    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-library" % scalaVer,
      "org.scala-lang" % "scala-reflect" % scalaVer,
      "org.scala-lang" % "scala-compiler" % scalaVer,
      // "org.scala-lang" % "scala-partest" % scalaVer,
      "com.googlecode.java-diff-utils" % "diffutils" % "1.2.1"
    ),

    // macros paradise:
    addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full),
    libraryDependencies ++= (
      if (scalaVersion.value.startsWith("2.10")) List("org.scalamacros" %% "quasiquotes" % paradiseVersion)
      else Nil
    ),

    // miniboxing plugin:
    resolvers += Resolver.sonatypeRepo("snapshots"),
    libraryDependencies += "org.scala-miniboxing.plugins" %% "miniboxing-runtime" % "0.3-SNAPSHOT",
    addCompilerPlugin("org.scala-miniboxing.plugins" %% "miniboxing-plugin" % "0.3-SNAPSHOT"),
    scalacOptions ++= (
      //"-P:minibox:log" ::    // enable the miniboxing plugin output
                             // which explains what the plugin is doing
      //"-P:minibox:two-way" :: // enable even better performance
      //"-Xprint:minibox-spec" ::
      "-P:minibox:hijack" :: // enable hijacking the @specialized annotations
      //                       // transforming them into @miniboxed annotations
      // "-optimize" ::         // necessary to get the best performance when
                             // using the miniboxing plugin
      "-P:minibox:no-logo" ::
      Nil
    )
  ) ++ {
    val scalaMeter  = "com.github.axel22" %% "scalameter" % "0.5-M2"
    val scalaMeterFramework = new TestFramework("org.scalameter.ScalaMeterFramework")

    Seq(
      libraryDependencies ++= Seq(scalaMeter,
                                  "org.scalatest" %% "scalatest" % "2.1.7" % "test",
                                  "junit" % "junit" % "4.8.1" % "test"),
      testFrameworks += scalaMeterFramework,
      testOptions in ThisBuild += Tests.Argument(scalaMeterFramework, "-silent"),
      parallelExecution in Test := false
    )
  }

  lazy val _freezing   = Project(id = "freezing",            base = file(".")) aggregate (macros, benchmarks, scratchpad)
  lazy val macros      = Project(id = "freezing-macros",     base = file("components/macros"), settings = defaults)
  lazy val benchmarks  = Project(id = "freezing-benchmarks", base = file("components/benchmarks"), settings = defaults) dependsOn (macros,scratchpad)
  lazy val scratchpad  = Project(id = "freezing-scratchpad", base = file("components/scratchpad"), settings = defaults)
}
