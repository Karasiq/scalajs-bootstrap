package com.karasiq.bootstrap.tooltip

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.utils.Utils

import scala.language.postfixOps

trait TextTooltips { self: RenderingContext with BootstrapComponents with Tooltips with Utils ⇒
  import BootstrapAttrs._

  import scalaTags.all._

  class TextTooltip(val options: TooltipOptions) extends Tooltip {
    override def render(md: Modifier*): Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        ((`data-toggle` := "tooltip") +: Bootstrap.dataProperties(options.toStrings:_*) +: md).applyTo(t)
      }
    }
  }

  /**
    * Inspired by the excellent jQuery.tipsy plugin written by Jason Frame;
    * Tooltips are an updated version, which don't rely on images, use CSS3 for animations, and data-attributes for local title storage.
    * Tooltips with zero-length titles are never displayed.
    * @see [[http://getbootstrap.com/javascript/#tooltips]]
    */
  object Tooltip extends TooltipFactory {
    def apply(content: Frag, placement: TooltipPlacement): Tooltip = {
      new TextTooltip(TooltipOptions(html = true, title = content, placement = placement))
    }
  }
}
