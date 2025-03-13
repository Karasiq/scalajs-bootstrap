package com.karasiq.bootstrap.buttons

import scala.language.postfixOps

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.{ClassModifiers, RenderingContext}

trait UniversalButtons extends UniversalButtonStates with UniversalButtonGroups {
  self: RenderingContext with BootstrapComponents with ClassModifiers with Buttons ⇒
  import scalaTags.all._

  type Button = ButtonBuilder
  object Button extends ButtonFactory {

    /** Shortcut to [[com.karasiq.bootstrap.buttons.UniversalButtons.ButtonBuilder ButtonBuilder]].
      */
    def apply(
        style: ButtonStyle = ButtonStyle.default,
        size: ButtonSize = ButtonSize.default,
        block: Boolean = false,
        active: Boolean = false,
        disabled: Boolean = false
    ): ButtonBuilder = {
      ButtonBuilder(style, size, block, active, disabled)
    }
  }

  trait UniversalButton extends AbstractButton {
    override def renderTag(md: ModifierT*): TagT = {
      @inline def optional(flag: Boolean, className: String) = if (flag) Some(className) else None
      val classList = Seq(
        Some("btn"),
        optional(block, "btn-block"),
        optional(active, "active"),
        optional(disabled, "disabled")
      ).flatten.map(_.addClass)
      scalaTags.tags.button(`type` := "button", classList, style, size)(md: _*)
    }
  }

  /** Button builder
    * @param style
    *   Use any of the available button classes to quickly create a styled button
    * @param size
    *   Fancy larger or smaller buttons? Add `.btn-lg`, `.btn-sm`, or `.btn-xs` for additional sizes
    * @param block
    *   Create block level buttons—those that span the full width of a parent— by adding `.btn-block`
    * @param active
    *   Buttons will appear pressed (with a darker background, darker border, and inset shadow) when active
    * @param disabled
    *   Make buttons look unclickable by fading them back with `opacity`
    * @see
    *   [[http://getbootstrap.com/css/#buttons]]
    */
  case class ButtonBuilder(
      style: ButtonStyle = ButtonStyle.default,
      size: ButtonSize = ButtonSize.default,
      block: Boolean = false,
      active: Boolean = false,
      disabled: Boolean = false
  ) extends UniversalButton {

    def withStyle(style: ButtonStyle): ButtonBuilder = copy(style = style)
    def withSize(size: ButtonSize): ButtonBuilder    = copy(size = size)
  }
}
