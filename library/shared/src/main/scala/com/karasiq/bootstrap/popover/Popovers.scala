package com.karasiq.bootstrap.popover

import scala.language.postfixOps

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.tooltip.Tooltips
import com.karasiq.bootstrap.utils.Utils

trait Popovers { self: RenderingContext with BootstrapComponents with Tooltips with Utils ⇒
  import scalaTags.all._

  case class PopoverOptions(
      animation: Boolean = true,
      container: String = "",
      content: Frag = "",
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
        opt("content", content, ""),
        opt("delay", delay, ""),
        opt("html", html, false),
        opt("placement", placement, TooltipPlacement.right),
        opt("selector", selector, ""),
        opt("template", template, ""),
        opt("title", title, ""),
        opt("trigger", trigger, ""),
        opt("viewport", viewport, "")
      ).flatten.map(kv ⇒ kv._1 → kv._2.toString)
    }
  }

  trait Popover extends BootstrapComponent {
    def options: PopoverOptions
  }

  /** Add small overlays of content, like those on the iPad, to any element for housing secondary information. Popovers
    * whose both title and content are zero-length are never displayed.
    * @see
    *   [[http://getbootstrap.com/javascript/#popovers]]
    */
  trait PopoverFactory {
    def apply(title: Frag, content: Frag, placement: TooltipPlacement = TooltipPlacement.auto): Popover
  }

  val Popover: PopoverFactory
}
