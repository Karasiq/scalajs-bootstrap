import scala.annotation.nowarn

import sbt.{Def, Project}
import scalatags.Text.all._

import com.karasiq.scalajsbundler.dsl._
import com.karasiq.scalajsbundler.ScalaJSBundler.PageContent

object TestPageAssets {
  def index: String = {
    "<!DOCTYPE html>" + html(
      head(
        base(href := "/"),
        meta(name := "viewport", content := "width=device-width, initial-scale=1.0")
      ),
      body(
        // Empty
      )
    )
  }

  def style: String = {
    """
      |#main-container {
      |    padding: 20px;
      |}
      |
      |@media (min-width: 768px) {
      |    #main-container {
      |        max-width: 80%;
      |    }
      |}
      |
      |td.buttons {
      |    text-align: center;
      |}
      |
      |.panel-primary .panel-head-buttons .glyphicon {
      |    color: white;
      |}
      |
      |.glyphicon {
      |    margin-left: 2px;
      |    margin-right: 2px;
      |}
      |
      |.panel-title .glyphicon {
      |    margin-right: 10px;
      |}
      |
      |#main-container {
      |    margin-top: 50px;
      |}
    """.stripMargin
  }

  // TODO: Move to the bundler
  @nowarn
  def sourceMap(project: Project, fastOpt: Boolean = false): Def.Initialize[PageContent] = Def.setting {
    import sbt.{project ⇒ _, _}
    import sbt.Keys.{name, scalaVersion, target}

    val nameValue    = (name in project).value
    val targetValue  = (target in project).value
    val versionValue = (scalaVersion in project).value

    val output = targetValue / s"scala-${CrossVersion.binaryScalaVersion(versionValue)}" / "scalajs-bundler" / "main"
    val sourceMapName =
      if (fastOpt) s"$nameValue-fastopt-bundle.js.map"
      else s"$nameValue-opt-bundle.js.map"

    Static(s"scripts/$sourceMapName") from (output / sourceMapName)
  }
}
