import sbt._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object Settings {
  // The name of the application  
  val name = "todoMVC-akka-http-scalajs"

  // The version of the application
  val version = "1.0.0"

  // centralize the version numbers used in the application
  // This way, there is no reason to touch the build.sbt
  object versions {
    val scala = "2.12.1"
    val akkaHttp = "10.0.3"
    val scalajsScripts = "1.1.0"
    val config = "1.3.1"

    val scalajsDom = "0.9.1"
    val scalatags = "0.6.2"
  }

  // describe all your server dependencies here
  val jvmDependencies = Def.setting(Seq(
    "com.typesafe.akka" %% "akka-http" % versions.akkaHttp,
    "com.typesafe.akka" %% "akka-http-spray-json" % versions.akkaHttp,
    "com.vmunier" %% "scalajs-scripts" % versions.scalajsScripts,
    "com.typesafe" % "config" % versions.config
  ))

  // describe all your client dependencies here
  // use of %%%: https://github.com/vmunier/play-with-scalajs-example/issues/20#issuecomment-56589251
  val scalajsDependencies = Def.setting(Seq(
    "org.scala-js" %%% "scalajs-dom" % versions.scalajsDom
  ))

  // describe depdencies that should be shared
  // between server and client
  val sharedDependencies = Def.setting(Seq(
    "com.lihaoyi" %%% "scalatags" % versions.scalatags
  ))
}