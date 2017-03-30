package com.karasiq.bootstrap.components

import com.karasiq.bootstrap.context.RenderingContext

import scala.language.{implicitConversions, postfixOps}

trait BootstrapComponents { self: RenderingContext ⇒
  import scalaTags.all.{Modifier, Tag}

  trait ModifierFactory extends scalaTags.Modifier {
    def createModifier: Modifier

    final def applyTo(t: Element): Unit = {
      createModifier.applyTo(t)
    }
  }

  trait BootstrapComponent extends ModifierFactory {
    def render(md: Modifier*): Modifier

    final def createModifier: Modifier = {
      render()
    }
  }

  trait BootstrapHtmlComponent extends BootstrapComponent {
    def renderTag(md: Modifier*): Tag

    final def render(md: Modifier*): Modifier = {
      renderTag(md:_*)
    }
  }

  implicit def renderBootstrapHtmlComponent[C](bc: C)(implicit ev: C ⇒ BootstrapHtmlComponent): scalaTags.Tag = {
    bc.renderTag()
  }

  implicit def renderBootstrapComponent[C](bc: C)(implicit ev: C ⇒ BootstrapComponent): scalaTags.Modifier = {
    bc.render()
  }

  implicit def renderModifierFactory[C](bc: C)(implicit ev: C ⇒ ModifierFactory): scalaTags.Modifier = {
    bc.createModifier
  }
}
