package com.karasiq.bootstrap4.context

import scala.language.postfixOps

import rx.Ctx

import com.karasiq.bootstrap4.carousel.JSCarousels
import com.karasiq.bootstrap4.modal.JSModals
import com.karasiq.bootstrap4.navbar.JSNavigationBars
import com.karasiq.bootstrap4.popover.JSPopovers
import com.karasiq.bootstrap4.tooltip.JSTooltips

// JS components implementation
trait JSBootstrapBundle extends UniversalBootstrapBundle with JSRenderingContext with JSModals with JSTooltips
  with JSPopovers with JSNavigationBars with JSCarousels

object JSBootstrapBundle {
  def apply()(implicit rx: Ctx.Owner): JSBootstrapBundle = {
    new JSBootstrapBundle {
      implicit val scalaRxContext = rx
    }
  }
}
