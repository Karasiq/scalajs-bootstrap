package com.karasiq.bootstrap

import com.karasiq.bootstrap.context.{JSBootstrapBundle, TextBootstrapBundle}
import rx._

/**
  * Global context
  */
object Bootstrap {
  private[this] lazy val _scalaRxContext = Ctx.Owner.Unsafe

  lazy val js: JSBootstrapBundle = new JSBootstrapBundle {
    implicit val scalaRxContext = _scalaRxContext
  }

  lazy val text: TextBootstrapBundle = new TextBootstrapBundle {
    implicit val scalaRxContext = _scalaRxContext
  }

  lazy val default: JSBootstrapBundle = js
}