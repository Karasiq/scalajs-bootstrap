package com.karasiq.bootstrap_text.popover

import com.karasiq.bootstrap_text.BootstrapImplicits._
import com.karasiq.bootstrap_text.tooltip.TooltipPlacement
import com.karasiq.bootstrap_text.{Bootstrap, BootstrapComponent}

import scalatags.Text.all._

final class Popover(options: Map[String, Any]) extends BootstrapComponent {
  override def render(md: Modifier*): Modifier = new Modifier {
    override def applyTo(t: Element): Unit = {
      Bootstrap.dataProperties(options + ("toggle" → "popover")).applyTo(t)
    }
  }
}

/**
  * Add small overlays of content, like those on the iPad, to any element for housing secondary information.
  * Popovers whose both title and content are zero-length are never displayed.
  * @see [[http://getbootstrap.com/javascript/#popovers]]
  */
object Popover {
  def apply(title: Frag, content: Frag, placement: TooltipPlacement = TooltipPlacement.auto): Popover = {
    new Popover(Map(
      "html" → true,
      "title" → title.render,
      "content" → content.render,
      "placement" → placement.toString
    ))
  }
}