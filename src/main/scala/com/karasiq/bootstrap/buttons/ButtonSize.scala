package com.karasiq.bootstrap.buttons

sealed trait ButtonSize {
  def sizeClass: Option[String]
}

/**
  * @see [[https://getbootstrap.com/css/#buttons-sizes]]
  */
object ButtonSize {
  private final class BasicButtonSize(name: String) extends ButtonSize {
    override def sizeClass: Option[String] = Some(s"btn-$name")
  }

  def default: ButtonSize = new ButtonSize {
    override def sizeClass: Option[String] = None
  }

  def large: ButtonSize = new BasicButtonSize("lg")
  def small: ButtonSize = new BasicButtonSize("sm")
  def extraSmall: ButtonSize = new BasicButtonSize("xs")
}
