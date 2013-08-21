import sbt._
import sbt.Keys._

object MyBuild extends Build {

  /** This is the main project */
  lazy val root: Project = Project(
    "miniboxing-example",
    file("."),
    settings = Defaults.defaultSettings ++ Seq[Setting[_]](
      organization := "ch.epfl.lamp",
      version := "0.0.1-SNAPSHOT",
      // The plugin usually requires the latest version of the scalac compiler.
      // You can use older compilers, but before reporting a bug, please check
      // that it can be reproduced with the latest version of the compiler. Thx
      scalaVersion := "2.10.3-SNAPSHOT"
    ) ++ miniboxingSettings
  )

  /** Settings for the miniboxing plugin */
  lazy val miniboxingSettings = Seq[Setting[_]](
    resolvers += Resolver.sonatypeRepo("snapshots"),
    libraryDependencies += "org.scala-miniboxing.plugins" %% "miniboxing-runtime" % "0.1-SNAPSHOT",
    addCompilerPlugin("org.scala-miniboxing.plugins" %% "miniboxing-plugin" % "0.1-SNAPSHOT"),
    scalacOptions ++= (
      "-P:minibox:log" ::    // enable this to see the miniboxing plugin at work
      "-P:minibox:hijack" :: // enable this to hijack @specialized annotations
      Nil
    )
  )
}
