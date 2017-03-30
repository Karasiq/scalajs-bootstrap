package com.karasiq.bootstrap.context

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.utils.Callbacks

import scala.language.postfixOps
import scalatags.generic
import scalatags.generic.Aliases

trait RenderingContext extends ReactiveBinds with ReactiveImplicits with Callbacks with BootstrapComponents {
  type Element
  type Output <: FragT
  type FragT

  val scalaTags: generic.Bundle[Element, Output, FragT] with Aliases[Element, Output, FragT]
}
