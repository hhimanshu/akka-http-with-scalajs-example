import Settings.versions._

name := Settings.name

val sharedDirectory = "shared"
/**
  * We need cross building to make have a shared project with sources
  * shared between server and client. CrossProject is a builder that comes
  * in a plugin with ScalaJS and helps us achieve that.
  * There are other CrossTypes (Full [default] and Dummy) which provides a
  * very different structure than one we are using here. Read more about them
  * at
  * https://www.scala-js.org/doc/project/cross-build.html
  * https://www.scala-js.org/api/sbt-scalajs/0.6.14/#org.scalajs.sbtplugin.cross.CrossProject
  */
lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared")).
  settings(
    scalaVersion := scala,
    libraryDependencies ++= Settings.sharedDependencies.value
  )
  .jvmSettings(
    unmanagedResourceDirectories in Compile += file(".") / sharedDirectory / "src" / "main" / "resources"
  )
  .jsSettings(
    unmanagedResourceDirectories in Compile += file(".") / sharedDirectory / "src" / "main" / "resources"
  )
  // This creates new JS Project combining the ScalaJSWeb
  // This does not touches the JVM project
  .jsConfigure(_ enablePlugins ScalaJSWeb)

// Needed so that SBT can find projects
// reference: https://www.scala-js.org/api/sbt-scalajs/0.6.14/#org.scalajs.sbtplugin.cross.CrossProject
lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val server = (project in file("server")).settings(
  scalaVersion := scala,

  /**
    * Scala.js projects are collected in the scalaJSProjects setting key
    * of the SbtWeb project.
    * The plugin does nothing if scalaJSProjects is not specified or is empty
    *
    * scalaJSProjects setting lists the Scala.js projects whose output is used by the server.
    *
    * https://github.com/vmunier/sbt-web-scalajs
    */
  scalaJSProjects := Seq(client),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  // triggers scalaJSPipeline when using compile or continuous compilation
  compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
  libraryDependencies ++= Settings.jvmDependencies.value,
  WebKeys.packagePrefix in Assets := "public/",
  managedClasspath in Runtime += (packageBin in Assets).value
).enablePlugins(SbtWeb, SbtTwirl, JavaAppPackaging).
  dependsOn(sharedJvm)

lazy val client = (project in file("client")).settings(
  scalaVersion := scala,

  /**
    * If you want the code which is used to run the main class to be written to a file, you can set persistLauncher := true
    * See section Writing Launcher Code in
    * https://www.scala-js.org/doc/project/building.html
    */
  persistLauncher := true,
  persistLauncher in Test := false,
  libraryDependencies ++= Settings.scalajsDependencies.value

  /**
    * ScalaJSWeb should be manually added to the Scala.js projects
    * that you want to connect to your SbtWeb project.
    * https://github.com/vmunier/sbt-web-scalajs
    */
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
  dependsOn(sharedJs)


// TODO: Add release command

/**
  * loads the server project at sbt startup
  * project <project_name> sets the current project. It is similar to
  * using cd in bash or `use mydb` in MySQL.
  */
onLoad in Global := (Command.process("project server", _: State)) compose (onLoad in Global).value
