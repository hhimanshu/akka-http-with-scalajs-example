// sbt plugin that facilitates compiling, running and testing with Scala.js
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.14")

// fast development turnaround when using sbt ~re-start
addSbtPlugin("io.spray" % "sbt-revolver" % "0.8.0")

// Deploy fat JARs. Restart processes.
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.3")

// https://github.com/sbt/sbt-native-packager
// package zip, tar.gz,dmg, docker images among other things
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.4")

// use Scala.js along with any sbt-web server
addSbtPlugin("com.vmunier" % "sbt-web-scalajs" % "1.0.3")

// Play template engine
addSbtPlugin("com.typesafe.sbt" % "sbt-twirl" % "1.3.0")
