package com.karasiq.bootstrap.context

import scala.language.postfixOps
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scalatags.JsDom

import org.scalajs.dom
import org.scalajs.jquery.JQueryStatic

trait JSRenderingContext extends RenderingContext with JSBootstrapImplicits with JSReactiveBinds with JSCallbacks with JSClassModifiers {
  type Element = dom.Element
  type Output = dom.Element
  type FragT = dom.Node

  //noinspection TypeAnnotation
  val scalaTags = JsDom
}

object JSRenderingContext {
  object imports {
    @js.native
    @JSImport("jquery", JSImport.Namespace)
    object jQuery extends JQueryStatic

    @js.native
    @JSImport("bootstrap", JSImport.Namespace)
    object bootstrap extends js.Object
  }
  
  var jQuery: JQueryStatic = org.scalajs.jquery.jQuery

  def useNpmImports(): Unit = {
    jQuery = imports.jQuery
    js.Dynamic.global.jQuery = jQuery
    js.Dynamic.global.$ = jQuery
    imports.bootstrap
  }
}
