package com.karasiq.bootstrap.context

import com.karasiq.bootstrap.utils.{JSBootstrapImplicits, JSCallbacks, JSClassModifiers}
import org.scalajs.dom

import scala.language.postfixOps
import scalatags.JsDom

trait JSRenderingContext extends RenderingContext with JSBootstrapImplicits with JSReactiveBinds with JSCallbacks with JSClassModifiers {
  type Element = dom.Element
  type Output = dom.Element
  type FragT = dom.Node

  val scalaTags = JsDom
}
