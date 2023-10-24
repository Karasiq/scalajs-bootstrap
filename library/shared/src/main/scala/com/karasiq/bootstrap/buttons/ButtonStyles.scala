package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.utils.Utils

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
    *   [[https://getbootstrap.com/css/#buttons-sizes]]
    */
  object ButtonSize {
    def default         = DefaultButtonSize
    lazy val large      = new ButtonSizeValue("lg")
    lazy val small      = new ButtonSizeValue("sm")
    lazy val extraSmall = new ButtonSizeValue("xs")
  }

  final class ButtonStyle private[buttons] (style: String) extends ModifierFactory {
    val className      = s"btn-$style"
    val createModifier = className.addClass
  }

  /** @see
    *   [[https://getbootstrap.com/css/#buttons-options]]
    */
  object ButtonStyle {
    lazy val default = new ButtonStyle("default")
    lazy val primary = new ButtonStyle("primary")
    lazy val success = new ButtonStyle("success")
    lazy val info    = new ButtonStyle("info")
    lazy val warning = new ButtonStyle("warning")
    lazy val danger  = new ButtonStyle("danger")
    lazy val link    = new ButtonStyle("link")
  }
}
