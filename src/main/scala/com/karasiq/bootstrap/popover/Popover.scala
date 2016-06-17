package com.karasiq.bootstrap.popover

import com.karasiq.bootstrap.BootstrapComponent
import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.tooltip.TooltipPlacement
import org.scalajs.dom.Element
import org.scalajs.jquery.jQuery

import scala.scalajs.js
import scalatags.JsDom.all._

class Popover(title: String, content: Modifier, placement: TooltipPlacement) extends BootstrapComponent {
  override def render(md: Modifier*): Modifier = new Modifier {
    override def applyTo(t: Element): Unit = {
      val options = js.Object().asInstanceOf[PopoverOptions]
      options.html = true
      options.title = title
      options.content = div(content).render
      jQuery(t).popover(options)
    }
  }
}

object Popover {
  def apply(title: String, content: Modifier, placement: TooltipPlacement = TooltipPlacement.auto): Popover = {
    new Popover(title, content, placement)
  }
}