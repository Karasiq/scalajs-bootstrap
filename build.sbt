import sbt.Keys._

import com.karasiq.scalajsbundler.compilers.{AssetCompilers, ConcatCompiler}

// Versions
val scalaTagsVersion = "0.6.2"
val scalaRxVersion = "0.3.2"

// Settings
lazy val commonSettings = Seq(
  organization := "com.github.karasiq",
  version := "2.1.6",
  isSnapshot := version.value.endsWith("SNAPSHOT"),
  scalaVersion := "2.11.8",
  crossScalaVersions := Seq("2.11.8", "2.12.1")
)

lazy val publishSettings = Seq(
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

lazy val noPublishSettings = Seq(
  publishArtifact := false,
  publishArtifact in makePom := false,
  publishTo := Some(Resolver.file("Repo", file("target/repo")))
)

lazy val librarySettings = Seq(
  name := "scalajs-bootstrap"
)

lazy val libraryV4Settings = Seq(
  name := "scalajs-bootstrap-v4"
)

lazy val testServerSettings = Seq(
  scalaVersion := "2.11.8",
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
  scalaJsBundlerCompile in Compile <<= (scalaJsBundlerCompile in Compile).dependsOn(webpack in fullOptJS in Compile in testPage, webpack in fullOptJS in Compile in testPageV4),
  scalaJsBundlerAssets in Compile ++= {
    import com.karasiq.scalajsbundler.dsl._

    val jsDeps = Seq(
      // jQuery
      // Script from url("https://code.jquery.com/jquery-3.2.1.js"),

      // Font Awesome
      Style from url("https://raw.githubusercontent.com/FortAwesome/Font-Awesome/v4.5.0/css/font-awesome.css")
    )

    val bootstrap3 = Seq(
      Style from url("https://raw.githubusercontent.com/twbs/bootstrap/v3.3.7/dist/css/bootstrap.css")
      // Script from url("https://raw.githubusercontent.com/twbs/bootstrap/v3.3.7/dist/js/bootstrap.js")
    )

    val bootstrap4 = Seq(
      // Script from url("https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/popper.min.js"),
      Style from url("https://raw.githubusercontent.com/twbs/bootstrap/v4.0.0-beta.2/dist/css/bootstrap.css")
      //Script from url("https://raw.githubusercontent.com/twbs/bootstrap/v4.0.0-beta.2/dist/js/bootstrap.bundle.js")
    )

    val fonts = fontPackage("glyphicons-halflings-regular", "https://raw.githubusercontent.com/twbs/bootstrap/v3.3.6/dist/fonts/glyphicons-halflings-regular") ++
      fontPackage("fontawesome-webfont", "https://raw.githubusercontent.com/FortAwesome/Font-Awesome/v4.5.0/fonts/fontawesome-webfont")

    Seq(
      Bundle("index", jsDeps, bootstrap3, Html from TestPageAssets.index, Style from TestPageAssets.style, fonts, scalaJsApplication(testPage, fastOpt = false, launcher = false).value),
      Bundle("index-v4", jsDeps, bootstrap4, Html from TestPageAssets.index, Style from TestPageAssets.style, fonts, scalaJsApplication(testPageV4, fastOpt = false, launcher = false).value)
    )
  },
  scalaJsBundlerCompilers in Compile := AssetCompilers {
    case "text/javascript" ⇒
      ConcatCompiler
  }.<<=(AssetCompilers.default)
)

lazy val testPageSettings = Seq(
  scalaJSUseMainModuleInitializer := true,
  name := "scalajs-bootstrap-test-frontend"
)

lazy val testPageV4Settings = Seq(
  scalaJSUseMainModuleInitializer := true,
  name := "scalajs-bootstrap-test-frontend-v4"
)

// Projects
lazy val library = crossProject
  .settings(commonSettings, librarySettings, publishSettings)
  .jsSettings(
    libraryDependencies ++= Seq(
      "be.doeraene" %%% "scalajs-jquery" % "0.9.1",
      "com.lihaoyi" %%% "scalatags" % scalaTagsVersion,
      "com.lihaoyi" %%% "scalarx" % scalaRxVersion
    ),
    scalacOptions ++= (if (isSnapshot.value) Seq.empty else Seq({
      val g = s"https://raw.githubusercontent.com/Karasiq/${name.value}"
      s"-P:scalajs:mapSourceURI:${baseDirectory.value.toURI}->$g/v${version.value}/"
    })),
    npmDependencies in Compile ++= Seq(
      "jquery" -> "3.2.1",
      "bootstrap" -> "3.3.7"
    )
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "scalatags" % scalaTagsVersion,
      "com.lihaoyi" %% "scalarx" % scalaRxVersion
    )
  )
  .enablePlugins(scalajsbundler.sbtplugin.ScalaJSBundlerPlugin)

lazy val libraryJS = library.js

lazy val libraryJVM = library.jvm

lazy val libraryV4 = (crossProject in file("library-v4"))
  .settings(commonSettings, libraryV4Settings, publishSettings)
  .jsSettings(
    libraryDependencies ++= Seq(
      "be.doeraene" %%% "scalajs-jquery" % "0.9.1",
      "com.lihaoyi" %%% "scalatags" % scalaTagsVersion,
      "com.lihaoyi" %%% "scalarx" % scalaRxVersion
    ),
    scalacOptions ++= (if (isSnapshot.value) Seq.empty else Seq({
      val g = s"https://raw.githubusercontent.com/Karasiq/${name.value}"
      s"-P:scalajs:mapSourceURI:${baseDirectory.value.toURI}->$g/v${version.value}/"
    })),
    npmDependencies in Compile ++= Seq(
      "jquery" -> "3.2.1",
      "bootstrap" -> "4.0.0-beta2"
    )
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "scalatags" % scalaTagsVersion,
      "com.lihaoyi" %% "scalarx" % scalaRxVersion
    )
  )
  .enablePlugins(scalajsbundler.sbtplugin.ScalaJSBundlerPlugin)

lazy val libraryV4JS = libraryV4.js

lazy val libraryV4JVM = libraryV4.jvm

lazy val testShared = (crossProject.crossType(CrossType.Pure) in file("test") / "shared")
  .settings(commonSettings, name := "scalajs-bootstrap-test-shared")
  .dependsOn(library)

lazy val testSharedJS = testShared.js

lazy val testSharedJVM = testShared.jvm

lazy val testSharedV4 = (crossProject.crossType(CrossType.Pure) in file("test") / "shared-v4")
  .settings(commonSettings, name := "scalajs-bootstrap-test-shared-v4")
  .dependsOn(libraryV4)

lazy val testSharedV4JS = testSharedV4.js

lazy val testSharedV4JVM = testSharedV4.jvm

lazy val testServer = (project in file("test"))
  .settings(testServerSettings)
  .dependsOn(testSharedJVM)
  .enablePlugins(com.karasiq.scalajsbundler.ScalaJSBundlerPlugin)

lazy val testPage = (project in (file("test") / "frontend"))
  .settings(commonSettings, testPageSettings)
  .enablePlugins(scalajsbundler.sbtplugin.ScalaJSBundlerPlugin)
  .dependsOn(testSharedJS)

lazy val testPageV4 = (project in (file("test") / "frontend-v4"))
  .settings(commonSettings, testPageV4Settings)
  .enablePlugins(scalajsbundler.sbtplugin.ScalaJSBundlerPlugin)
  .dependsOn(testSharedV4JS)

lazy val root = (project in file("."))
  .settings(commonSettings, noPublishSettings)
  .aggregate(libraryJS, libraryJVM, libraryV4JS, libraryV4JVM)