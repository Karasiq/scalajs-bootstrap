package com.karasiq.bootstrap4.navbar

import com.karasiq.bootstrap.context.RenderingContext

trait NavigationBarStyles { self: RenderingContext ⇒
  import scalaTags.all._

  class NavigationBarStyle private[navbar] (style: String) extends ModifierFactory {
    val className      = s"navbar-$style"
    val createModifier = className.addClass
  }

  object NavigationBarStyle {
    // Style
    lazy val light = new NavigationBarStyle("light")
    lazy val dark  = new NavigationBarStyle("dark")

    // Position
    lazy val fixedTop    = new NavigationBarStyle("fixed-top")
    lazy val fixedBottom = new NavigationBarStyle("fixed-bottom")
    lazy val stickyTop   = new NavigationBarStyle("sticky-top")
  }

  final class NavigationBarExpand private[navbar] (size: String) extends NavigationBarStyle("expand-" + size)

  object NavigationBarExpand {
    lazy val sm = new NavigationBarExpand("sm")
    lazy val md = new NavigationBarExpand("md")
    lazy val lg = new NavigationBarExpand("lg")
    lazy val xl = new NavigationBarExpand("xl")
  }
}
