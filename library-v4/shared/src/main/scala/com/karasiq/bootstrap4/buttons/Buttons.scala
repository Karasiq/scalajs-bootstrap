package com.karasiq.bootstrap4.buttons

import scala.language.postfixOps

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.{ClassModifiers, RenderingContext}
import com.karasiq.bootstrap4.utils.Utils

trait Buttons extends ButtonStyles with ButtonGroups with ButtonStates {
  self: RenderingContext with BootstrapComponents with ClassModifiers with Utils ⇒
  import scalaTags.all._

  type Button <: AbstractButton
  val Button: ButtonFactory

  trait AbstractButton extends BootstrapHtmlComponent {
    def style: ButtonStyle
    def size: ButtonSize
    def block: Boolean
    def active: Boolean
    def disabled: Boolean
  }

  trait ButtonFactory {

    /** Creates button
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
    def apply(
        style: ButtonStyle = ButtonStyle.default,
        size: ButtonSize = ButtonSize.default,
        block: Boolean = false,
        active: Boolean = false,
        disabled: Boolean = false
    ): Button
  }
}
