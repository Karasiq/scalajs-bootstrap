package com.karasiq.bootstrap_text.buttons

import com.karasiq.bootstrap_text.BootstrapImplicits._
import com.karasiq.bootstrap_text.ModifierFactory

import scalatags.Text.all._

sealed trait ButtonSize extends ModifierFactory
object DefaultButtonSize extends ButtonSize {
  val createModifier: Modifier = ()
}
final class ButtonSizeValue private[buttons](size: String) extends ButtonSize {
  val className = s"btn-$size"
  val createModifier = className.addClass
}

/**
  * @see [[https://getbootstrap.com/css/#buttons-sizes]]
  */
object ButtonSize {
  def default = DefaultButtonSize
  lazy val large = new ButtonSizeValue("lg")
  lazy val small = new ButtonSizeValue("sm")
  lazy val extraSmall = new ButtonSizeValue("xs")
}
