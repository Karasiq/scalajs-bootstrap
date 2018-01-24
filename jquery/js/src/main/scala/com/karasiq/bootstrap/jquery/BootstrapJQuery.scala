package com.karasiq.bootstrap.jquery

import scala.language.postfixOps
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import org.scalajs.jquery.{JQuery, JQueryStatic}

@scalajs.js.native
trait BootstrapJQuery extends scalajs.js.Object {
  def tab(options: scalajs.js.Any): JQuery = scalajs.js.native
  def carousel(options: scalajs.js.Any = ???): JQuery = scalajs.js.native
  def modal(options: scalajs.js.Any = ???): JQuery = scalajs.js.native
  def tooltip(options: scalajs.js.Any = ???): JQuery = scalajs.js.native
  def popover(options: scalajs.js.Any = ???): JQuery = js.native
}

object BootstrapJQuery {
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
    // js.Dynamic.global.jQuery = jQuery
    // js.Dynamic.global.$ = jQuery
    imports.bootstrap
  }
}