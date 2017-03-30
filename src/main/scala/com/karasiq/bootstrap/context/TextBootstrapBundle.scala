package com.karasiq.bootstrap.context

import com.karasiq.bootstrap.carousel.TextCarousels
import com.karasiq.bootstrap.popover.TextPopovers
import com.karasiq.bootstrap.tooltip.TextTooltips
import rx.Ctx

import scala.language.postfixOps

trait TextBootstrapBundle extends BootstrapBundle with TextRenderingContext
  with TextCarousels with TextTooltips with TextPopovers

object TextBootstrapBundle {
  def apply()(implicit ctx: Ctx.Owner): TextBootstrapBundle = {
    new TextBootstrapBundle {
      implicit val scalaRxContext = ctx
    }
  }
}