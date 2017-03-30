package com.karasiq.bootstrap.popover

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.JSRenderingContext
import com.karasiq.bootstrap.tooltip.Tooltips
import org.scalajs.jquery

import scala.language.postfixOps

trait JSPopovers { self: JSRenderingContext with BootstrapComponents with Popovers with Tooltips ⇒
  import scalaTags.all._

  class JSPopover(val options: PopoverOptions) extends Popover {
    def toggle: Modifier = new Modifier {
      def applyTo(t: Element): Unit = {
        val jsOptions = scalajs.js.Object().asInstanceOf[JSPopoverOptions]
        def set(value: String, f: String ⇒ Unit) = if (value.nonEmpty) f(value)
        jsOptions.animation = options.animation
        jsOptions.content = options.content.render
        jsOptions.title = options.title.render
        jsOptions.html = options.html
        jsOptions.placement = options.placement.toString
        set(options.container, jsOptions.container = _)
        set(options.delay, jsOptions.delay = _)
        set(options.selector, jsOptions.selector = _)
        set(options.template, jsOptions.template = _)
        set(options.trigger, jsOptions.trigger = _)
        set(options.viewport, jsOptions.viewport = _)
        jquery.jQuery(t).popover(jsOptions)
      }
    }

    override def render(md: Modifier*): Modifier = {
      toggle +: md
    }
  }

  /**
    * Add small overlays of content, like those on the iPad, to any element for housing secondary information.
    * Popovers whose both title and content are zero-length are never displayed.
    * @see [[http://getbootstrap.com/javascript/#popovers]]
    */
  object Popover extends PopoverFactory {
    def apply(title: Frag, content: Frag, placement: TooltipPlacement = TooltipPlacement.auto): JSPopover = {
      new JSPopover(PopoverOptions(html = true, title = title, content = content, placement = placement))
    }
  }
}
