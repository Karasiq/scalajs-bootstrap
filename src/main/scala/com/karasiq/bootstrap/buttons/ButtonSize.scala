package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ClassModifier

import scalatags.JsDom.all

sealed trait ButtonSize extends ClassModifier {
  def sizeClass: Option[String]

  override def classMod: all.Modifier = sizeClass.classOpt
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
}
