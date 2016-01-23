package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.BootstrapHtmlComponent
import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom

import scalatags.JsDom.all._

case class ButtonBuilder(style: ButtonStyle = ButtonStyle.default, size: ButtonSize = ButtonSize.default, block: Boolean = false, active: Boolean = false, disabled: Boolean = false, classes: Seq[String] = Nil) extends BootstrapHtmlComponent[dom.html.Button] {
  @inline
  private def optionalClass(flag: Boolean, className: String): Option[String] = {
    if (flag) Some(className) else None
  }

  override def renderTag(md: Modifier*): RenderedTag = {
    val classList = Seq(style.styleClass, size.sizeClass, optionalClass(block, "btn-block"), optionalClass(active, "active"), optionalClass(disabled, "disabled"))
    button(`type` := "button", (Seq("btn") ++ classList.flatten ++ classes).map(_.addClass), md)
  }
}
