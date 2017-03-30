package com.karasiq.bootstrap.popover

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.tooltip.Tooltips
import com.karasiq.bootstrap.utils.Utils

import scala.language.postfixOps

trait TextPopovers { self: RenderingContext with BootstrapComponents with Tooltips with Popovers with Utils ⇒
  import BootstrapAttrs._

  import scalaTags.all._
  
  class TextPopover(val options: PopoverOptions) extends Popover {
    override def render(md: Modifier*): Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        ((`data-toggle` := "popover") +: Bootstrap.dataProperties(options.toStrings:_*) +: md).applyTo(t)
      }
    }
  }

  /**
    * Add small overlays of content, like those on the iPad, to any element for housing secondary information.
    * Popovers whose both title and content are zero-length are never displayed.
    * @see [[http://getbootstrap.com/javascript/#popovers]]
    */
  object Popover extends PopoverFactory {
    def apply(title: Frag, content: Frag, placement: TooltipPlacement = TooltipPlacement.auto): Popover = {
      val options = PopoverOptions(html = true, title = title, content = content, placement = placement)
      new TextPopover(options)
    }
  }
}
