logLevel := Level.Warn

resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.5")

addSbtPlugin("com.github.karasiq" % "sbt-scalajs-bundler" % "1.0.3")

libraryDependencies += "com.lihaoyi" %% "scalatags" % "0.5.3"