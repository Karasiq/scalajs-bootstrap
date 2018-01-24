package com.karasiq.bootstrap.jquery

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import org.scalajs.jquery.JQueryStatic

trait JQueryContext {
  def jQuery = JQueryContext.jQuery
}

object JQueryContext {
  var jQuery: JQueryStatic = org.scalajs.jquery.jQuery

  object imports {
    @js.native
    @JSImport("jquery", JSImport.Namespace)
    object jQuery extends JQueryStatic
  }

  def useStatic(): Unit = {
    jQuery = org.scalajs.jquery.jQuery
  }

  def useNpmImport(): Unit = {
    jQuery = imports.jQuery
  }
}
