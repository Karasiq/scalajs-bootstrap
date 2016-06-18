package com.karasiq.bootstrap.popover

import com.karasiq.bootstrap.BootstrapComponent
import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.tooltip.TooltipPlacement
import org.scalajs.dom.Element
import org.scalajs.jquery.jQuery

import scala.scalajs.js
import scalatags.JsDom.all._

final class Popover(options: PopoverOptions) extends BootstrapComponent {
  override def render(md: Modifier*): Modifier = new Modifier {
    override def applyTo(t: Element): Unit = {
      jQuery(t).popover(options)
      md.foreach(_.applyTo(t))
    }
  }
}

object Popover {
  def apply(title: String, content: Frag, placement: TooltipPlacement = TooltipPlacement.auto): Popover = {
    val options = js.Object().asInstanceOf[PopoverOptions]
    options.html = true
    options.title = title
    options.content = content.render
    options.placement = placement.asString
    new Popover(options)
  }
}