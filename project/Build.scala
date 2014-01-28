import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "trail-analyzer"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    jdbc,
    anorm,
    "lt.overdrive" %% "track-parser" % "1.0-b659bae8183a7c5a43b4da44bc26bc6f4c55414e"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += Resolver.url("lt.overdrive", url("http://liucijus.github.io/track-parser-scala/"))(Resolver.ivyStylePatterns)
  )
}
