package com.karasiq.bootstrap_text.navbar

import com.karasiq.bootstrap_text.BootstrapImplicits._
import com.karasiq.bootstrap_text.ModifierFactory

final class NavigationBarStyle private[navbar](style: String) extends ModifierFactory {
  val className = s"navbar-$style"
  val createModifier = className.addClass
}

object NavigationBarStyle {
  // Style
  lazy val default = new NavigationBarStyle("default")
  lazy val inverse = new NavigationBarStyle("inverse")

  // Position
  lazy val fixedTop = new NavigationBarStyle("fixed-top")
  lazy val fixedBottom = new NavigationBarStyle("fixed-bottom")
  lazy val staticTop = new NavigationBarStyle("static-top")
}