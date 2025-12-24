package com.karasiq.bootstrap4.utils

import com.karasiq.bootstrap.context.{ClassModifiers, RenderingContext}
import com.karasiq.bootstrap4.buttons.Buttons
import com.karasiq.bootstrap4.icons.Icons

//noinspection TypeAnnotation
trait Utils { self: RenderingContext with Icons with Buttons with ClassModifiers ⇒
  import scalaTags.all._

  type Bootstrap <: AbstractUtils
  val Bootstrap: Bootstrap

  type BootstrapAttrs <: DefaultBootstrapAttrs
  val BootstrapAttrs: BootstrapAttrs

  trait DefaultBootstrapAttrs {
    lazy val `data-toggle`   = attr("data-toggle")
    lazy val `data-target`   = attr("data-target")
    lazy val `data-slide-to` = attr("data-slide-to")
    lazy val `data-ride`     = attr("data-ride")
    lazy val `data-slide`    = attr("data-slide")
    lazy val `data-dismiss`  = attr("data-dismiss")
  }

  trait AbstractUtils {
    type FragT      = Frag
    type ModifierT  = Modifier
    type ElementT   = Tag
    type ElementIdT = String

    val image: AbstractImageUtils
    val textAlign: AbstractTextAlignments
    val textTransform: AbstractTextTransformations
    val textStyle: AbstractTextStyles
    val background: AbstractBackgroundStyles
    val border: AbstractBorders
    val borderStyle: AbstractBorderStyles
    val borderRounding: AbstractBorderRounding
    val pull: AbstractPullModifiers
    val visibility: AbstractVisibilityModifiers
    val display: AbstractDisplayModifiers
    val shadow: AbstractShadowModifiers

    /** Generates unique element ID
      */
    def newId: ElementIdT

    /** A lightweight, flexible component that can optionally extend the entire viewport to showcase key content on your
      * site.
      * @see
      *   [[https://getbootstrap.com/components/#jumbotron]]
      */
    def jumbotron: ElementT

    /** Easily highlight new or unread items by adding a badge to links, Bootstrap navs, and more.
      * @see
      *   [[https://getbootstrap.com/components/#badges]]
      */
    def badge: ElementT

    /** Default button
      */
    def button: ElementT

    /** Default icon
      * @param name
      *   Icon name
      * @see
      *   [[https://getbootstrap.com/components/#glyphicons]]
      */
    def icon(name: String): IconModifier

    /** Use the generic close icon for dismissing content like modals and alerts.
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-close]]
      */
    def closeIcon: ElementT

    /** Use carets to indicate dropdown functionality and direction. Note that the default caret will reverse
      * automatically in dropup menus.
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-carets]]
      */
    def caret: ElementT

    /** Set an element to display: block and center via margin
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-center]]
      */
    def centerBlock: ModifierT

    /** Easily clear floats by adding `.clearfix` <b>to the parent element</b>. Utilizes
      * [[http://nicolasgallagher.com/micro-clearfix-hack/ the micro clearfix]] as popularized by Nicolas Gallagher.
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-clearfix]]
      */
    def clearFix: ModifierT

    /** Hide an element to all devices except screen readers with `.sr-only`
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-screen-readers]]
      */
    def srOnly: ModifierT

    /** Combine [[com.karasiq.bootstrap4.utils.Utils.AbstractUtils#srOnly() .sr-only]] with `.sr-only-focusable` to show
      * the element again when it's focused (e.g. by a keyboard-only user)
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-screen-readers]]
      */
    def srOnlyFocusable: ModifierT

    /** Appends `data-%property%` attributes to the element
      * @param props
      *   Properties
      * @tparam T
      *   Value type
      * @return
      *   Modifier
      */
    def dataProps[T: AttrValue](props: (String, T)*): ModifierT

    /** Non-breaking Space A common character entity used in HTML is the non-breaking space: &nbsp; A non-breaking space
      * is a space that will not break into a new line.
      */
    def nbsp: ModifierT

    /** No-op frag
      */
    def noContent: FragT

    /** No-op modifier
      */
    def noModifier: ModifierT
  }

  trait StyleModifier extends ModifierFactory {
    def styleName: String
  }

  trait StyleClassModifier extends StyleModifier {
    def className: String
  }

  trait AbstractImageStyle         extends StyleModifier
  trait AbstractTextStyle          extends StyleModifier
  trait AbstractTextAlignment      extends AbstractTextStyle
  trait AbstractTextTransformation extends AbstractTextStyle
  trait AbstractBackgroundStyle    extends StyleModifier
  trait AbstractBorderStyle        extends StyleModifier
  trait AbstractPullModifier       extends StyleModifier
  trait AbstractVisibilityModifier extends StyleModifier
  trait AbstractDisplayModifier    extends StyleModifier
  trait AbstractShadowModifier     extends StyleModifier

  /** Add classes to an `img` element to easily style images in any project.
    * @see
    *   [[http://getbootstrap.com/css/#images-shapes]]
    */
  trait AbstractImageUtils {

    /** Images in Bootstrap 3 can be made responsive-friendly via the addition of the .img-responsive class. This
      * applies max-width: 100%;, height: auto; and display: block; to the image so that it scales nicely to the parent
      * element. To center images which use the .img-responsive class, use .center-block instead of .text-center. See
      * the helper classes section for more details about .center-block usage.
      * @see
      *   [[http://getbootstrap.com/css/#images-responsive]]
      */
    def responsive: AbstractImageStyle

    def rounded: AbstractImageStyle
    def circle: AbstractImageStyle
    def thumbnail: AbstractImageStyle
  }

  /** Easily realign text to components with text alignment classes.
    * @see
    *   [[http://getbootstrap.com/css/#type-alignment]]
    */
  trait AbstractTextAlignments {
    def left: AbstractTextAlignment
    def center: AbstractTextAlignment
    def right: AbstractTextAlignment
    def justify: AbstractTextAlignment
    def nowrap: AbstractTextAlignment
  }

  /** Transform text in components with text capitalization classes.
    * @see
    *   [[http://getbootstrap.com/css/#type-transformation]]
    */
  trait AbstractTextTransformations {
    def lowercase: AbstractTextTransformation
    def uppercase: AbstractTextTransformation
    def capitalize: AbstractTextTransformation
  }

  /** Convey meaning through color with a handful of emphasis utility classes. These may also be applied to links and
    * will darken on hover just like our default link styles.
    * @see
    *   [[http://getbootstrap.com/css/#helper-classes-colors]]
    */
  trait AbstractTextStyles {
    def muted: AbstractTextStyle
    def primary: AbstractTextStyle
    def secondary: AbstractTextStyle
    def success: AbstractTextStyle
    def info: AbstractTextStyle
    def warning: AbstractTextStyle
    def danger: AbstractTextStyle
    def light: AbstractTextStyle
    def dark: AbstractTextStyle
    def white: AbstractTextStyle

    /** Utilize the `.text-hide` class or mixin to help replace an element's text content with a background image.
      * @see
      *   [[http://getbootstrap.com/css/#helper-classes-image-replacement]]
      */
    @deprecated(
      "Deprecated .text-hide—you’ll see a warning during compilation—as it’s a dated and undocumented feature.",
      "4.1"
    )
    def hide: AbstractTextStyle

    // See https://blog.getbootstrap.com/2018/04/09/bootstrap-4-1/
    def monospace: AbstractTextStyle
    def body: AbstractTextStyle
    def `black-50%`: AbstractTextStyle
    def `white-50%`: AbstractTextStyle
  }

  /** Similar to the contextual text color classes, easily set the background of an element to any contextual class.
    * Anchor components will darken on hover, just like the text classes.
    * @see
    *   [[http://getbootstrap.com/css/#helper-classes-backgrounds]]
    */
  trait AbstractBackgroundStyles {
    def primary: AbstractBackgroundStyle
    def secondary: AbstractBackgroundStyle
    def success: AbstractBackgroundStyle
    def info: AbstractBackgroundStyle
    def warning: AbstractBackgroundStyle
    def danger: AbstractBackgroundStyle
    def light: AbstractBackgroundStyle
    def dark: AbstractBackgroundStyle
    def white: AbstractBackgroundStyle
  }

  /** Change the border color using utilities built on our theme colors.
    * @see
    *   [[https://getbootstrap.com/docs/4.0/utilities/borders/#border-color]]
    */
  trait AbstractBorderStyles {
    def primary: AbstractBorderStyle
    def secondary: AbstractBorderStyle
    def success: AbstractBorderStyle
    def info: AbstractBorderStyle
    def warning: AbstractBorderStyle
    def danger: AbstractBorderStyle
    def light: AbstractBorderStyle
    def dark: AbstractBorderStyle
    def white: AbstractBorderStyle
  }

  /** Add classes to an element to remove all borders or some borders.
    * @see
    *   [[https://getbootstrap.com/docs/4.0/utilities/borders/]]
    */
  trait AbstractBorders {
    def border: Modifier
    def border0: Modifier
    def borderTop0: Modifier
    def borderBottom0: Modifier
    def borderLeft0: Modifier
    def borderRight0: Modifier
  }

  /** Add classes to an element to easily round its corners.
    * @see
    *   [[https://getbootstrap.com/docs/4.0/utilities/borders/#border-radius]]
    */
  trait AbstractBorderRounding {
    def rounded: Modifier
    def roundedTop: Modifier
    def roundedBottom: Modifier
    def roundedRight: Modifier
    def roundedLeft: Modifier
    def roundedCircle: Modifier
    def rounded0: Modifier
  }

  /** Float an element to the left or right with a class. `!important` is included to avoid specificity issues.
    * @note
    *   To align components in navbars with utility classes, use .navbar-left or .navbar-right instead. See the navbar
    *   docs for details.
    * @see
    *   [[http://getbootstrap.com/css/#helper-classes-floats]]
    */
  trait AbstractPullModifiers {
    def left: AbstractPullModifier
    def right: AbstractPullModifier
  }

  /** Control the visibility, without modifying the display, of elements with visibility utilities.
    * @see
    *   [[https://getbootstrap.com/docs/4.0/utilities/visibility/]]
    */
  trait AbstractVisibilityModifiers {
    def visible: AbstractVisibilityModifier
    def invisible: AbstractVisibilityModifier
  }

  /** @see
    *   [[https://getbootstrap.com/docs/4.0/utilities/display/]]
    */
  trait AbstractDisplayModifiers {
    def none: AbstractDisplayModifier
    def inline: AbstractDisplayModifier
    def inlineBlock: AbstractDisplayModifier
    def block: AbstractDisplayModifier
    def table: AbstractDisplayModifier
    def tableCell: AbstractDisplayModifier
    def flex: AbstractDisplayModifier
    def inlineFlex: AbstractDisplayModifier
  }

  /** Add or remove shadows to elements with `box-shadow` utilities.
    * @since 4.1
    * @see
    *   [[https://getbootstrap.com/docs/4.1/utilities/shadows/]]
    */
  trait AbstractShadowModifiers {
    def none: AbstractShadowModifier
    def small: AbstractShadowModifier
    def regular: AbstractShadowModifier
    def large: AbstractShadowModifier
  }
}
