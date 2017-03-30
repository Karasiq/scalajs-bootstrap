package com.karasiq.bootstrap.icons

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.utils.ClassModifiers

import scala.language.{implicitConversions, postfixOps}

trait Icons { self: RenderingContext with ClassModifiers ⇒
  import scalaTags.all._

  trait IconModifier extends BootstrapComponent

  case object NoIcon extends IconModifier {
    override def render(md: Modifier*): Modifier = ()
  }

  final class BootstrapGlyphicon(name: String) extends BootstrapHtmlComponent with IconModifier {
    override def renderTag(md: Modifier*): Tag = {
      span(`class` := s"glyphicon glyphicon-$name", aria.hidden := true, md)
    }
  }

  object BootstrapGlyphicon {
    def apply(name: String): BootstrapGlyphicon = {
      new BootstrapGlyphicon(name)
    }
  }

  final class FontAwesomeIcon(name: String, styles: Seq[FontAwesomeStyle]) extends BootstrapHtmlComponent with IconModifier {
    override def renderTag(md: Modifier*): Tag = {
      i(Seq("fa", s"fa-$name").map(_.addClass), styles, aria.hidden := true, md)
    }
  }

  final class FontAwesomeStyle private[icons] (style: String) extends ModifierFactory {
    override val createModifier: Modifier = s"fa-$style".addClass
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

    lazy val inverse: FontAwesomeStyle = "inverse"

    // Size modifiers
    lazy val large: FontAwesomeStyle = "lg"
    lazy val x2: FontAwesomeStyle = "2x"
    lazy val x3: FontAwesomeStyle = "3x"
    lazy val x4: FontAwesomeStyle = "4x"
    lazy val x5: FontAwesomeStyle = "5x"

    // Fixed width
    lazy val fixedWidth: FontAwesomeStyle = "fw"

    // List icons
    lazy val list: FontAwesomeStyle = "ul"
    lazy val line: FontAwesomeStyle = "li"

    // Bordered & Pulled icons
    lazy val border: FontAwesomeStyle = "border"
    lazy val pullRight: FontAwesomeStyle = "pull-right"
    lazy val pullLeft: FontAwesomeStyle = "pull-left"

    // Animated icons
    lazy val spin: FontAwesomeStyle = "spin"
    lazy val pulse: FontAwesomeStyle = "pulse"

    // Rotated & Flipped
    lazy val rotate90: FontAwesomeStyle = "rotate-90"
    lazy val rotate180: FontAwesomeStyle = "rotate-90"
    lazy val rotate270: FontAwesomeStyle = "rotate-90"
    lazy val flipHorizontal: FontAwesomeStyle = "flip-horizontal"
    lazy val flipVertical: FontAwesomeStyle = "flip-vertical"

    // Stacked icons
    def stacked(icons: Tag*): Tag = {
      span(style("stack"), icons)
    }

    lazy val stacked1x: FontAwesomeStyle = "stack-1x"
    lazy val stacked2x: FontAwesomeStyle = "stack-2x"
  }

  implicit class BootstrapIconsOps(iconName: String) {
    def glyphicon: BootstrapGlyphicon = BootstrapGlyphicon(iconName)
    def fontAwesome(styles: FontAwesomeStyle*): FontAwesomeIcon = FontAwesome(iconName, styles:_*)
  }
}
