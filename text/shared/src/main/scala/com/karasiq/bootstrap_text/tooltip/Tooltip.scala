package com.karasiq.bootstrap_text.tooltip

import com.karasiq.bootstrap_text.BootstrapImplicits._
import com.karasiq.bootstrap_text.{Bootstrap, BootstrapComponent}

import scalatags.Text.all._

final class Tooltip(options: Map[String, Any]) extends BootstrapComponent {
  override def render(md: Modifier*): Modifier = new Modifier {
    override def applyTo(t: Element): Unit = {
      Bootstrap.dataProperties(options + ("toggle" → "tooltip")).applyTo(t)
    }
  }
}

/**
  * Inspired by the excellent jQuery.tipsy plugin written by Jason Frame;
  * Tooltips are an updated version, which don't rely on images, use CSS3 for animations, and data-attributes for local title storage.
  * Tooltips with zero-length titles are never displayed.
  * @see [[http://getbootstrap.com/javascript/#tooltips]]
  */
object Tooltip {
  def apply(content: Frag, placement: TooltipPlacement = TooltipPlacement.auto): Tooltip = {
    new Tooltip(Map("html" → true, "title" → content.render, "placement" → placement.toString))
  }
}
