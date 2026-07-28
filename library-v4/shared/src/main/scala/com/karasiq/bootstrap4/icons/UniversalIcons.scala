package com.karasiq.bootstrap4.icons

import scala.language.implicitConversions

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.{ClassModifiers, RenderingContext}

trait UniversalIcons { self: RenderingContext with Icons with ClassModifiers with BootstrapComponents ⇒
  import scalaTags.all._

  object Icon extends IconFactory {
    def apply(iconName: String): FontAwesomeIcon = {
      faFw(iconName)
    }

    def glyphicon(iconName: String): BootstrapGlyphicon = {
      BootstrapGlyphicon(iconName)
    }

    def fa(iconName: String, styles: FontAwesomeStyle*): FontAwesomeIcon = {
      FontAwesome(iconName, styles: _*)
    }

    def faFw(iconName: String, styles: FontAwesomeStyle*): FontAwesomeIcon = {
      FontAwesome(iconName, FontAwesome.fixedWidth +: styles: _*)
    }
  }

  final class BootstrapGlyphicon(val iconName: String) extends BootstrapHtmlComponent with IconModifier {
    override def renderTag(md: ModifierT*): TagT = {
      span(`class` := "glyphicon glyphicon-" + iconName, aria.hidden := true)(md: _*)
    }
  }

  object BootstrapGlyphicon {
    def apply(name: String): BootstrapGlyphicon = {
      new BootstrapGlyphicon(name)
    }
  }

  final class FontAwesomeIcon(val iconName: String, val styles: Seq[FontAwesomeStyle])
      extends BootstrapHtmlComponent
      with IconModifier {
    override def renderTag(md: ModifierT*): TagT = {
      i(`class` := "fa fa-" + iconName, styles, aria.hidden := true)(md: _*)
    }
  }

  final class FontAwesomeStyle private[icons] (val styleName: String) extends ModifierFactory {
    override val createModifier = ("fa-" + styleName).addClass
  }

  /** @see
    *   [[https://fortawesome.github.io/Font-Awesome/examples]]
    */
  object FontAwesome {
    def apply(name: String, styles: FontAwesomeStyle*): FontAwesomeIcon = {
      new FontAwesomeIcon(name, styles)
    }

    @inline
    private[this] implicit def faStyle(str: String): FontAwesomeStyle = new FontAwesomeStyle(str)

    lazy val inverse: FontAwesomeStyle = "inverse"

    // Size modifiers
    lazy val large: FontAwesomeStyle = "lg"
    lazy val x2: FontAwesomeStyle    = "2x"
    lazy val x3: FontAwesomeStyle    = "3x"
    lazy val x4: FontAwesomeStyle    = "4x"
    lazy val x5: FontAwesomeStyle    = "5x"

    // Fixed width
    lazy val fixedWidth: FontAwesomeStyle = "fw"

    // List icons
    lazy val list: FontAwesomeStyle = "ul"
    lazy val line: FontAwesomeStyle = "li"

    // Bordered & Pulled icons
    lazy val border: FontAwesomeStyle    = "border"
    lazy val pullRight: FontAwesomeStyle = "pull-right"
    lazy val pullLeft: FontAwesomeStyle  = "pull-left"

    // Animated icons
    lazy val spin: FontAwesomeStyle  = "spin"
    lazy val pulse: FontAwesomeStyle = "pulse"

    // Rotated & Flipped
    lazy val rotate90: FontAwesomeStyle       = "rotate-90"
    lazy val rotate180: FontAwesomeStyle      = "rotate-90"
    lazy val rotate270: FontAwesomeStyle      = "rotate-90"
    lazy val flipHorizontal: FontAwesomeStyle = "flip-horizontal"
    lazy val flipVertical: FontAwesomeStyle   = "flip-vertical"

    // Stacked icons
    def stacked(icons: Tag*): Tag = {
      span(faStyle("stack"), icons)
    }

    lazy val stacked1x: FontAwesomeStyle = "stack-1x"
    lazy val stacked2x: FontAwesomeStyle = "stack-2x"
  }

  // noinspection SpellCheckingInspection
  implicit class BootstrapIconsOps(iconName: String) {
    def glyphicon: BootstrapGlyphicon                           = Icon.glyphicon(iconName)
    def fontAwesome(styles: FontAwesomeStyle*): FontAwesomeIcon = Icon.fa(iconName, styles: _*)

    // Shortcuts
    def faIcon: FontAwesomeIcon   = this.fontAwesome()
    def faFwIcon: FontAwesomeIcon = this.fontAwesome(FontAwesome.fixedWidth)
  }
}
