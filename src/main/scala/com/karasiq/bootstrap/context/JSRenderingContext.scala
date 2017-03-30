package com.karasiq.bootstrap.context

import com.karasiq.bootstrap.utils.{JSBootstrapImplicits, JSCallbacks}
import org.scalajs.dom

import scala.language.postfixOps
import scalatags.JsDom

trait JSRenderingContext extends RenderingContext with JSBootstrapImplicits with JSReactiveBinds with JSCallbacks {
  type Element = dom.Element
  type Output = dom.Element
  type FragT = dom.Node
  val scalaTags = JsDom
}
