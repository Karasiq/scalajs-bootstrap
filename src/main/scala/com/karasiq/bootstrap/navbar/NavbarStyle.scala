package com.karasiq.bootstrap.navbar

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ClassModifier

import scalatags.JsDom.all._

sealed trait NavbarStyle extends ClassModifier

object NavbarStyle {
  private def style(className: String): NavbarStyle = new NavbarStyle {
    def classMod = s"navbar-$className".addClass
  }

  def light = style("light")
  def dark = style("dark")

  def fixedTop = style("fixed-top")
  def fixedBottom = style("fixed-bottom")
  def staticTop = style("static-top")
}

sealed trait NavbarBackground extends NavbarStyle

object NavbarBackground {
  private def scheme(name: String) = new NavbarBackground {
    override def classMod: Modifier = s"bg-$name".addClass
  }

  def primary = scheme("primary")
  def inverse = scheme("inverse")
  def faded = scheme("faded")
  def color(value: String) = new NavbarBackground {
    override def classMod: Modifier = backgroundColor := value
  }
}