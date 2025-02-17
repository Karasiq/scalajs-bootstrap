package com.karasiq.bootstrap4

import rx._

/** Global context
  */
object Bootstrap {
  private[this] lazy val _scalaRxContext = Ctx.Owner.safe()

  lazy val js: JSBootstrapBundle      = JSBootstrapBundle()(_scalaRxContext)
  lazy val text: TextBootstrapBundle  = TextBootstrapBundle()(_scalaRxContext)
  lazy val default: JSBootstrapBundle = js
}
