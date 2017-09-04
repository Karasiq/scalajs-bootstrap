logLevel := Level.Warn

resolvers += Resolver.sonatypeRepo("snapshots")

val scalaJSVersion = Option(System.getenv("SCALAJS_VERSION")).getOrElse("0.6.14")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)

libraryDependencies += "com.lihaoyi" %% "scalatags" % "0.5.4"

if (scalaJSVersion.startsWith("0.6.")) {
  Seq(addSbtPlugin("org.scala-native" % "sbt-scalajs-crossproject" % "0.2.0"), addSbtPlugin("com.github.karasiq" % "sbt-scalajs-bundler" % "1.0.7"))
} else {
  Seq(addSbtPlugin("com.github.karasiq" % "sbt-scalajs-bundler" % "1.1.0"))
}