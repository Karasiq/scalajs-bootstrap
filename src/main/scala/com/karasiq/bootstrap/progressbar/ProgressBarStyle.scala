package com.karasiq.bootstrap.progressbar

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ModifierFactory

import scalatags.JsDom.all._

sealed trait ProgressBarStyle extends ModifierFactory

object ProgressBarStyle {
  private def style(s: String): ProgressBarStyle = new ProgressBarStyle {
    override def createModifier: Modifier = s"progress-bar-$s".addClass
  }

  def success: ProgressBarStyle = style("success")
  def info: ProgressBarStyle = style("info")
  def warning: ProgressBarStyle = style("warning")
  def danger: ProgressBarStyle = style("danger")

  def striped: ProgressBarStyle = style("striped")
  def animated: ProgressBarStyle = new ProgressBarStyle {
    override def createModifier: Modifier = "active".addClass
  }
}
