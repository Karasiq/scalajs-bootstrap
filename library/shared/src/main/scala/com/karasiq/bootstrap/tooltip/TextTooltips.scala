package com.karasiq.bootstrap.tooltip

import scala.language.postfixOps

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.utils.Utils

trait TextTooltips { self: RenderingContext with BootstrapComponents with Tooltips with Utils ⇒
  import scalaTags.all._

  import BootstrapAttrs._

  class TextTooltip(val options: TooltipOptions) extends AbstractTooltip {
    override def render(md: Modifier*): Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        ((`data-toggle` := "tooltip") +: Bootstrap.dataProps(options.toStrings:_*) +: md).applyTo(t)
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
    def apply(content: Frag, placement: TooltipPlacement): AbstractTooltip = {
      new TextTooltip(TooltipOptions(html = true, title = content, placement = placement))
    }
  }
}
