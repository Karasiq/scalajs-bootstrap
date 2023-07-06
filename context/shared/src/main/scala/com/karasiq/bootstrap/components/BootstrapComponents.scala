package com.karasiq.bootstrap.components

import scala.language.implicitConversions

import com.karasiq.bootstrap.components.generic.{GenComponent, GenDomComponent, GenHtmlComponent, GenModifierFactory}
import com.karasiq.bootstrap.context.RenderingContext

trait BootstrapComponents { self: RenderingContext ⇒
  final type ModifierFactory        = generic.ModifierFactory[Element]
  final type BootstrapComponent     = generic.BootstrapComponent[Element]
  final type BootstrapDomComponent  = generic.BootstrapDomComponent[Element, FragT]
  final type BootstrapHtmlComponent = generic.BootstrapHtmlComponent[Element, Output, FragT]

  implicit def renderBootstrapHtmlComponent[C](bc: C)(implicit ev: C ⇒ BootstrapHtmlComponent): scalaTags.Tag = {
    bc.renderTag()
  }

  implicit def renderBootstrapDomComponent[C](bc: C)(implicit ev: C ⇒ BootstrapDomComponent): scalaTags.Frag = {
    bc.renderFrag()
  }

  implicit def renderBootstrapComponent[C](bc: C)(implicit ev: C ⇒ BootstrapComponent): scalaTags.Modifier = {
    bc.render()
  }

  implicit def renderModifierFactory[C](bc: C)(implicit ev: C ⇒ ModifierFactory): scalaTags.Modifier = {
    bc.createModifier
  }

  implicit def renderGenModifierFactory(bc: GenModifierFactory { type RC >: self.type }): ModifierFactory = {
    bc.component
  }

  implicit def renderGenComponent(bc: GenComponent { type RC >: self.type }): BootstrapComponent = {
    bc.component
  }

  implicit def renderGenDomComponent(bc: GenDomComponent { type RC >: self.type }): BootstrapDomComponent = {
    bc.component
  }

  implicit def renderGenHtmlComponent(bc: GenHtmlComponent { type RC >: self.type }): BootstrapHtmlComponent = {
    bc.component
  }
}
