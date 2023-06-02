package com.karasiq.bootstrap.context

import scala.language.postfixOps
import scalatags.generic

import com.karasiq.bootstrap.components.BootstrapComponents

trait RenderingContext
    extends ReactiveBinds
    with ReactiveImplicits
    with Callbacks
    with BootstrapComponents
    with ClassModifiers {

  type Element
  type Output <: FragT
  type FragT

  val scalaTags: generic.Bundle[Element, Output, FragT] with generic.Aliases[Element, Output, FragT]
  implicit val implicitRenderingContext: this.type = this
}
