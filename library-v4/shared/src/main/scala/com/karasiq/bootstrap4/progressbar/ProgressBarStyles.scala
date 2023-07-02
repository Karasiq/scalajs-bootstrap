package com.karasiq.bootstrap4.progressbar

import com.karasiq.bootstrap.context.RenderingContext

trait ProgressBarStyles { self: RenderingContext ⇒
  import scalaTags.all._

  sealed trait ProgressBarStyle extends ModifierFactory {
    val className: String
    lazy val createModifier = className.addClass
  }

  object ProgressBarStyle {
    private[progressbar] def style(style: String): ProgressBarStyle = new ProgressBarStyle {
      val className = s"progress-bar-$style"
    }
    lazy val striped: ProgressBarStyle  = style("striped")
    lazy val animated: ProgressBarStyle = style("animated")
  }
}
