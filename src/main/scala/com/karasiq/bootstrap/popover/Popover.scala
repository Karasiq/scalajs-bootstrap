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

/**
  * Add small overlays of content, like those on the iPad, to any element for housing secondary information.
  * Popovers whose both title and content are zero-length are never displayed.
  * @see [[http://getbootstrap.com/javascript/#popovers]]
  */
object Popover {
  def apply(title: Frag, content: Frag, placement: TooltipPlacement = TooltipPlacement.auto): Popover = {
    val options = js.Object().asInstanceOf[PopoverOptions]
    options.html = true
    options.title = title.render
    options.content = content.render
    options.placement = placement.toString
    new Popover(options)
  }
}