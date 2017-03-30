package com.karasiq.bootstrap.context

import com.karasiq.bootstrap.carousel.JSCarousels
import com.karasiq.bootstrap.modal.JSModals
import com.karasiq.bootstrap.navbar.JSNavigationBars
import com.karasiq.bootstrap.popover.JSPopovers
import com.karasiq.bootstrap.tooltip.JSTooltips
import rx.Ctx

import scala.language.postfixOps

trait JSBootstrapBundle extends BootstrapBundle with JSRenderingContext with JSModals with JSTooltips
  with JSPopovers with JSNavigationBars with JSCarousels

object JSBootstrapBundle {
  def apply()(implicit rx: Ctx.Owner): JSBootstrapBundle = {
    new JSBootstrapBundle {
      implicit val scalaRxContext = rx
    }
  }
}
