package com.karasiq.bootstrap.tooltip

import com.karasiq.bootstrap.BootstrapComponent
import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom.Element
import org.scalajs.jquery.jQuery

import scala.scalajs.js
import scalatags.JsDom.all._

final class Tooltip(options: TooltipOptions) extends BootstrapComponent {
  override def render(md: Modifier*): Modifier = new Modifier {
    override def applyTo(t: Element): Unit = {
      jQuery(t).tooltip(options)
      md.foreach(_.applyTo(t))
    }
  }
}

object Tooltip {
  def apply(content: Frag, placement: TooltipPlacement = TooltipPlacement.auto): Tooltip = {
    val options = js.Object().asInstanceOf[TooltipOptions]
    options.html = true
    options.title = content.render
    options.placement = placement.asString
    new Tooltip(options)
  }
}
