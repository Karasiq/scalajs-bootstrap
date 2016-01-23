package com.karasiq.bootstrap

import org.scalajs.dom
import org.scalajs.dom.Element

import scalatags.JsDom.all._

trait BootstrapComponent {
  def render: Modifier
}

trait BootstrapHtmlComponent[T <: dom.Element] extends BootstrapComponent {
  final type RenderedTag = ConcreteHtmlTag[T]

  def renderTag(md: Modifier*): RenderedTag

  override final def render: Modifier = {
    this.renderTag()
  }
}

trait ClassModifier extends Modifier {
  def classMod: Modifier

  override def applyTo(t: Element): Unit = {
    classMod.applyTo(t)
  }
}
