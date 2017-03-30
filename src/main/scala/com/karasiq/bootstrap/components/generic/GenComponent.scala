package com.karasiq.bootstrap.components.generic

import com.karasiq.bootstrap.context.RenderingContext

// Generic components
// TODO: Dependent types breaks implicits
trait GenModifierFactory {
  type RC <: RenderingContext
  def render(context: RC): context.ModifierFactory
}

trait GenComponent extends GenModifierFactory {
  def render(context: RC): context.BootstrapComponent
}

trait GenDomComponent extends GenComponent {
  def render(context: RC): context.BootstrapDomComponent
}

trait GenHtmlComponent extends GenDomComponent {
  def render(context: RC): context.BootstrapHtmlComponent
}
