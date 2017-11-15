package com.karasiq.bootstrap4.components.generic

import com.karasiq.bootstrap4.context.RenderingContext

// Generic components
trait GenModifierFactory {
  type RC <: RenderingContext
  implicit val rc: RC

  def component: rc.ModifierFactory
}

trait GenComponent extends GenModifierFactory {
  def component: rc.BootstrapComponent
}

trait GenDomComponent extends GenComponent {
  def component: rc.BootstrapDomComponent
}

trait GenHtmlComponent extends GenDomComponent {
  def component: rc.BootstrapHtmlComponent
}
