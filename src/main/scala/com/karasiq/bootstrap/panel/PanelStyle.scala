package com.karasiq.bootstrap.panel

sealed trait PanelStyle {
  def styleClass: Option[String]
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