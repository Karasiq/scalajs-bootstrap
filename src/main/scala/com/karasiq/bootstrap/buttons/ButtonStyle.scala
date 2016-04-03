package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ClassModifier

import scalatags.JsDom.all

sealed trait ButtonStyle extends ClassModifier {
  def styleClass: Option[String]

  override def classMod: all.Modifier = styleClass.classOpt
}

/**
  * @see [[https://getbootstrap.com/css/#buttons-options]]
  */
object ButtonStyle {
  final class BasicButtonStyle(val name: String) extends ButtonStyle {
    override def styleClass: Option[String] = Some(s"btn-$name")
  }

  final class OutlineButtonStyle(val name: String) extends ButtonStyle {
    override def styleClass: Option[String] = Some(s"btn-$name-outline")
  }

  def primary = new BasicButtonStyle("primary")
  def secondary = new BasicButtonStyle("secondary")
  def success = new BasicButtonStyle("success")
  def info = new BasicButtonStyle("info")
  def warning = new BasicButtonStyle("warning")
  def danger = new BasicButtonStyle("danger")
  def link: ButtonStyle = new BasicButtonStyle("link")
  def outline(basic: BasicButtonStyle) = new OutlineButtonStyle(basic.name)
}