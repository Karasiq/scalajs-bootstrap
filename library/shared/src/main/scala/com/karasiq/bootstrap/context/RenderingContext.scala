package com.karasiq.bootstrap.context

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.utils.{Callbacks, ClassModifiers}

import scala.language.postfixOps
import scalatags.generic

trait RenderingContext extends ReactiveBinds with ReactiveImplicits with Callbacks
  with BootstrapComponents with ClassModifiers {
  
  type Element
  type Output <: FragT
  type FragT

  val scalaTags: generic.Bundle[Element, Output, FragT] with generic.Aliases[Element, Output, FragT]
  implicit val implicitRenderingContext: this.type = this
}
