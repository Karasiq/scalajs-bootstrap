package com.karasiq.bootstrap4.utils

import java.util.UUID

import scala.language.postfixOps

import com.karasiq.bootstrap.context.{ClassModifiers, RenderingContext}
import com.karasiq.bootstrap4.buttons.Buttons
import com.karasiq.bootstrap4.icons.Icons

//noinspection TypeAnnotation
trait UniversalUtils { self: RenderingContext with Icons with Buttons with ClassModifiers with Utils ⇒
  import scalaTags.all._

  type Bootstrap = UniversalUtils with AbstractUtils
  object Bootstrap extends UniversalUtils with AbstractUtils

  type BootstrapAttrs = DefaultBootstrapAttrs
  object BootstrapAttrs extends DefaultBootstrapAttrs

  trait UniversalUtils extends AbstractUtils {

    /** A lightweight, flexible component that can optionally extend the entire viewport to showcase key content on your
      * site.
      * @see
      *   [[https://getbootstrap.com/docs/4.0/components/jumbotron/]]
      */
    lazy val jumbotron: Tag = div(`class` := "jumbotron")

    /** Easily highlight new or unread items by adding a badge to links, Bootstrap navs, and more.
      * @see
      *   [[https://getbootstrap.com/components/#badges]]
      */
    lazy val badge: Tag = span(`class` := "badge")

    /** Default button
      */
    lazy val button: Tag = {
      Button().renderTag()
    }

    /** Default icon
      * @param name
      *   Icon name
      * @see
      *   [[https://getbootstrap.com/components/#glyphicons]]
      */
    def icon(name: String): IconModifier = {
      Icon(name)
    }

    /** Generates unique element ID
      */
    def newId: String = {
      s"bs-auto-${UUID.randomUUID()}"
    }

    /** Add classes to an `img` element to easily style images in any project.
      * @see
      *   [[http://getbootstrap.com/css/#images-shapes]]
      */
    object image extends AbstractImageUtils {
      final class ImageStyle private[bootstrap4] (val styleName: String)
          extends AbstractImageStyle
          with StyleClassModifier {
        val className      = s"img-$styleName"
        val createModifier = className.addClass
      }

      /** Images in Bootstrap 3 can be made responsive-friendly via the addition of the .img-responsive class. This
        * applies max-width: 100%;, height: auto; and display: block; to the image so that it scales nicely to the
        * parent element. To center images which use the .img-responsive class, use .center-block instead of
        * .text-center. See the helper classes section for more details about .center-block usage.
        * @see
        *   [[http://getbootstrap.com/css/#images-responsive]]
        */
      lazy val responsive = new ImageStyle("responsive")

      lazy val rounded   = new ImageStyle("rounded")
      lazy val circle    = new ImageStyle("circle")
      lazy val thumbnail = new ImageStyle("thumbnail")
    }

    sealed trait TextModifier extends StyleClassModifier {
      def styleName: String
      def className           = s"text-$styleName"
      lazy val createModifier = className.addClass
    }

    /** Easily realign text to components with text alignment classes.
      * @see
      *   [[http://getbootstrap.com/css/#type-alignment]]
      */
    object textAlign extends AbstractTextAlignments {
      final class TextAlignment private[bootstrap4] (val styleName: String)
          extends TextModifier
          with AbstractTextAlignment

      lazy val left    = new TextAlignment("left")
      lazy val center  = new TextAlignment("center")
      lazy val right   = new TextAlignment("right")
      lazy val justify = new TextAlignment("justify")
      lazy val nowrap  = new TextAlignment("nowrap")
    }

    /** Transform text in components with text capitalization classes.
      * @see
      *   [[http://getbootstrap.com/css/#type-transformation]]
      */
    object textTransform extends AbstractTextTransformations {
      final class TextTransformation private[bootstrap4] (val styleName: String)
          extends TextModifier
          with AbstractTextTransformation

      lazy val lowercase  = new TextTransformation("lowercase")
      lazy val uppercase  = new TextTransformation("uppercase")
      lazy val capitalize = new TextTransformation("capitalize")
    }

    /** Convey meaning through color with a handful of emphasis utility classes. These may also be applied to links and
      * will darken on hover just like our default link styles.
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-colors]]
      */
    object textStyle extends AbstractTextStyles {
      final class TextStyle private[bootstrap4] (val styleName: String) extends TextModifier with AbstractTextStyle

      lazy val muted       = new TextStyle("muted")
      lazy val primary     = new TextStyle("primary")
      lazy val secondary   = new TextStyle("secondary")
      lazy val success     = new TextStyle("success")
      lazy val info        = new TextStyle("info")
      lazy val warning     = new TextStyle("warning")
      lazy val danger      = new TextStyle("danger")
      lazy val light       = new TextStyle("light")
      lazy val dark        = new TextStyle("dark")
      lazy val white       = new TextStyle("white")
      lazy val hide        = new TextStyle("hide")
      lazy val monospace   = new TextStyle("monospace")
      lazy val body        = new TextStyle("body")
      lazy val `black-50%` = new TextStyle("black-50")
      lazy val `white-50%` = new TextStyle("white-50")
    }

    /** Similar to the contextual text color classes, easily set the background of an element to any contextual class.
      * Anchor components will darken on hover, just like the text classes.
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-backgrounds]]
      */
    object background extends AbstractBackgroundStyles {
      final class BackgroundStyle private[bootstrap4] (val styleName: String)
          extends AbstractBackgroundStyle
          with StyleClassModifier {
        val className      = s"bg-$styleName"
        val createModifier = className.addClass
      }

      lazy val primary     = new BackgroundStyle("primary")
      lazy val secondary   = new BackgroundStyle("secondary")
      lazy val success     = new BackgroundStyle("success")
      lazy val info        = new BackgroundStyle("info")
      lazy val warning     = new BackgroundStyle("warning")
      lazy val danger      = new BackgroundStyle("danger")
      lazy val light       = new BackgroundStyle("light")
      lazy val dark        = new BackgroundStyle("dark")
      lazy val white       = new BackgroundStyle("white")
      lazy val transparent = new BackgroundStyle("transparent")
    }

    object border extends AbstractBorders {
      lazy val border        = "border".addClass
      lazy val border0       = "border0".addClass
      lazy val borderTop0    = "border-top-0".addClass
      lazy val borderBottom0 = "border-bottom-0".addClass
      lazy val borderLeft0   = "border-left-0".addClass
      lazy val borderRight0  = "border-right-0".addClass
    }

    final class BorderStyle private[bootstrap4] (val styleName: String)
        extends AbstractBorderStyle
        with StyleClassModifier {
      val className      = s"border-$styleName"
      val createModifier = className.addClass
    }

    object borderStyle extends AbstractBorderStyles {
      lazy val primary   = new BorderStyle("primary")
      lazy val secondary = new BorderStyle("secondary")
      lazy val success   = new BorderStyle("success")
      lazy val info      = new BorderStyle("info")
      lazy val warning   = new BorderStyle("warning")
      lazy val danger    = new BorderStyle("danger")
      lazy val light     = new BorderStyle("light")
      lazy val dark      = new BorderStyle("dark")
      lazy val white     = new BorderStyle("white")
    }

    object borderRounding extends AbstractBorderRounding {
      lazy val rounded       = "rounded".addClass
      lazy val roundedTop    = "rounded-top".addClass
      lazy val roundedBottom = "rounded-bottom".addClass
      lazy val roundedRight  = "rounded-right".addClass
      lazy val roundedLeft   = "rounded-left".addClass
      lazy val roundedCircle = "rounded-circle".addClass
      lazy val rounded0      = "rounded-0".addClass
    }

    /** Use the generic close icon for dismissing content like modals and alerts.
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-close]]
      */
    lazy val closeIcon: Tag = {
      scalaTags.tags.button(
        `type`     := "button",
        `class`    := "close",
        aria.label := "Close",
        span(aria.hidden := true, raw("&times;"))
      )
    }

    /** Use carets to indicate dropdown functionality and direction. Note that the default caret will reverse
      * automatically in dropup menus.
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-carets]]
      */
    lazy val caret: Tag = span(`class` := "caret")

    /** Float an element to the left or right with a class. `!important` is included to avoid specificity issues.
      * @note
      *   To align components in navbars with utility classes, use .navbar-left or .navbar-right instead. See the navbar
      *   docs for details.
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-floats]]
      */
    object pull extends AbstractPullModifiers {
      final class PullModifier private[bootstrap4] (val styleName: String)
          extends AbstractPullModifier
          with StyleClassModifier {
        val className      = s"pull-$styleName"
        val createModifier = className.addClass
      }

      lazy val left  = new PullModifier("left")
      lazy val right = new PullModifier("right")
    }

    /** Set an element to display: block and center via margin
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-center]]
      */
    lazy val centerBlock = "center-block".addClass

    /** Easily clear floats by adding `.clearfix` <b>to the parent element</b>. Utilizes
      * [[http://nicolasgallagher.com/micro-clearfix-hack/ the micro clearfix]] as popularized by Nicolas Gallagher.
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-clearfix]]
      */
    lazy val clearFix = "clearfix".addClass

    /** Force an element to be shown or hidden (including for screen readers) with the use of `.show` and `.hidden`
      * classes. These classes use `!important` to avoid specificity conflicts, just like the quick floats. They are
      * only available for block level toggling. They can also be used as mixins. `.hide` is available, but it does not
      * always affect screen readers and is deprecated as of v3.0.1. Use `.hidden` or `.sr-only` instead. Furthermore,
      * `.invisible` can be used to toggle only the visibility of an element, meaning its display is not modified and
      * the element can still affect the flow of the document.
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-show-hide]]
      */
    object visibility extends AbstractVisibilityModifiers {
      final class ElementVisibility private[bootstrap4] (val styleName: String)
          extends AbstractVisibilityModifier
          with StyleClassModifier {
        val className      = styleName
        val createModifier = className.addClass
      }

      lazy val visible   = new ElementVisibility("visible")
      lazy val invisible = new ElementVisibility("invisible")
    }

    class DisplayModifier private[bootstrap4] (val size: Option[String], val styleName: String)
        extends AbstractDisplayModifier {
      val className      = size.fold("d-" + styleName)(screenSize ⇒ "d-" + screenSize + "-" + styleName)
      val createModifier = className.addClass
    }

    class DisplayModifiers private[bootstrap4] (size: Option[String]) extends AbstractDisplayModifiers {
      protected def displayModifier(str: String): DisplayModifier = new DisplayModifier(size, str)

      lazy val none        = displayModifier("none")
      lazy val inline      = displayModifier("inline")
      lazy val inlineBlock = displayModifier("inline-block")
      lazy val block       = displayModifier("block")
      lazy val table       = displayModifier("table")
      lazy val tableCell   = displayModifier("table-cell")
      lazy val flex        = displayModifier("flex")
      lazy val inlineFlex  = displayModifier("inline-flex")
    }

    object display extends DisplayModifiers(None) {
      lazy val sm = new DisplayModifiers(Some("sm"))
      lazy val md = new DisplayModifiers(Some("md"))
      lazy val lg = new DisplayModifiers(Some("lg"))
      lazy val xl = new DisplayModifiers(Some("xl"))
    }

    class ShadowModifier private[bootstrap4] (val styleName: String) extends AbstractShadowModifier {
      val createModifier: ModifierT = if (styleName.isEmpty) "shadow" else "shadow-" + styleName
    }

    object shadow extends AbstractShadowModifiers {
      def none    = new ShadowModifier("none")
      def small   = new ShadowModifier("sm")
      def regular = new ShadowModifier("")
      def large   = new ShadowModifier("lg")
    }

    /** Hide an element to all devices except screen readers with `.sr-only`
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-screen-readers]]
      */
    lazy val srOnly = "sr-only".addClass

    /** Combine [[com.karasiq.bootstrap4.utils.UniversalUtils.UniversalUtils#srOnly() .sr-only]] with
      * `.sr-only-focusable` to show the element again when it's focused (e.g. by a keyboard-only user)
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-screen-readers]]
      */
    lazy val srOnlyFocusable: Modifier = Array("sr-only", "sr-only-focusable").map(_.addClass)

    /** Appends `data-%property%` attributes to the element
      * @param props
      *   Properties
      * @tparam T
      *   Value type
      * @return
      *   Modifier
      */
    def dataProps[T: AttrValue](props: (String, T)*): Modifier = {
      props.map { case (name, value) ⇒
        attr("data-" + name) := value
      }
    }

    /** Non-breaking Space A common character entity used in HTML is the non-breaking space: &nbsp; A non-breaking space
      * is a space that will not break into a new line.
      */
    val nbsp = raw("&nbsp")

    /** No-op frag
      */
    val noContent = (): FragT

    /** No-op modifier
      */
    val noModifier = (): ModifierT
  }
}
