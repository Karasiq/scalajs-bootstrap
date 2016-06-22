package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ModifierFactory

import scalatags.JsDom.all._

final class ButtonStyle private[buttons](style: String) extends ModifierFactory {
  val className = s"btn-$style"
  val createModifier: Modifier = className.addClass
}

/**
  * @see [[https://getbootstrap.com/css/#buttons-options]]
  */
object ButtonStyle {
  lazy val default = new ButtonStyle("default")
  lazy val primary = new ButtonStyle("primary")
  lazy val success = new ButtonStyle("success")
  lazy val info = new ButtonStyle("info")
  lazy val warning = new ButtonStyle("warning")
  lazy val danger = new ButtonStyle("danger")
  lazy val link = new ButtonStyle("link")
}