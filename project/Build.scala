import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "track-analyzer"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    jdbc,
    anorm
  )

  lazy val trackParserProject = RootProject(file("../track-parser"))

  val main = play.Project(appName, appVersion, appDependencies).settings(
  ).dependsOn(trackParserProject).aggregate(trackParserProject)
}
