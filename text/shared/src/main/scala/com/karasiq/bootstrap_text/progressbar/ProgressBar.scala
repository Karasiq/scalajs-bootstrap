package com.karasiq.bootstrap_text.progressbar

import com.karasiq.bootstrap_text.BootstrapHtmlComponent
import com.karasiq.bootstrap_text.BootstrapImplicits._

import scalatags.Text.all._

sealed abstract class ProgressBar extends BootstrapHtmlComponent {
  protected def content: Modifier
  def progress: Int

  override def renderTag(md: Modifier*): RenderedTag = {
    div("progress".addClass)(
      div("progress-bar".addClass, role := "progressbar", aria.valuenow := progress,
        aria.valuemin := 0, aria.valuemax := 100, width := progress.pct, content, md)
    )
  }
}

/**
  * Provide up-to-date feedback on the progress of a workflow or action with simple yet flexible progress bars.
  * @see [[http://getbootstrap.com/components/#progress]]
  */
object ProgressBar {
  def basic(value: Int): ProgressBar = new ProgressBar {
    override val progress: Int = value

    override protected val content: Modifier = {
      span("sr-only".addClass, s"$progress% Complete")
    }
  }

  def withLabel(value: Int): ProgressBar = new ProgressBar {
    override val progress: Int = value

    override protected val content: Modifier = {
      s"$progress%"
    }
  }
}
