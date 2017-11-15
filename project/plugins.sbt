logLevel := Level.Warn

resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.14")

addSbtPlugin("com.github.karasiq" % "sbt-scalajs-bundler" % "1.1.2-SNAPSHOT")

// addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.9.0")

libraryDependencies += "com.lihaoyi" %% "scalatags" % "0.5.4"

resolvers += MavenRepository("Central", "https://repo.maven.apache.org/maven2/")