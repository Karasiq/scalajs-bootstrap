package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.utils.ClassModifiers
import rx.Var

import scala.language.postfixOps

trait Buttons { self: RenderingContext with BootstrapComponents with ClassModifiers ⇒
  import scalaTags.all._

  /**
    * Button builder
    * @param style Use any of the available button classes to quickly create a styled button
    * @param size Fancy larger or smaller buttons? Add `.btn-lg`, `.btn-sm`, or `.btn-xs` for additional sizes
    * @param block Create block level buttons—those that span the full width of a parent— by adding `.btn-block`
    * @param active Buttons will appear pressed (with a darker background, darker border, and inset shadow) when active
    * @param disabled Make buttons look unclickable by fading them back with `opacity`
    * @see [[http://getbootstrap.com/css/#buttons]]
    */
  case class ButtonBuilder(style: ButtonStyle = ButtonStyle.default, size: ButtonSize = ButtonSize.default, block: Boolean = false, active: Boolean = false, disabled: Boolean = false) extends BootstrapHtmlComponent {
    def withStyle(style: ButtonStyle): ButtonBuilder = copy(style = style)
    def withSize(size: ButtonSize): ButtonBuilder = copy(size = size)

    override def renderTag(md: Modifier*): Tag = {
      @inline def optional(flag: Boolean, className: String) = if (flag) Some(className) else None
      val classList = Seq(Some("btn"), optional(block, "btn-block"), optional(active, "active"), optional(disabled, "disabled")).flatten.map(_.addClass)
      button(`type` := "button", classList, style, size, md)
    }
  }

  object Button {
    /**
      * Shortcut to [[com.karasiq.bootstrap.buttons.Buttons.ButtonBuilder ButtonBuilder]].
      */
    def apply(style: ButtonStyle = ButtonStyle.default, size: ButtonSize = ButtonSize.default, block: Boolean = false, active: Boolean = false, disabled: Boolean = false): ButtonBuilder = {
      ButtonBuilder(style, size, block, active, disabled)
    }
  }

  sealed trait ButtonSize extends ModifierFactory

  object DefaultButtonSize extends ButtonSize {
    val createModifier: Modifier = ()
  }

  final class ButtonSizeValue private[buttons](size: String) extends ButtonSize {
    val className = s"btn-$size"
    val createModifier = className.addClass
  }

  /**
    * @see [[https://getbootstrap.com/css/#buttons-sizes]]
    */
  object ButtonSize {
    def default = DefaultButtonSize
    lazy val large = new ButtonSizeValue("lg")
    lazy val small = new ButtonSizeValue("sm")
    lazy val extraSmall = new ButtonSizeValue("xs")
  }


  case class ButtonGroup(size: ButtonGroupSize, buttons: Modifier*) extends BootstrapHtmlComponent {
    override def renderTag(md: Modifier*): Tag = {
      div("btn-group".addClass, size, role := "group", aria.label := "Button group", md)(
        buttons
      )
    }
  }

  case class ButtonToolbar(buttonGroups: ButtonGroup*) extends BootstrapHtmlComponent {
    override def renderTag(md: Modifier*): Tag = {
      div(`class` := "btn-toolbar", role := "toolbar", aria.label := "Button toolbar", md)(
        buttonGroups.map(_.renderTag())
      )
    }
  }

  sealed trait ButtonGroupSize extends ModifierFactory

  object DefaultButtonGroupSize extends ButtonGroupSize {
    val createModifier: Modifier = ()
  }

  final class ButtonGroupSizeValue private[buttons](size: String) extends ButtonGroupSize {
    val className = s"btn-group-$size"
    val createModifier = className.addClass
  }

  object ButtonGroupSize {
    def default: ButtonGroupSize = DefaultButtonGroupSize
    lazy val large: ButtonGroupSize = new ButtonGroupSizeValue("lg")
    lazy val small: ButtonGroupSize = new ButtonGroupSizeValue("sm")
    lazy val extraSmall: ButtonGroupSize = new ButtonGroupSizeValue("xs")
  }

  final class ButtonStyle private[buttons](style: String) extends ModifierFactory {
    val className = s"btn-$style"
    val createModifier: Modifier = className.addClass
  }

  /**
    * @see [[https://getbootstrap.com/css/#buttons-options]]
    */
  object ButtonStyle {
    lazy val default = new ButtonStyle("default")
    lazy val primary = new ButtonStyle("primary")
    lazy val success = new ButtonStyle("success")
    lazy val info = new ButtonStyle("info")
    lazy val warning = new ButtonStyle("warning")
    lazy val danger = new ButtonStyle("danger")
    lazy val link = new ButtonStyle("link")
  }

  object DisabledButton {
    def apply(btn: Tag): DisabledButton = {
      new DisabledButton(btn)
    }
  }

  final class DisabledButton(btn: Tag) extends BootstrapHtmlComponent {
    val state: Var[Boolean] = Var(false)

    override def renderTag(md: Modifier*): Tag = {
      btn(
        "disabled".classIf(state),
        onclick := Callback.onClick { _ ⇒
          if (!state.now) state.update(true)
        },
        md
      )
    }
  }

  object ToggleButton {
    def apply(btn: Tag): ToggleButton = {
      new ToggleButton(btn)
    }
  }

  final class ToggleButton(btn: Tag) extends BootstrapHtmlComponent {
    val state: Var[Boolean] = Var(false)

    override def renderTag(md: Modifier*): Tag = {
      btn(
        "active".classIf(state),
        onclick := Callback.onClick { _ ⇒
          state.update(!state.now)
        },
        md
      )
    }
  }
}
