logLevel := Level.Warn

// resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.0")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.20")

addSbtPlugin("com.github.karasiq" % "sbt-scalajs-bundler" % "1.2.1")

addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.13.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.9.3")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.7")

libraryDependencies += "com.lihaoyi" %% "scalatags" % "0.6.2"