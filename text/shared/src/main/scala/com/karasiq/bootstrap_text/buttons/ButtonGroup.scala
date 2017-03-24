package com.karasiq.bootstrap_text.buttons

import com.karasiq.bootstrap_text.BootstrapImplicits._
import com.karasiq.bootstrap_text.{BootstrapHtmlComponent, ModifierFactory}

import scalatags.Text.all._

case class ButtonGroup(size: ButtonGroupSize, buttons: Modifier*) extends BootstrapHtmlComponent {
  override def renderTag(md: Modifier*): RenderedTag = {
    div("btn-group".addClass, size, role := "group", aria.label := "Button group", md)(
      buttons
    )
  }
}

case class ButtonToolbar(buttonGroups: ButtonGroup*) extends BootstrapHtmlComponent {
  override def renderTag(md: Modifier*): RenderedTag = {
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