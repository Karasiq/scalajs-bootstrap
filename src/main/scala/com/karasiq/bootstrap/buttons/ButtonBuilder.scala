package com.karasiq.bootstrap.buttons

import org.scalajs.dom

import scalatags.JsDom.all._

case class ButtonBuilder(style: ButtonStyle = ButtonStyle.default, size: ButtonSize = ButtonSize.default, block: Boolean = false, active: Boolean = false, disabled: Boolean = false, classes: Seq[String] = Nil) {
  @inline
  private def optionalClass(flag: Boolean, className: String): Option[String] = {
    if (flag) Some(className) else None
  }

  def build: ConcreteHtmlTag[dom.html.Button] = {
    val classList = Seq(style.styleClass, size.sizeClass, optionalClass(block, "btn-block"), optionalClass(active, "active"), optionalClass(disabled, "disabled"))
    button(`type` := "button", `class` := (classList.flatten ++ classes).mkString(" "))
  }
}
