package com.karasiq.bootstrap.progressbar

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ClassModifier

import scalatags.JsDom.all._

object ProgressBarStyles {
  private def style(s: String): ClassModifier = new ClassModifier {
    override def classMod: Modifier = s"progress-bar-$s".addClass
  }

  def success: ClassModifier = style("success")
  def info: ClassModifier = style("info")
  def warning: ClassModifier = style("warning")
  def danger: ClassModifier = style("danger")
}
