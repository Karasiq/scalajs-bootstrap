package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.BootstrapHtmlComponent
import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom

import scalatags.JsDom.all._

case class ButtonBuilder(style: ButtonStyle, size: ButtonSize, block: Boolean, active: Boolean, disabled: Boolean) extends BootstrapHtmlComponent[dom.html.Button] {
  def withStyle(style: ButtonStyle): ButtonBuilder = copy(style = style)
  def withSize(size: ButtonSize): ButtonBuilder = copy(size = size)

  @inline
  private def optionalClass(flag: Boolean, className: String): Option[String] = {
    if (flag) Some(className) else None
  }

  override def renderTag(md: Modifier*): RenderedTag = {
    val classList = Seq(style.styleClass, size.sizeClass, optionalClass(block, "btn-block"), optionalClass(active, "active"), optionalClass(disabled, "disabled"))
    button(`type` := "button", (Seq("btn") ++ classList.flatten).map(_.addClass), md)
  }
}
