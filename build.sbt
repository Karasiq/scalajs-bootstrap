import sbt.Keys._
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

import com.karasiq.scalajsbundler.compilers.{AssetCompilers, ConcatCompiler}
import com.karasiq.scalajsbundler.dsl.Mimes

// Reload on .sbt change
Global / onChangedBuildSource := ReloadOnSourceChanges

// -----------------------------------------------------------------------
// Versions
// -----------------------------------------------------------------------
val ScalaTagsVersion     = "0.11.0"
val ScalaRxVersion       = if (ProjectDefs.scalaJSIs06) "0.4.1" else "0.4.3"
val ScalaJsDomVersion    = if (ProjectDefs.scalaJSIs06) "1.0.0" else "2.0.0"
val ScalaJSJQueryVersion = if (ProjectDefs.scalaJSIs06) "3.0.1" else "3.2.0"

// -----------------------------------------------------------------------
// Settings
// -----------------------------------------------------------------------
lazy val commonSettings = Seq(
  ThisBuild / version       := "2.4.0",
  ThisBuild / versionScheme := Some("pvp"),
  scalaVersion              := (if (ProjectDefs.scalaJSIs06) "2.13.4" else "2.13.7"),
  crossScalaVersions := {
    if (ProjectDefs.scalaJSIs06) Seq("2.11.12", "2.12.12", scalaVersion.value)
    else Seq("2.12.15", scalaVersion.value)
  },
  organization := "com.github.karasiq",
  isSnapshot   := version.value.endsWith("SNAPSHOT")
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
  sonatypeSessionName    := s"scalajs-bootstrap v${version.value}",
  publishConfiguration   := publishConfiguration.value.withOverwrite(true),
  publishTo              := sonatypePublishToBundle.value,
  Test / publishArtifact := false,
  pomIncludeRepository   := { _ ⇒ false },
  licenses               := Seq("The MIT License" → url("http://opensource.org/licenses/MIT")),
  homepage               := Some(url("https://github.com/Karasiq/scalajs-bootstrap")),
  scmInfo := Some(ScmInfo(new URL("https://github.com/Karasiq/scalajs-bootstrap"), "scm:git:git@github.com:Karasiq/scalajs-bootstrap.git")),
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
  publishArtifact           := false,
  makePom / publishArtifact := false,
  publishTo                 := Some(Resolver.file("Repo", file("target/repo")))
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
      "com.lihaoyi" %%% "scalatags" % ScalaTagsVersion
    ),
    libraryDependencies ++= (scalaBinaryVersion.value match {
      case "2.11" ⇒
        Seq(
          "com.lihaoyi" %%% "scalarx" % "0.4.0"
        )
      case _ ⇒
        Seq(
          "com.lihaoyi" %%% "scalarx" % ScalaRxVersion
        )
    })
  )

lazy val contextLibraryJS = contextLibrary.js

lazy val contextLibraryJVM = contextLibrary.jvm

// -----------------------------------------------------------------------
// JQuery library
// -----------------------------------------------------------------------
lazy val jsLibrarySettings = Seq(
  libraryDependencies ++= (scalaBinaryVersion.value match {
    case "2.11" ⇒
      Seq(
        "io.udash"     %%% "udash-jquery" % "3.0.1",
        "org.scala-js" %%% "scalajs-dom"  % ScalaJsDomVersion
      )
    case _ if ProjectDefs.scalaJSIs06 ⇒
      Seq(
        "io.udash"     %%% "udash-jquery" % "3.0.2",
        "org.scala-js" %%% "scalajs-dom"  % ScalaJsDomVersion
      )

    case _ ⇒
      Seq(
        "io.udash"     %%% "udash-jquery" % ScalaJSJQueryVersion,
        "org.scala-js" %%% "scalajs-dom"  % ScalaJsDomVersion
      )
  }),
  scalacOptions += {
    val local  = file("").toURI
    val remote = s"https://raw.githubusercontent.com/Karasiq/scalajs-bootstrap/${git.gitHeadCommit.value.get}/"
    s"-P:scalajs:mapSourceURI:$local->$remote"
  },
  Compile / webpackEmitSourceMaps := true,
  Compile / webpack / version     := "4.46.0"
)

lazy val jQueryLibrary = (project in file("jquery"))
  .settings(
    commonSettings,
    jsLibrarySettings,
    publishSettings,
    name                                 := "scalajs-bootstrap-jquery",
    Compile / npmDependencies += "jquery" → "~3.4.1"
  )
  .enablePlugins(scalajsbundler.sbtplugin.ScalaJSBundlerPlugin)

// -----------------------------------------------------------------------
// Library
// -----------------------------------------------------------------------
lazy val library = crossProject(JSPlatform, JVMPlatform)
  .settings(commonSettings, publishSettings, name := "scalajs-bootstrap")
  .jsSettings(
    jsLibrarySettings,
    Compile / npmDependencies += "bootstrap" → "~3.4.1"
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
    Compile / npmDependencies ++= Seq(
      "popper.js" → "^1.16.1",
      "bootstrap" → "~4.5.3"
    )
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
  name                            := "scalajs-bootstrap-test-frontend",
  Compile / npmDevDependencies ++= Seq(
    "webpack-merge"  → "~4.2.2",
    "imports-loader" → "~0.8.0",
    "expose-loader"  → "~0.7.5"
  ),
  webpackConfigFile := Some(baseDirectory.value / "webpack.config.js")
)

lazy val testPageV4Settings = Seq(
  scalaJSUseMainModuleInitializer := true,
  name                            := "scalajs-bootstrap-test-frontend-v4"
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
  .settings(commonSettings, jsLibrarySettings, testPageSettings, noPublishSettings)
  .enablePlugins(scalajsbundler.sbtplugin.ScalaJSBundlerPlugin)
  .dependsOn(testSharedJS)

lazy val testPageV4 = (project in (file("test") / "frontend-v4"))
  .settings(commonSettings, jsLibrarySettings, testPageV4Settings, noPublishSettings)
  .enablePlugins(scalajsbundler.sbtplugin.ScalaJSBundlerPlugin)
  .dependsOn(testSharedV4JS)

// -----------------------------------------------------------------------
// Test server
// -----------------------------------------------------------------------
lazy val testServerSettings = Seq(
  scalaVersion := "2.13.7",
  name         := "scalajs-bootstrap-test",
  resolvers += Resolver.sonatypeRepo("snapshots"),
  libraryDependencies ++= {
    val akkaV     = "2.6.18"
    val akkaHttpV = "10.2.7"
    Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % akkaV,
      "com.typesafe.akka" %% "akka-stream"      % akkaV,
      "com.typesafe.akka" %% "akka-http"        % akkaHttpV
    )
  },
  clean := clean.dependsOn(testPage / clean, testPageV4 / clean).value
) ++ inConfig(Compile)(
  Seq(
    mainClass            := Some("com.karasiq.bootstrap.test.backend.BootstrapTestApp"),
    compile              := compile.dependsOn(scalaJsBundlerCompile).value,
    scalaJsBundlerInline := false,
    scalaJsBundlerCompile := {
      val result = scalaJsBundlerCompile
        .dependsOn(testPage / Compile / fastOptJS / webpack, testPageV4 / Compile / fastOptJS / webpack)
        .value

      result
    },
    scalaJsBundlerAssets ++= {
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
        fontPackage(
          "glyphicons-halflings-regular",
          "https://raw.githubusercontent.com/twbs/bootstrap/v3.3.6/dist/fonts/glyphicons-halflings-regular"
        ) ++
        fontPackage("fontawesome-webfont", "https://raw.githubusercontent.com/FortAwesome/Font-Awesome/v4.5.0/fonts/fontawesome-webfont")

      Seq(
        Bundle(
          "index",
          bootstrap3,
          Html from TestPageAssets.index,
          Style from TestPageAssets.style,
          fonts,
          scalaJsBundlerApplication(testPage, fastOpt = true).value,
          TestPageAssets.sourceMap(testPage, fastOpt = true).value
        ),
        Bundle(
          "index-v4",
          bootstrap4,
          Html from TestPageAssets.index, /* Style from TestPageAssets.style, */ fonts,
          scalaJsBundlerApplication(testPageV4, fastOpt = true).value,
          TestPageAssets.sourceMap(testPageV4, fastOpt = true).value
        )
      )
    },
    Compile / scalaJsBundlerCompilers := AssetCompilers { case Mimes.javascript ⇒
      ConcatCompiler
    }.<<=(AssetCompilers.default)
  )
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
