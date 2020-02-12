import sbt.Keys._
import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}

// -----------------------------------------------------------------------
// Versions
// -----------------------------------------------------------------------
val ScalaTagsVersion = "0.6.2"
val ScalaRxVersion = "0.4.0"
val ScalaJSJQueryVersion = "0.9.1"

// -----------------------------------------------------------------------
// Settings
// -----------------------------------------------------------------------
lazy val commonSettings = Seq(
  scalaVersion := "2.11.11",
  crossScalaVersions := Seq(scalaVersion.value, "2.12.3"),
  organization := "com.github.karasiq",
  isSnapshot := version.value.endsWith("SNAPSHOT")
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
  pomExtra :=
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

lazy val releaseSettings = Seq(
  releaseCrossBuild := true,
  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
  releaseProcess := {
    import ReleaseTransformations._

    Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      // runClean,
      // runTest,
      setReleaseVersion,
      releaseStepCommandAndRemaining("publishSigned"),
      // releaseStepCommandAndRemaining("sonatypeRelease"),
      commitReleaseVersion,
      tagRelease,
      setNextVersion,
      commitNextVersion,
      // pushChanges
    )
  }
)

// -----------------------------------------------------------------------
// Context library
// -----------------------------------------------------------------------
lazy val contextLibrary = (crossProject(JSPlatform, JVMPlatform) in file("context"))
  .settings(
    commonSettings,
    publishSettings,
    name := "scalajs-bootstrap-context",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "scalatags" % ScalaTagsVersion,
      "com.lihaoyi" %%% "scalarx" % ScalaRxVersion
    )
  )

lazy val contextLibraryJS = contextLibrary.js

lazy val contextLibraryJVM = contextLibrary.jvm

// -----------------------------------------------------------------------
// JQuery library
// -----------------------------------------------------------------------
lazy val jQueryLibrary = (project in file("jquery"))
  .settings(
    commonSettings,
    publishSettings,
    name := "scalajs-bootstrap-jquery",
    libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % ScalaJSJQueryVersion,
    npmDependencies in Compile += "jquery" → "~3.4.1"
  )
  .enablePlugins(scalajsbundler.sbtplugin.ScalaJSBundlerPlugin)

// -----------------------------------------------------------------------
// Library
// -----------------------------------------------------------------------
lazy val jsLibrarySettings = Seq(
  libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % ScalaJSJQueryVersion,
  scalacOptions += {
    val local = file("").toURI
    val remote = s"https://raw.githubusercontent.com/Karasiq/scalajs-bootstrap/${git.gitHeadCommit.value.get}/"
    s"-P:scalajs:mapSourceURI:$local->$remote"
  }
)

lazy val library = crossProject(JSPlatform, JVMPlatform)
  .settings(commonSettings, publishSettings, name := "scalajs-bootstrap")
  .jsSettings(
    jsLibrarySettings,
    npmDependencies in Compile += "bootstrap" → "~3.4.1",
    version in webpack := "4.41.6"
  )
  .dependsOn(contextLibrary)

lazy val libraryJS = library.js
  .dependsOn(jQueryLibrary)
  .enablePlugins(scalajsbundler.sbtplugin.ScalaJSBundlerPlugin)

lazy val libraryJVM = library.jvm

lazy val libraryV4 = (crossProject(JSPlatform, JVMPlatform) in file("library-v4"))
  .settings(commonSettings, publishSettings, name := "scalajs-bootstrap-v4")
  .jsSettings(
    jsLibrarySettings,
    npmDependencies in Compile ++= Seq(
      "popper.js" → "^1.14.3",
      "bootstrap" → "~4.4.1"
    ),
    version in webpack := "4.41.6"
  )
  .dependsOn(contextLibrary)

lazy val libraryV4JS = libraryV4.js
  .dependsOn(jQueryLibrary)
  .enablePlugins(scalajsbundler.sbtplugin.ScalaJSBundlerPlugin)

lazy val libraryV4JVM = libraryV4.jvm

// -----------------------------------------------------------------------
// Test page
// -----------------------------------------------------------------------
lazy val testPageSettings = Seq(
  scalaJSUseMainModuleInitializer := true,
  name := "scalajs-bootstrap-test-frontend",
  npmDevDependencies in Compile ++= Seq(
    "webpack-merge" -> "~4.2.2",
    "imports-loader" -> "~0.8.0",
    "expose-loader" -> "~0.7.5"
  ),
  webpackConfigFile := Some(baseDirectory.value / "webpack.config.js")
)

lazy val testPageV4Settings = Seq(
  scalaJSUseMainModuleInitializer := true,
  name := "scalajs-bootstrap-test-frontend-v4"
)

lazy val testShared = (crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Pure) in file("test") / "shared")
  .settings(commonSettings, noPublishSettings, name := "scalajs-bootstrap-test-shared")
  .dependsOn(library)

lazy val testSharedJS = testShared.js

lazy val testSharedJVM = testShared.jvm

lazy val testSharedV4 = (crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Pure) in file("test") / "shared-v4")
  .settings(commonSettings, noPublishSettings, name := "scalajs-bootstrap-test-shared-v4")
  .dependsOn(libraryV4)

lazy val testSharedV4JS = testSharedV4.js

lazy val testSharedV4JVM = testSharedV4.jvm

lazy val testPage = (project in (file("test") / "frontend"))
  .settings(commonSettings, testPageSettings, noPublishSettings)
  .enablePlugins(scalajsbundler.sbtplugin.ScalaJSBundlerPlugin)
  .dependsOn(testSharedJS)

lazy val testPageV4 = (project in (file("test") / "frontend-v4"))
  .settings(commonSettings, testPageV4Settings, noPublishSettings)
  .enablePlugins(scalajsbundler.sbtplugin.ScalaJSBundlerPlugin)
  .dependsOn(testSharedV4JS)

// -----------------------------------------------------------------------
// Test server
// -----------------------------------------------------------------------
lazy val testServerSettings = Seq(
  scalaVersion := "2.11.11",
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
  scalaJsBundlerCompile in Compile := {
    (scalaJsBundlerCompile in Compile)
      .dependsOn(webpack in fullOptJS in Compile in testPage, webpack in fullOptJS in Compile in testPageV4)
      .value
  },
  scalaJsBundlerAssets in Compile ++= {
    import com.karasiq.scalajsbundler.dsl._

    // val jQuery = Seq(Script from url("https://code.jquery.com/jquery-3.2.1.js"))

    val bootstrap3 = Seq(
      Style from url("https://raw.githubusercontent.com/twbs/bootstrap/v3.3.7/dist/css/bootstrap.css")
      // Script from url("https://raw.githubusercontent.com/twbs/bootstrap/v3.3.7/dist/js/bootstrap.js")
    )

    val bootstrap4 = Seq(
      Style from url("https://raw.githubusercontent.com/twbs/bootstrap/v4.1.1/dist/css/bootstrap.css")
      // Script from url("https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/popper.min.js"),
      // Script from url("https://raw.githubusercontent.com/twbs/bootstrap/v4.1.1/dist/js/bootstrap.bundle.js")
    )

    val fonts = Seq(Style from url("https://raw.githubusercontent.com/FortAwesome/Font-Awesome/v4.5.0/css/font-awesome.css")) ++
      fontPackage("glyphicons-halflings-regular", "https://raw.githubusercontent.com/twbs/bootstrap/v3.3.6/dist/fonts/glyphicons-halflings-regular") ++
      fontPackage("fontawesome-webfont", "https://raw.githubusercontent.com/FortAwesome/Font-Awesome/v4.5.0/fonts/fontawesome-webfont")

    Seq(
      Bundle("index", bootstrap3, Html from TestPageAssets.index, Style from TestPageAssets.style, fonts, scalaJsBundlerApplication(testPage, fastOpt = false).value),
      Bundle("index-v4", bootstrap4, Html from TestPageAssets.index, /* Style from TestPageAssets.style, */ fonts, scalaJsBundlerApplication(testPageV4, fastOpt = false).value)
    )
  } /* ,
  scalaJsBundlerCompilers in Compile := AssetCompilers {
    case "text/javascript" ⇒
      ConcatCompiler
  }.<<=(AssetCompilers.default) */
)

lazy val testServer = (project in file("test"))
  .settings(testServerSettings, noPublishSettings)
  .dependsOn(testSharedJVM)
  .enablePlugins(SJSAssetBundlerPlugin)

// -----------------------------------------------------------------------
// Misc
// -----------------------------------------------------------------------
lazy val `scalajs-bootstrap` = (project in file("."))
  .settings(commonSettings, noPublishSettings)
  .aggregate(contextLibraryJS, contextLibraryJVM, jQueryLibrary, libraryJS, libraryJVM, libraryV4JS, libraryV4JVM)
