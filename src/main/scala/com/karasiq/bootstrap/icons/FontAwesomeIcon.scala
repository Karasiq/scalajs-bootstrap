package com.karasiq.bootstrap.icons

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.{BootstrapHtmlComponent, ModifierFactory}
import org.scalajs.dom

import scala.language.implicitConversions
import scalatags.JsDom.all._

final class FontAwesomeIcon(name: String, styles: Seq[FontAwesomeStyle]) extends BootstrapHtmlComponent[dom.html.Element] with IconModifier {
  override def renderTag(md: Modifier*): RenderedTag = {
    i(Seq("fa", s"fa-$name").map(_.addClass), styles, aria.hidden := true, md)
  }
}

final class FontAwesomeStyle private[icons] (style: String) extends ModifierFactory {
  override def createModifier: Modifier = s"fa-$style".addClass
}

/**
  * @see [[https://fortawesome.github.io/Font-Awesome/examples]]
  */
object FontAwesome {
  def apply(name: String, styles: FontAwesomeStyle*): FontAwesomeIcon = {
    new FontAwesomeIcon(name, styles)
  }

  private implicit def style(str: String): FontAwesomeStyle = {
    new FontAwesomeStyle(str)
  }

  def inverse: FontAwesomeStyle = "inverse"

  // Size modifiers
  def large: FontAwesomeStyle = "lg"
  def x2: FontAwesomeStyle = "2x"
  def x3: FontAwesomeStyle = "3x"
  def x4: FontAwesomeStyle = "4x"
  def x5: FontAwesomeStyle = "5x"

  // Fixed width
  def fixedWidth: FontAwesomeStyle = "fw"

  // List icons
  def list: FontAwesomeStyle = "ul"
  def line: FontAwesomeStyle = "li"

  // Bordered & Pulled icons
  def border: FontAwesomeStyle = "border"
  def pullRight: FontAwesomeStyle = "pull-right"
  def pullLeft: FontAwesomeStyle = "pull-left"

  // Animated icons
  def spin: FontAwesomeStyle = "spin"
  def pulse: FontAwesomeStyle = "pulse"

  // Rotated & Flipped
  def rotate90: FontAwesomeStyle = "rotate-90"
  def rotate180: FontAwesomeStyle = "rotate-90"
  def rotate270: FontAwesomeStyle = "rotate-90"
  def flipHorizontal: FontAwesomeStyle = "flip-horizontal"
  def flipVertical: FontAwesomeStyle = "flip-vertical"

  // Stacked icons
  def stacked(icons: Tag*): Tag = {
    span(style("stack"), icons)
  }

  def stacked1x: FontAwesomeStyle = "stack-1x"
  def stacked2x: FontAwesomeStyle = "stack-2x"
}