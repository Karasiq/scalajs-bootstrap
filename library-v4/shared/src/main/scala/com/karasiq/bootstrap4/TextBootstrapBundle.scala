package com.karasiq.bootstrap4

import scala.language.postfixOps

import rx.Ctx

import com.karasiq.bootstrap.context.TextRenderingContext
import com.karasiq.bootstrap4.carousel.TextCarousels
import com.karasiq.bootstrap4.popover.TextPopovers
import com.karasiq.bootstrap4.tooltip.TextTooltips

// Text components implementation
trait TextBootstrapBundle
    extends UniversalBootstrapBundle
    with TextRenderingContext
    with TextCarousels
    with TextTooltips
    with TextPopovers

object TextBootstrapBundle {
  def apply()(implicit ctx: Ctx.Owner): TextBootstrapBundle = {
    new TextBootstrapBundle {
      implicit val scalaRxContext = ctx
    }
  }
}
