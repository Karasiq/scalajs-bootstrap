package com.karasiq.bootstrap.components

import scalatags.generic.{Frag, Modifier, TypedTag}

package object generic {
  // Context-bound components
  trait ModifierFactory[E] extends Modifier[E] {
    // Shortcuts
    protected type ElementT  = E
    protected type ModifierT = Modifier[E]

    def createModifier: ModifierT

    final def applyTo(t: ElementT): Unit = {
      createModifier.applyTo(t)
    }
  }

  trait BootstrapComponent[E] extends ModifierFactory[E] {
    def render(md: ModifierT*): ModifierT

    final def createModifier: ModifierT = {
      render()
    }
  }

  trait BootstrapDomComponent[E, F] extends BootstrapComponent[E] with Frag[E, F] {
    protected type FragT = Frag[E, F]

    def renderFrag(md: ModifierT*): FragT

    final def render: F = {
      renderFrag().render
    }

    final def render(md: ModifierT*): ModifierT = {
      renderFrag(md: _*)
    }
  }

  trait BootstrapHtmlComponent[E, O <: F, F] extends BootstrapDomComponent[E, F] {
    protected type TagT = TypedTag[E, O, F]

    def renderTag(md: ModifierT*): TagT

    final def renderFrag(md: ModifierT*): FragT = {
      renderTag(md: _*)
    }
  }
}
