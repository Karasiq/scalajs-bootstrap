package com.karasiq.bootstrap.icons

import com.karasiq.bootstrap.BootstrapHtmlComponent
import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom

import scalatags.JsDom.all._

sealed class FontAwesomeIcon(name: String) extends BootstrapHtmlComponent[dom.html.Element] with IconModifier {
  override def renderTag(md: Modifier*): RenderedTag = {
    i(Seq("fa", s"fa-$name").map(_.addClass), md)
  }
}

/**
  * @see [[https://fortawesome.github.io/Font-Awesome/examples]]
  */
object FontAwesome {
  def apply(name: String, style: Modifier*): FontAwesomeIcon = new FontAwesomeIcon(name) {
    override def renderTag(md: Modifier*): RenderedTag = {
      super.renderTag(style ++ md:_*)
    }
  }
  def inverse: Modifier = "fa-inverse".addClass

  // Size modifiers
  def large: Modifier = "fa-lg".addClass
  def x2: Modifier = "fa-2x".addClass
  def x3: Modifier = "fa-3x".addClass
  def x4: Modifier = "fa-4x".addClass
  def x5: Modifier = "fa-5x".addClass

  // Fixed width
  def fixedWidth: Modifier = "fa-fw".addClass

  // List icons
  def list: Modifier = "fa-ul".addClass
  def line: Modifier = "fa-li".addClass

  // Bordered & Pulled icons
  def border: Modifier = "fa-border".addClass
  def pullRight: Modifier = "fa-pull-right".addClass
  def pullLeft: Modifier = "fa-pull-left".addClass

  // Animated icons
  def spin: Modifier = "fa-spin".addClass
  def pulse: Modifier = "fa-pulse".addClass

  // Rotated & Flipped
  def rotate90: Modifier = "fa-rotate-90".addClass
  def rotate180: Modifier = "fa-rotate-90".addClass
  def rotate270: Modifier = "fa-rotate-90".addClass
  def flipHorizontal: Modifier = "fa-flip-horizontal".addClass
  def flipVertical: Modifier = "fa-flip-vertical".addClass

  // Stacked icons
  def stacked(icons: Tag*): Tag = {
    span("fa-stack".addClass, icons)
  }

  def stacked1x: Modifier = "fa-stack-1x".addClass
  def stacked2x: Modifier = "fa-stack-2x".addClass
}