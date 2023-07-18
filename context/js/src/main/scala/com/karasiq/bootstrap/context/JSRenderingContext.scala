package com.karasiq.bootstrap.context

import scala.language.postfixOps

import scalatags.JsDom
import org.scalajs.dom
import org.scalajs.dom.Node

trait JSRenderingContext
    extends RenderingContext
    with JSReactiveBinds
    with JSCallbacks
    with JSClassModifiers
    with JSImplicits {
  type Element = dom.Element
  type Output  = dom.Element
  type FragT   = dom.Node

  // noinspection TypeAnnotation
  val scalaTags = JsDom
}
