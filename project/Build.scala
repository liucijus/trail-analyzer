import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "trail-analyzer"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    jdbc,
    anorm
  )

  lazy val trackParserProject = Project("track-parser", file("trackparser")).settings(
    libraryDependencies ++= Seq(
      "com.google.guava" % "guava" % "15.0",
      "joda-time" % "joda-time" % "2.3",
      "org.hamcrest" % "hamcrest-all" % "1.3",
      "com.novocode" % "junit-interface" % "0.10" % "test")
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
  ).dependsOn(trackParserProject).aggregate(trackParserProject)
}
