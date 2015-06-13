import sbt._
import Keys._

object InfluxBuild extends Build {

  val org = "org.influxdb"

  // the core API is compatible with scala 2.10+ and only depends on slf4j
  lazy val core = Project("core", file("core")) settings(
      name := "influxdb-scala-core",
      organization := org,
      version := "0.6-SNAPSHOT",
      scalaVersion := "2.11.6",
      libraryDependencies ++= Seq(
        "org.slf4j" % "slf4j-api" % "1.7.7"

      )
  )

  // the standalone extension of the core API has implementations of HTTPService and JsonConverter
  // that use async-http-client and json4s. In a Play! framework application, you may want to provide
  // different implementations based on WS and the json macros available in play to avoid these redundant
  // dependencies
  lazy val standalone = Project("standalone", file("standalone")) dependsOn(core) settings(
      name := "influxdb-scala-standalone",
      organization := org,
      version := "0.6-SNAPSHOT",
      scalaVersion := "2.11.6",
      mainClass := Some("TestApp"),
      libraryDependencies ++= Seq(
    		  "org.json4s" %% "json4s-native" % "3.2.11",
    		  "com.ning" % "async-http-client" % "1.7.19",
          "org.scalatest" %% "scalatest" % "2.2.4" % "test",
          "org.mockito" % "mockito-core" % "1.9.5" % "test"
      )
  )
}
