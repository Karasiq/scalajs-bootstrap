package com.karasiq.bootstrap.panel

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ModifierFactory

import scalatags.JsDom.all._

sealed trait PanelStyle extends ModifierFactory

object DefaultPanelStyle extends PanelStyle {
  val createModifier: Modifier = ()
}

final class PanelStyleValue private[panel](style: String) extends PanelStyle {
  val className = s"panel-$style"
  val createModifier = className.addClass
}

object PanelStyle {
  def default = DefaultPanelStyle
  lazy val primary = new PanelStyleValue("primary")
  lazy val success = new PanelStyleValue("success")
  lazy val info = new PanelStyleValue("info")
  lazy val warning = new PanelStyleValue("warning")
  lazy val danger = new PanelStyleValue("danger")
}