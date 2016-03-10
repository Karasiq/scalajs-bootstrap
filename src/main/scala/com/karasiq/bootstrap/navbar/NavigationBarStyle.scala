package com.karasiq.bootstrap.navbar

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ClassModifier

sealed trait NavigationBarStyle extends ClassModifier

object NavigationBarStyle {
  private def style(className: String): NavigationBarStyle = new NavigationBarStyle {
    def classMod = s"navbar-$className".addClass
  }

  def default = style("default")
  def inverse = style("inverse")

  def fixedTop = style("fixed-top")
  def fixedBottom = style("fixed-bottom")
  def staticTop = style("static-top")
}