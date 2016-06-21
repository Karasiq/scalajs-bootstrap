package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.{BootstrapHtmlComponent, ModifierFactory}
import org.scalajs.dom
import org.scalajs.dom.html.Div

import scalatags.JsDom.all._

case class ButtonGroup(size: ButtonGroupSize, buttons: Modifier*) extends BootstrapHtmlComponent[dom.html.Div] {
  override def renderTag(md: Modifier*): ConcreteHtmlTag[Div] = {
    div((Seq("btn-group") ++ size.sizeClass).map(_.addClass), role := "group", aria.label := "Button group", md)(
      buttons
    )
  }
}

case class ButtonToolbar(buttonGroups: ButtonGroup*) extends BootstrapHtmlComponent[dom.html.Div] {
  override def renderTag(md: Modifier*): RenderedTag = {
    div(`class` := "btn-toolbar", role := "toolbar", aria.label := "Button toolbar", md)(
      buttonGroups.map(_.renderTag())
    )
  }
}

sealed trait ButtonGroupSize extends ModifierFactory {
  def sizeClass: Option[String]

  override def createModifier: Modifier = sizeClass.classOpt
}

object ButtonGroupSize {
  private final class BasicButtonGroupSize(name: String) extends ButtonGroupSize {
    override def sizeClass: Option[String] = Some(s"btn-group-$name")
  }

  def default: ButtonGroupSize = new ButtonGroupSize {
    override def sizeClass: Option[String] = None
  }

  def large: ButtonGroupSize = new BasicButtonGroupSize("lg")
  def small: ButtonGroupSize = new BasicButtonGroupSize("sm")
  def extraSmall: ButtonGroupSize = new BasicButtonGroupSize("xs")
}