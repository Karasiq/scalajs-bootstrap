package com.karasiq.bootstrap.tooltip

import scala.language.{implicitConversions, postfixOps}

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext

trait Tooltips extends TooltipStyles { self: RenderingContext with BootstrapComponents ⇒
  import scalaTags.all._

  type Tooltip <: AbstractTooltip
  val Tooltip: TooltipFactory

  case class TooltipOptions(
      animation: Boolean = true,
      container: String = "",
      delay: String = "",
      html: Boolean = false,
      placement: TooltipPlacement = TooltipPlacement.right,
      selector: String = "",
      template: String = "",
      title: Frag = "",
      trigger: String = "",
      viewport: String = ""
  ) {
    def toStrings: Seq[(String, String)] = {
      def opt[T](name: String, value: T, default: T) = Option(name → value).filterNot(_._2 == default)
      Seq(
        opt("animation", animation, true),
        opt("container", container, ""),
        opt("delay", delay, ""),
        opt("html", html, false),
        opt("placement", placement.placement, TooltipPlacement.right),
        opt("selector", selector, ""),
        opt("template", template, ""),
        opt("title", title, ""),
        opt("trigger", trigger, ""),
        opt("viewport", viewport, "")
      ).flatten.map(kv ⇒ kv._1 → kv._2.toString)
    }
  }

  trait AbstractTooltip extends BootstrapComponent {
    def options: TooltipOptions
  }

  /** Inspired by the excellent jQuery.tipsy plugin written by Jason Frame; Tooltips are an updated version, which don't
    * rely on images, use CSS3 for animations, and data-attributes for local title storage. Tooltips with zero-length
    * titles are never displayed.
    * @see
    *   [[http://getbootstrap.com/javascript/#tooltips]]
    */
  trait TooltipFactory {
    def apply(content: Frag, placement: TooltipPlacement = TooltipPlacement.auto): AbstractTooltip
  }
}
