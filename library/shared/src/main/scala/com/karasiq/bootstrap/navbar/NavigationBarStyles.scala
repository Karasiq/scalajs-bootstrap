package com.karasiq.bootstrap.navbar

import com.karasiq.bootstrap.context.RenderingContext

trait NavigationBarStyles { self: RenderingContext ⇒
  import scalaTags.all._

  final class NavigationBarStyle private[navbar] (style: String) extends ModifierFactory {
    val className      = s"navbar-$style"
    val createModifier = className.addClass
  }

  object NavigationBarStyle {
    // Style
    lazy val default = new NavigationBarStyle("default")
    lazy val inverse = new NavigationBarStyle("inverse")

    // Position
    lazy val fixedTop    = new NavigationBarStyle("fixed-top")
    lazy val fixedBottom = new NavigationBarStyle("fixed-bottom")
    lazy val staticTop   = new NavigationBarStyle("static-top")
  }
}
