package com.karasiq.bootstrap

import rx._

/** Global context
  */
object Bootstrap {
  private[this] lazy val _scalaRxContext = Ctx.Owner.safe()

  lazy val js: JSBootstrapBundle = new JSBootstrapBundle {
    implicit val scalaRxContext = _scalaRxContext
  }

  lazy val text: TextBootstrapBundle = new TextBootstrapBundle {
    implicit val scalaRxContext = _scalaRxContext
  }

  lazy val default: JSBootstrapBundle = js
}
