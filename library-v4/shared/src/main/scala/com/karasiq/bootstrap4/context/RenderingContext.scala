package com.karasiq.bootstrap4.context

import scala.language.postfixOps
import scalatags.generic

import com.karasiq.bootstrap4.components.BootstrapComponents
import com.karasiq.bootstrap4.utils.{Callbacks, ClassModifiers}

trait RenderingContext extends ReactiveBinds with ReactiveImplicits with Callbacks
  with BootstrapComponents with ClassModifiers {
  
  type Element
  type Output <: FragT
  type FragT

  val scalaTags: generic.Bundle[Element, Output, FragT] with generic.Aliases[Element, Output, FragT]
  implicit val implicitRenderingContext: this.type = this
}
