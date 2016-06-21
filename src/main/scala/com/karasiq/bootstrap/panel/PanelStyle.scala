package com.karasiq.bootstrap.panel

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ModifierFactory

import scalatags.JsDom.all._

sealed trait PanelStyle extends ModifierFactory {
  def styleClass: Option[String]

  override def createModifier: Modifier = styleClass.classOpt
}

object PanelStyle {
  def default: PanelStyle = new PanelStyle {
    override def styleClass: Option[String] = None
  }

  private def style(name: String): PanelStyle = new PanelStyle {
    override def styleClass: Option[String] = Some(s"panel-$name")
  }

  def primary: PanelStyle = style("primary")
  def success: PanelStyle = style("success")
  def info: PanelStyle = style("info")
  def warning: PanelStyle = style("warning")
  def danger: PanelStyle = style("danger")
}