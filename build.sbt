import sbt.Keys._

// Settings
lazy val commonSettings = Seq(
  organization := "com.github.karasiq",
  version := "1.1.2",
  isSnapshot := version.value.endsWith("SNAPSHOT"),
  scalaVersion := "2.11.8",
  crossScalaVersions := Seq("2.11.8", "2.12.0"),
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
  name := "scalajs-bootstrap",
  libraryDependencies ++= Seq(
    "be.doeraene" %%% "scalajs-jquery" % "0.9.1",
    "com.lihaoyi" %%% "scalatags" % "0.6.2",
    "com.lihaoyi" %%% "scalarx" % "0.3.2"
  ),
  scalacOptions ++= (if (isSnapshot.value) Seq.empty else Seq({
    val g = s"https://raw.githubusercontent.com/Karasiq/${name.value}"
    s"-P:scalajs:mapSourceURI:${baseDirectory.value.toURI}->$g/v${version.value}/"
  }))
)

lazy val testServerSettings = Seq(
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
  scalaJsBundlerCompile in Compile <<= (scalaJsBundlerCompile in Compile).dependsOn(fullOptJS in Compile in testPage),
  scalaJsBundlerAssets in Compile += {
    import com.karasiq.scalajsbundler.dsl.{Script, _}

    val jsDeps = Seq(
      // jQuery
      Script from url("https://code.jquery.com/jquery-1.12.0.js"),

      // Bootstrap
      Style from url("https://raw.githubusercontent.com/twbs/bootstrap/v3.3.6/dist/css/bootstrap.css"),
      Script from url("https://raw.githubusercontent.com/twbs/bootstrap/v3.3.6/dist/js/bootstrap.js"),

      // Font Awesome
      Style from url("https://raw.githubusercontent.com/FortAwesome/Font-Awesome/v4.5.0/css/font-awesome.css")
    )

    val fonts = fontPackage("glyphicons-halflings-regular", "https://raw.githubusercontent.com/twbs/bootstrap/v3.3.6/dist/fonts/glyphicons-halflings-regular") ++
      fontPackage("fontawesome-webfont", "https://raw.githubusercontent.com/FortAwesome/Font-Awesome/v4.5.0/fonts/fontawesome-webfont")

    Bundle("index", jsDeps, Html from TestPageAssets.index, Style from TestPageAssets.style, fonts, scalaJsApplication(testPage).value)
  }
)

lazy val testPageSettings = Seq(
  persistLauncher in Compile := true,
  name := "scalajs-bootstrap-test-frontend"
)

// Projects
lazy val library = (project in file("."))
  .settings(commonSettings, librarySettings)
  .enablePlugins(ScalaJSPlugin)

lazy val testServer = (project in file("test"))
  .settings(commonSettings, testServerSettings)
  .enablePlugins(ScalaJSBundlerPlugin)

lazy val testPage = (project in (file("test") / "frontend"))
  .settings(commonSettings, testPageSettings)
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(library)
