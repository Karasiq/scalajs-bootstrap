logLevel := Level.Warn

resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.8")

addSbtPlugin("com.github.karasiq" % "sbt-scalajs-bundler" % "1.0.7")

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "scalatags" % "0.5.4",
  "org.webjars" % "bootstrap" % "4.0.0-alpha.2",
  "org.webjars.bower" % "tether" % "1.1.1"
)