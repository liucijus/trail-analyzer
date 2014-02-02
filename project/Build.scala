import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "trail-analyzer"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    jdbc,
    anorm,
    "lt.overdrive" %% "track-parser" % "1.0-+"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += Resolver.file("local", file(System.getProperty("user.home") + "/.ivy2/local"))(Resolver.ivyStylePatterns),
    resolvers += Resolver.url("lt.overdrive", url("http://liucijus.github.io/track-parser-scala/"))(Resolver.ivyStylePatterns)
  )
}
