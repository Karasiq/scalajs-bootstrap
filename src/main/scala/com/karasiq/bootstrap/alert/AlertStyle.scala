package com.karasiq.bootstrap.alert

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ClassModifier

import scalatags.JsDom.all._

sealed trait AlertStyle extends ClassModifier

object AlertStyle {
  private def style(name: String): AlertStyle = new AlertStyle {
    override def classMod: Modifier = s"alert-$name".addClass
  }

  def success: AlertStyle = style("success")
  def info: AlertStyle = style("info")
  def warning: AlertStyle = style("warning")
  def danger: AlertStyle = style("danger")
}