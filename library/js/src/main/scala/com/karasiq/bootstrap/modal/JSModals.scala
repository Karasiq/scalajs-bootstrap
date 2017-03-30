package com.karasiq.bootstrap.modal

import com.karasiq.bootstrap.context.JSRenderingContext
import org.scalajs.jquery.jQuery

import scala.language.postfixOps
import scala.scalajs.js
import scala.scalajs.js.|

trait JSModals { self: JSRenderingContext with Modals ⇒
  implicit class JSModalOps(modal: Modal) {
    def show(backdrop: Boolean | String = true, keyboard: Boolean = true, show: Boolean = true): Unit = {
      val dialog = jQuery(modal.renderTag().render)
      val options = js.Object().asInstanceOf[JSModalOptions]
      options.backdrop = backdrop
      options.keyboard = keyboard
      options.show = show
      dialog.modal(options)
      dialog.on("hidden.bs.modal", () ⇒ {
        // Remove from DOM
        dialog.remove()
      })
      dialog.modal("show")
    }
  }
}
