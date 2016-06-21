package com.karasiq.bootstrap

import org.scalajs.dom
import org.scalajs.dom.Element

import scalatags.JsDom.all._

trait BootstrapComponent {
  def render(md: Modifier*): Modifier
}

trait BootstrapHtmlComponent[T <: dom.Element] extends BootstrapComponent {
  final type RenderedTag = ConcreteHtmlTag[T]

  def renderTag(md: Modifier*): RenderedTag

  override final def render(md: Modifier*): Modifier = {
    this.renderTag(md:_*)
  }
}

trait ModifierFactory extends Modifier {
  def createModifier: Modifier

  override def applyTo(t: Element): Unit = {
    createModifier.applyTo(t)
  }
}
