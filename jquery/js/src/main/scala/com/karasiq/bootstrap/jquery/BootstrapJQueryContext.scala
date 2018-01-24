package com.karasiq.bootstrap.jquery

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

trait BootstrapJQueryContext extends JQueryContext with BootstrapJQueryImplicits

object BootstrapJQueryContext {
  object imports {
    @js.native
    @JSImport("bootstrap", JSImport.Namespace)
    object bootstrap extends js.Object
  }

  def useNpmImports(): Unit = {
    JQueryContext.useNpmImport()
    imports.bootstrap
  }
}
