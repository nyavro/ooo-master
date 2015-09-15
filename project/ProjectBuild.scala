import com.typesafe.sbt.digest.Import._
import com.typesafe.sbt.gzip.Import._
import com.typesafe.sbt.uglify.Import._
import com.typesafe.sbt.web.Import._
import play.PlayScala
import sbt.Keys._
import sbt._

object ProjectBuild extends Build {

  val ScalaVersion = "2.11.7"
  val ScalatestVersion = "3.0.0-M7"
  val MysqlAsyncVersion = "0.2.18"

  lazy val parent = Project(
    id = "parent",
    base = file("."),
    settings = super.settings ++ sharedSettings
  )
  .settings(
    name := "OOO Master Utility"
  )
  .aggregate(ui, dbApi, dbImpl)

  lazy val dbApi = Project(
    id = "dbApi",
    base = file("dbApi"),
    settings = super.settings ++ sharedSettings
  )
  .settings(

  )

  lazy val dbImpl = Project(
    id = "dbImpl",
    base = file("dbImpl"),
    settings = super.settings ++ sharedSettings ++ Seq(
      exportJars := true
    )
  )
  .settings(
      libraryDependencies ++= Seq(
        "com.google.inject" % "guice" % "4.0",
        "javax.inject" % "javax.inject" % "1",
        "com.github.mauricio" % "mysql-async_2.11" % MysqlAsyncVersion
      ),
      mappings in (Compile, packageBin) ++= Seq(
        (baseDirectory.value / "exports.txt") -> "META-INF/services/com.google.inject.Module"
      )
  )
  .dependsOn(dbApi)

  lazy val ui = Project(
    id = "ui",
    base = file("ui"),
    settings = super.settings ++ sharedSettings
  )
  .settings(
    libraryDependencies ++= Seq(
      "com.google.inject" % "guice" % "4.0",
      "javax.inject" % "javax.inject" % "1",
      "org.webjars" % "bootstrap" % "3.3.4",
      "org.webjars" % "angularjs" % "1.3.15",
      "org.webjars" % "angular-ui-bootstrap" % "0.13.0",
      "org.webjars" % "angular-ui-router" % "0.2.15",
      "org.mockito" % "mockito-core" % "1.10.19" % "test"
    ),
    resolvers ++= Seq(
      "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
    ),
    pipelineStages := Seq(uglify, digest, gzip),
    pipelineStages in Assets := Seq(),
    pipelineStages := Seq(uglify, digest, gzip),
    DigestKeys.algorithms += "sha1",
    UglifyKeys.uglifyOps := { js =>
      Seq((js.sortBy(_._2), "concat.min.js"))
    }
  )
  .enablePlugins(PlayScala)
  .dependsOn(dbApi, dbImpl % "runtime")

  lazy val sharedSettings = super.settings ++ Seq(
    version := "1.0.0",
    scalaVersion := ScalaVersion,
    scalaBinaryVersion:= CrossVersion.binaryScalaVersion(ScalaVersion),
    autoCompilerPlugins := true,
    scalacOptions ++= Seq(
      "-language:postfixOps",
      "-language:implicitConversions",
      "-language:reflectiveCalls",
      "-language:higherKinds",
      "-language:existentials",
      "-Yinline-warnings",
      "-Xlint",
      "-deprecation",
      "-feature",
      "-unchecked"
    ),
    ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
  )
}