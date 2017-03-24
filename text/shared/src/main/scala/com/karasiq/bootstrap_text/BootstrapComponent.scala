package com.karasiq.bootstrap_text

import scalatags.Text.all._

trait BootstrapComponent {
  def render(md: Modifier*): Modifier
}

trait BootstrapHtmlComponent extends BootstrapComponent {
  final type RenderedTag = Tag

  def renderTag(md: Modifier*): RenderedTag

  override final def render(md: Modifier*): Modifier = {
    this.renderTag(md:_*)
  }
}

trait ModifierFactory extends Modifier {
  def createModifier: Modifier

  override def applyTo(t: scalatags.text.Builder): Unit = {
    createModifier.applyTo(t)
  }
}
