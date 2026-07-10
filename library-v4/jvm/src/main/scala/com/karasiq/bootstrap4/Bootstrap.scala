package com.karasiq.bootstrap4

import rx._

/** Global context
  */
object Bootstrap {
  private[this] lazy val _scalaRxContext = Ctx.Owner.safe()

  lazy val text: TextBootstrapBundle = new TextBootstrapBundle {
    implicit val scalaRxContext = _scalaRxContext
  }

  lazy val default: TextBootstrapBundle = text
}
