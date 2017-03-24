package com.karasiq.bootstrap_text

import java.util.UUID

import com.karasiq.bootstrap_text.BootstrapImplicits._
import com.karasiq.bootstrap_text.buttons.Button
import com.karasiq.bootstrap_text.icons.BootstrapGlyphicon

import scalatags.Text.all._
import scalatags.text.Builder.GenericAttrValueSource

//noinspection ConvertExpressionToSAM
object Bootstrap {
  /**
    * A lightweight, flexible component that can optionally extend the entire viewport to showcase key content on your site.
    * @see [[https://getbootstrap.com/components/#jumbotron]]
    */
  def jumbotron: Tag = div("jumbotron".addClass)

  /**
    * Use the well as a simple effect on an element to give it an inset effect.
    * @see [[https://getbootstrap.com/components/#wells]]
    */
  def well: Tag = div("well".addClass)

  /**
    * Easily highlight new or unread items by adding a badge to links, Bootstrap navs, and more.
    * @see [[https://getbootstrap.com/components/#badges]]
    */
  def badge: Tag = span("badge".addClass)

  /**
    * Default button
    */
  def button: Tag = Button()

  /**
    * Glyphicon
    * @param name Icon name
    * @see [[https://getbootstrap.com/components/#glyphicons]]
    */
  def icon(name: String): BootstrapGlyphicon = {
    new BootstrapGlyphicon(name)
  }

  /**
    * Generates unique element ID
    */
  def newId: String = {
    s"bs-auto-${UUID.randomUUID()}"
  }

  /**
    * Add classes to an `img` element to easily style images in any project.
    * @see [[http://getbootstrap.com/css/#images-shapes]]
    */
  object image {
    final class ImageStyle private[bootstrap_text](style: String) extends ModifierFactory {
      def createModifier: Modifier = s"img-$style".addClass
    }

    /**
      * Images in Bootstrap 3 can be made responsive-friendly via the addition of the .img-responsive class.
      * This applies max-width: 100%;, height: auto; and display: block; to the image so that it scales nicely to the parent element.
      * To center images which use the .img-responsive class, use .center-block instead of .text-center.
      * See the helper classes section for more details about .center-block usage.
      * @see [[http://getbootstrap.com/css/#images-responsive]]
      */
    lazy val responsive = "img-responsive".addClass

    lazy val rounded = new ImageStyle("rounded")
    lazy val circle = new ImageStyle("circle")
    lazy val thumbnail = new ImageStyle("thumbnail")
  }

  sealed trait TextModifier extends ModifierFactory {
    def style: String
    def createModifier: Modifier = s"text-$style".addClass
  }

  /**
    * Easily realign text to components with text alignment classes.
    * @see [[http://getbootstrap.com/css/#type-alignment]]
    */
  object textAlign {
    final class TextAlignment private[bootstrap_text](val style: String) extends TextModifier

    lazy val left = new TextAlignment("left")
    lazy val center = new TextAlignment("center")
    lazy val right = new TextAlignment("right")
    lazy val justify = new TextAlignment("justify")
    lazy val nowrap = new TextAlignment("nowrap")
  }

  /**
    * Transform text in components with text capitalization classes.
    * @see [[http://getbootstrap.com/css/#type-transformation]]
    */
  object textTransform {
    final class TextTransformation private[bootstrap_text](val style: String) extends TextModifier

    lazy val lowercase = new TextTransformation("lowercase")
    lazy val uppercase = new TextTransformation("uppercase")
    lazy val capitalize = new TextTransformation("capitalize")
  }

  /**
    * Convey meaning through color with a handful of emphasis utility classes.
    * These may also be applied to links and will darken on hover just like our default link styles.
    * @see [[http://getbootstrap.com/css/#helper-classes-colors]]
    */
  object textStyle {
    final class TextStyle private[bootstrap_text](val style: String) extends TextModifier

    lazy val muted = new TextStyle("muted")
    lazy val primary = new TextStyle("primary")
    lazy val success = new TextStyle("success")
    lazy val info = new TextStyle("info")
    lazy val warning = new TextStyle("warning")
    lazy val danger = new TextStyle("danger")
  }

  /**
    * Similar to the contextual text color classes, easily set the background of an element to any contextual class.
    * Anchor components will darken on hover, just like the text classes.
    * @see [[http://getbootstrap.com/css/#helper-classes-backgrounds]]
    */
  object background {
    final class BackgroundStyle private[bootstrap_text](style: String) extends ModifierFactory {
      def createModifier: Modifier = s"bg-$style".addClass
    }

    lazy val primary = new BackgroundStyle("primary")
    lazy val success = new BackgroundStyle("success")
    lazy val info = new BackgroundStyle("info")
    lazy val warning = new BackgroundStyle("warning")
    lazy val danger = new BackgroundStyle("danger")
  }

  /**
    * Use the generic close icon for dismissing content like modals and alerts.
    * @see [[http://getbootstrap.com/css/#helper-classes-close]]
    */
  def closeIcon: Tag = {
    scalatags.Text.tags.button(`type` := "button", `class` := "close", aria.label := "Close", span(aria.hidden := true, raw("&times;")))
  }

  /**
    * Use carets to indicate dropdown functionality and direction.
    * Note that the default caret will reverse automatically in dropup menus.
    * @see [[http://getbootstrap.com/css/#helper-classes-carets]]
    */
  def caret: Tag = {
    span(`class` := "caret")
  }

  /**
    * Float an element to the left or right with a class.
    * `!important` is included to avoid specificity issues.
    * @note To align components in navbars with utility classes, use .navbar-left or .navbar-right instead. See the navbar docs for details.
    * @see [[http://getbootstrap.com/css/#helper-classes-floats]]
    */
  object pull {
    lazy val left = "pull-left".addClass
    lazy val right = "pull-right".addClass
  }

  /**
    * Set an element to display: block and center via margin
    * @see [[http://getbootstrap.com/css/#helper-classes-center]]
    */
  def centerBlock: Modifier = "center-block".addClass

  /**
    * Easily clear floats by adding `.clearfix` <b>to the parent element</b>.
    * Utilizes [[http://nicolasgallagher.com/micro-clearfix-hack/ the micro clearfix]] as popularized by Nicolas Gallagher.
    * @see [[http://getbootstrap.com/css/#helper-classes-clearfix]]
    */
  def clearFix: Modifier = "clearfix".addClass

  /**
    * Force an element to be shown or hidden (including for screen readers) with the use of `.show` and `.hidden` classes.
    * These classes use `!important` to avoid specificity conflicts, just like the quick floats.
    * They are only available for block level toggling. They can also be used as mixins.
    * `.hide` is available, but it does not always affect screen readers and is deprecated as of v3.0.1. Use `.hidden` or `.sr-only` instead.
    * Furthermore, `.invisible` can be used to toggle only the visibility of an element, meaning its display is not modified and the element can still affect the flow of the document.
    * @see [[http://getbootstrap.com/css/#helper-classes-show-hide]]
    */
  object visibility {
    final class ElementVisibility private[bootstrap_text](val className: String) extends ModifierFactory {
      val createModifier = className.addClass
    }
    lazy val show = new ElementVisibility("show")
    lazy val hidden = new ElementVisibility("hidden")
    lazy val invisible = new ElementVisibility("invisible")
  }

  /**
    * Hide an element to all devices except screen readers with `.sr-only`
    * @see [[http://getbootstrap.com/css/#helper-classes-screen-readers]]
    */
  def srOnly: Modifier = "sr-only".addClass

  /**
    * Combine [[com.karasiq.bootstrap_text.Bootstrap#srOnly() .sr-only]] with `.sr-only-focusable` to show the element again when it's focused (e.g. by a keyboard-only user)
    * @see [[http://getbootstrap.com/css/#helper-classes-screen-readers]]
    */
  def srOnlyFocusable: Modifier = Seq("sr-only", "sr-only-focusable").map(_.addClass)

  /**
    * Utilize the `.text-hide` class or mixin to help replace an element's text content with a background image.
    * @see [[http://getbootstrap.com/css/#helper-classes-image-replacement]]
    */
  def textHide: TextModifier = new TextModifier {
    def style = "hide"
  }

  def dataProperties(props: Map[String, _]): Modifier = new Modifier {
    def applyTo(t: Element): Unit = {
      props.foreach { case (property, value) ⇒
        t.setAttr(s"data-$property", GenericAttrValueSource(value.toString))
      }
    }
  }
}