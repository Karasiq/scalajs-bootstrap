package com.karasiq.bootstrap.buttons

import org.scalajs.dom

case class ButtonGroup(size: ButtonGroupSize, buttons: dom.html.Button*)

case class ButtonToolbar(buttonGroups: ButtonGroup*)

sealed trait ButtonGroupSize {
  def sizeClass: Option[String]
}

object ButtonGroupSize {
  private final class BasicButtonGroupSize(name: String) extends ButtonGroupSize {
    override def sizeClass: Option[String] = Some(s"btn-group-$name")
  }

  def default: ButtonGroupSize = new ButtonGroupSize {
    override def sizeClass: Option[String] = None
  }

  def large: ButtonGroupSize = new BasicButtonGroupSize("lg")
  def small: ButtonGroupSize = new BasicButtonGroupSize("sm")
  def extraSmall: ButtonGroupSize = new BasicButtonGroupSize("xs")
}