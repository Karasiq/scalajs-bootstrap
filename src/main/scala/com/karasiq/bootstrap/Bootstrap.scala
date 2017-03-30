package com.karasiq.bootstrap

import com.karasiq.bootstrap.context.{JSBootstrapBundle, TextBootstrapBundle}
import rx._

/**
  * Global context
  */
object Bootstrap {
  val js: JSBootstrapBundle = new JSBootstrapBundle {
    implicit val scalaRxContext = Ctx.Owner.Unsafe
  }

  val text: TextBootstrapBundle = new TextBootstrapBundle {
    implicit val scalaRxContext = Ctx.Owner.Unsafe
  }

  val default: JSBootstrapBundle = js
}