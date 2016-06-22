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
    div("progress".addClass)(
      div("progress-bar".addClass, role := "progressbar", Rx[AutoModifier](aria.valuenow := progress()), aria.valuemin := 0, aria.valuemax := 100, Rx[AutoModifier](width := progress().pct), content, md)
    )
  }
}

/**
  * Provide up-to-date feedback on the progress of a workflow or action with simple yet flexible progress bars.
  * @see [[http://getbootstrap.com/components/#progress]]
  */
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
