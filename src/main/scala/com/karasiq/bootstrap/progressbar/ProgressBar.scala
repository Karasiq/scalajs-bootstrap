package com.karasiq.bootstrap.progressbar

import com.karasiq.bootstrap.BootstrapHtmlComponent
import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom
import rx._

import scalatags.JsDom.all._

sealed abstract class ProgressBar(implicit ctx: Ctx.Owner) extends BootstrapHtmlComponent[dom.html.Div] {
  protected def content: Modifier

  def progress: Rx[Int]

  override def renderTag(md: Modifier*): RenderedTag = {
    val progressTag = "progress".tag
    progressTag(
      `class` := "progress",
      Rx[AutoModifier](value := progress()),
      max := 100,
      Rx(s"${progress()}%"),
      md
    )
  }
}

object ProgressBar {
  def basic(value: Rx[Int])(implicit ctx: Ctx.Owner): ProgressBar = new ProgressBar {
    override val progress: Rx[Int] = value

    override protected val content: Modifier = Rx {
      span("sr-only".addClass, s"${progress()}% Complete")
    }
  }

  def withLabel(value: Rx[Int])(implicit ctx: Ctx.Owner): ProgressBar = new ProgressBar {
    override val progress: Rx[Int] = value

    override protected val content: Modifier = Rx {
      s"${progress()}%"
    }
  }
}
