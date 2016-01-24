package com.karasiq.bootstrap.progressbar

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ClassModifier

import scalatags.JsDom.all._

sealed trait ProgressBarStyle extends ClassModifier

object ProgressBarStyle {
  private def style(s: String): ProgressBarStyle = new ProgressBarStyle {
    override def classMod: Modifier = s"progress-bar-$s".addClass
  }

  def success: ProgressBarStyle = style("success")
  def info: ProgressBarStyle = style("info")
  def warning: ProgressBarStyle = style("warning")
  def danger: ProgressBarStyle = style("danger")

  def striped: ProgressBarStyle = style("striped")
  def animated: ProgressBarStyle = new ProgressBarStyle {
    override def classMod: Modifier = "active".addClass
  }
}
