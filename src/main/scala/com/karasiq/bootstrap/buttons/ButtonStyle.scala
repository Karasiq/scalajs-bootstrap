package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ModifierFactory

import scalatags.JsDom.all._

sealed trait ButtonStyle extends ModifierFactory {
  def styleClass: Option[String]

  override def createModifier: Modifier = styleClass.classOpt
}

/**
  * @see [[https://getbootstrap.com/css/#buttons-options]]
  */
object ButtonStyle {
  private final class BasicButtonStyle(name: String) extends ButtonStyle {
    override def styleClass: Option[String] = Some(s"btn-$name")
  }

  def default: ButtonStyle = new BasicButtonStyle("default")
  def primary: ButtonStyle = new BasicButtonStyle("primary")
  def success: ButtonStyle = new BasicButtonStyle("success")
  def info: ButtonStyle = new BasicButtonStyle("info")
  def warning: ButtonStyle = new BasicButtonStyle("warning")
  def danger: ButtonStyle = new BasicButtonStyle("danger")
  def link: ButtonStyle = new BasicButtonStyle("link")
}