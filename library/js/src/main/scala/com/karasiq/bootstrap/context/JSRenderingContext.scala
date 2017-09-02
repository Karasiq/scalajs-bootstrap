package com.karasiq.bootstrap.context

import scala.language.postfixOps
import scalatags.JsDom

import org.scalajs.dom

trait JSRenderingContext extends RenderingContext with JSBootstrapImplicits with JSReactiveBinds with JSCallbacks with JSClassModifiers {
  type Element = dom.Element
  type Output = dom.Element
  type FragT = dom.Node

  //noinspection TypeAnnotation
  val scalaTags = JsDom
}
