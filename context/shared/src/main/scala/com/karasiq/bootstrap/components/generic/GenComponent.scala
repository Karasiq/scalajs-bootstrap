package com.karasiq.bootstrap.components.generic

import com.karasiq.bootstrap.context.RenderingContext

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
