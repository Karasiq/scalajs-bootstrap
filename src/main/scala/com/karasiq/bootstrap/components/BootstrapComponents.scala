package com.karasiq.bootstrap.components

import com.karasiq.bootstrap.context.RenderingContext

import scala.language.{implicitConversions, postfixOps}

trait BootstrapComponents { self: RenderingContext ⇒
  final type ModifierFactory = generic.ModifierFactory[Element]
  final type BootstrapComponent = generic.BootstrapComponent[Element]
  final type BootstrapDomComponent = generic.BootstrapDomComponent[Element, FragT]
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
}
