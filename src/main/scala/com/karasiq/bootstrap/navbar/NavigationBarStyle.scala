package com.karasiq.bootstrap.navbar

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ModifierFactory

final class NavigationBarStyle private[navbar](style: String) extends ModifierFactory {
  def createModifier = s"navbar-$style".addClass
}

object NavigationBarStyle {
  // Style
  def default = new NavigationBarStyle("default")
  def inverse = new NavigationBarStyle("inverse")

  // Position
  def fixedTop = new NavigationBarStyle("fixed-top")
  def fixedBottom = new NavigationBarStyle("fixed-bottom")
  def staticTop = new NavigationBarStyle("static-top")
}