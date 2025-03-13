package com.karasiq.bootstrap4.buttons

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap4.utils.Utils

trait ButtonStyles { self: RenderingContext with Utils ⇒
  import scalaTags.all._

  sealed trait ButtonSize extends ModifierFactory

  object DefaultButtonSize extends ButtonSize {
    val createModifier = Bootstrap.noModifier
  }

  final class ButtonSizeValue private[buttons] (size: String) extends ButtonSize {
    val className      = s"btn-$size"
    val createModifier = className.addClass
  }

  /** @see
    *   [[https://getbootstrap.com/docs/4.0/components/buttons/#sizes]]
    */
  object ButtonSize {
    def default    = DefaultButtonSize
    lazy val large = new ButtonSizeValue("lg")
    lazy val small = new ButtonSizeValue("sm")
  }

  class ButtonStyle private[buttons] (style: String) extends ModifierFactory {
    val className      = s"btn-$style"
    val createModifier = className.addClass
  }

  /** @see
    *   [[https://getbootstrap.com/docs/4.0/components/buttons/]]
    */
  object ButtonStyle {
    def default        = primary
    lazy val primary   = new ButtonStyle("primary")
    lazy val secondary = new ButtonStyle("secondary")
    lazy val success   = new ButtonStyle("success")
    lazy val info      = new ButtonStyle("info")
    lazy val warning   = new ButtonStyle("warning")
    lazy val danger    = new ButtonStyle("danger")
    lazy val light     = new ButtonStyle("light")
    lazy val dark      = new ButtonStyle("dark")
    lazy val link      = new ButtonStyle("link")
  }

  class ButtonOutline private[buttons] (style: String) extends ButtonStyle(s"outline-$style")

  object ButtonOutline {
    lazy val primary   = new ButtonOutline("primary")
    lazy val secondary = new ButtonOutline("secondary")
    lazy val success   = new ButtonOutline("success")
    lazy val info      = new ButtonOutline("info")
    lazy val warning   = new ButtonOutline("warning")
    lazy val danger    = new ButtonOutline("danger")
    lazy val light     = new ButtonOutline("light")
    lazy val dark      = new ButtonOutline("dark")
    lazy val white     = new ButtonOutline("white")
  }
}
