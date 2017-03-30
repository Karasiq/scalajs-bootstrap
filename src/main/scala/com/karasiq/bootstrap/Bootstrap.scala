package com.karasiq.bootstrap

import com.karasiq.bootstrap.context.JSBootstrapBundle
import rx._

/**
  * Global JS context
  */
object Bootstrap extends JSBootstrapBundle {
  implicit val scalaRxContext = Ctx.Owner.Unsafe
}