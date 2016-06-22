package com.karasiq.bootstrap.progressbar

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ModifierFactory

import scalatags.JsDom.all._

sealed trait ProgressBarStyle extends ModifierFactory {
  val className: String
  lazy val createModifier: Modifier = className.addClass
}

object ProgressBarStyle {
  private def style(style: String): ProgressBarStyle = new ProgressBarStyle {
    val className = s"progress-bar-$style"
  }

  lazy val success: ProgressBarStyle = style("success")
  lazy val info: ProgressBarStyle = style("info")
  lazy val warning: ProgressBarStyle = style("warning")
  lazy val danger: ProgressBarStyle = style("danger")

  lazy val striped: ProgressBarStyle = style("striped")
  lazy val animated: ProgressBarStyle = new ProgressBarStyle {
    val className = "active"
  }
}
