import sbt.Keys._

// Settings
lazy val commonSettings = Seq(
  organization := "com.github.karasiq",
  isSnapshot := true,
  version := "1.0.0-SNAPSHOT",
  scalaVersion := "2.11.7",
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  publishArtifact in Test := false,
  pomIncludeRepository := { _ ⇒ false },
  licenses := Seq("The MIT License" → url("http://opensource.org/licenses/MIT")),
  homepage := Some(url("https://github.com/Karasiq/scalajs-bootstrap")),
  pomExtra := <scm>
    <url>git@github.com:Karasiq/scalajs-bootstrap.git</url>
    <connection>scm:git:git@github.com:Karasiq/scalajs-bootstrap.git</connection>
  </scm>
    <developers>
      <developer>
        <id>karasiq</id>
        <name>Piston Karasiq</name>
        <url>https://github.com/Karasiq</url>
      </developer>
    </developers>
)

lazy val librarySettings = Seq(
  libraryDependencies ++= Seq(
    "be.doeraene" %%% "scalajs-jquery" % "0.8.1",
    "com.lihaoyi" %%% "scalatags" % "0.5.3"
  ),
  name := "scalajs-bootstrap"
)

lazy val libraryTestSettings = Seq(
  name := "scalajs-bootstrap-test",
  resolvers += Resolver.sonatypeRepo("snapshots"),
  libraryDependencies ++= {
    val sprayV = "1.3.3"
    val akkaV = "2.4.0"
    Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaV,
      "io.spray" %% "spray-can" % sprayV,
      "io.spray" %% "spray-routing-shapeless2" % sprayV,
      "io.spray" %% "spray-json" % "1.3.2"
    )
  },
  mainClass in Compile := Some("com.karasiq.bootstrap.test.backend.BootstrapTestApp"),
  scalaJsBundlerInline in Compile := true,
  scalaJsBundlerCompile in Compile <<= (scalaJsBundlerCompile in Compile).dependsOn(fullOptJS in Compile in libraryTestFrontend),
  scalaJsBundlerAssets in Compile += {
    import com.karasiq.scalajsbundler.dsl._

    Bundle("index",
      // Static
      Html from TestPageAssets.index,
      Style from TestPageAssets.style,

      // jQuery
      Script from url("https://code.jquery.com/jquery-1.12.0.js"),

      // Bootstrap
      Style from url("https://raw.githubusercontent.com/twbs/bootstrap/v3.3.6/dist/css/bootstrap.css"),
      Script from url("https://raw.githubusercontent.com/twbs/bootstrap/v3.3.6/dist/js/bootstrap.js"),
      Static("fonts/glyphicons-halflings-regular.eot") from url("https://raw.githubusercontent.com/twbs/bootstrap/v3.3.6/dist/fonts/glyphicons-halflings-regular.eot"),
      Static("fonts/glyphicons-halflings-regular.svg") from url("https://raw.githubusercontent.com/twbs/bootstrap/v3.3.6/dist/fonts/glyphicons-halflings-regular.svg"),
      Static("fonts/glyphicons-halflings-regular.ttf") from url("https://raw.githubusercontent.com/twbs/bootstrap/v3.3.6/dist/fonts/glyphicons-halflings-regular.ttf"),
      Static("fonts/glyphicons-halflings-regular.woff") from url("https://raw.githubusercontent.com/twbs/bootstrap/v3.3.6/dist/fonts/glyphicons-halflings-regular.woff"),
      Static("fonts/glyphicons-halflings-regular.woff2") from url("https://raw.githubusercontent.com/twbs/bootstrap/v3.3.6/dist/fonts/glyphicons-halflings-regular.woff2"),

      // Scala.js app
      Script from file("test") / "frontend" / "target" / "scala-2.11" / "scalajs-bootstrap-test-frontend-opt.js",
      Script from file("test") / "frontend" / "target" / "scala-2.11" / "scalajs-bootstrap-test-frontend-launcher.js"
    )
  }
)

lazy val libraryTestFrontendSettings = Seq(
  persistLauncher in Compile := true,
  name := "scalajs-bootstrap-test-frontend",
  libraryDependencies ++= Seq(
    "com.lihaoyi" %%% "scalatags" % "0.5.3"
  )
)

// Projects
lazy val library = Project("scalajs-library", file("."))
  .settings(commonSettings, librarySettings)
  .enablePlugins(ScalaJSPlugin)

lazy val libraryTest = Project("scalajs-bootstrap-test", file("test"))
  .settings(commonSettings, libraryTestSettings)
  .enablePlugins(ScalaJSBundlerPlugin)

lazy val libraryTestFrontend = Project("scalajs-bootstrap-test-frontend", file("test") / "frontend")
  .settings(commonSettings, libraryTestFrontendSettings)
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(library)