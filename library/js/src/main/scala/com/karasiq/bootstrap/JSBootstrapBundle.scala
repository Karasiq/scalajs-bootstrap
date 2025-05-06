package com.karasiq.bootstrap

import scala.language.postfixOps

import rx.Ctx

import com.karasiq.bootstrap.carousel.JSCarousels
import com.karasiq.bootstrap.context.JSRenderingContext
import com.karasiq.bootstrap.jquery.BootstrapJQueryContext
import com.karasiq.bootstrap.modal.JSModals
import com.karasiq.bootstrap.navbar.JSNavigationBars
import com.karasiq.bootstrap.popover.JSPopovers
import com.karasiq.bootstrap.tooltip.JSTooltips

// JS components implementation
trait JSBootstrapBundle
    extends UniversalBootstrapBundle
    with JSRenderingContext
    with JSModals
    with JSTooltips
    with JSPopovers
    with JSNavigationBars
    with JSCarousels
    with BootstrapJQueryContext

object JSBootstrapBundle {
  def apply()(implicit rx: Ctx.Owner): JSBootstrapBundle = {
    new JSBootstrapBundle {
      implicit val scalaRxContext = rx
    }
  }
}
