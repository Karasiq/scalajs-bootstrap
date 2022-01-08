logLevel := Level.Warn

// resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % ProjectDefs.ScalaJSVersion)

addSbtPlugin("com.github.karasiq" % "sbt-scalajs-bundler" % "1.2.2")

if (ProjectDefs.scalaJSIs06)
  addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler-sjs06" % "0.19.0")
else
  addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.20.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.9.3")

addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.1.0")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.9.10")

libraryDependencies += "com.lihaoyi" %% "scalatags" % "0.11.0"
