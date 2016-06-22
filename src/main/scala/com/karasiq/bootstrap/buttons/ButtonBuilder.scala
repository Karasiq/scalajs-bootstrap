package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.BootstrapHtmlComponent
import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom

import scalatags.JsDom.all._

/**
  * Button builder
  * @param style Use any of the available button classes to quickly create a styled button
  * @param size Fancy larger or smaller buttons? Add `.btn-lg`, `.btn-sm`, or `.btn-xs` for additional sizes
  * @param block Create block level buttons—those that span the full width of a parent— by adding `.btn-block`
  * @param active Buttons will appear pressed (with a darker background, darker border, and inset shadow) when active
  * @param disabled Make buttons look unclickable by fading them back with `opacity`
  * @see [[http://getbootstrap.com/css/#buttons]]
  */
case class ButtonBuilder(style: ButtonStyle = ButtonStyle.default, size: ButtonSize = ButtonSize.default, block: Boolean = false, active: Boolean = false, disabled: Boolean = false) extends BootstrapHtmlComponent[dom.html.Button] {
  def withStyle(style: ButtonStyle): ButtonBuilder = copy(style = style)
  def withSize(size: ButtonSize): ButtonBuilder = copy(size = size)

  override def renderTag(md: Modifier*): RenderedTag = {
    @inline def optional(flag: Boolean, className: String) = if (flag) Some(className) else None
    val classList = Seq(Some("btn"), optional(block, "btn-block"), optional(active, "active"), optional(disabled, "disabled")).flatten.map(_.addClass)
    button(`type` := "button", classList, style, size, md)
  }
}
