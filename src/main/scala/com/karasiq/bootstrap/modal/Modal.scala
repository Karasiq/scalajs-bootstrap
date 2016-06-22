package com.karasiq.bootstrap.modal

import com.karasiq.bootstrap.Bootstrap
import com.karasiq.bootstrap.BootstrapAttrs._
import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.buttons.{Button, ButtonStyle}
import org.scalajs.dom
import org.scalajs.jquery.jQuery

import scala.scalajs.js
import scala.scalajs.js.|
import scalatags.JsDom.all._

sealed trait Modal {
  def title: Modifier
  def body: Modifier
  def buttons: Modifier

  private def modalHeader = {
    div(`class` := "modal-header")(
      button(`type` := "button", `class` := "close", Modal.dismiss, aria.label := "Close")(
        span(aria.hidden := true, raw("&times;"))
      ),
      h4(`class` := "modal-title", this.title)
    )
  }

  private def modalBody = {
    div(`class` := "modal-body", this.body)
  }

  private def modalFooter = {
    div(`class` := "modal-footer")(
      this.buttons
    )
  }

  private def modal = {
    div(`class` := "modal fade", tabindex := -1, role := "dialog")(
      div(`class` := "modal-dialog")(
        div(`class` := "modal-content")(
          modalHeader,
          modalBody,
          modalFooter
        )
      )
    )
  }

  def show(backdrop: Boolean | String = true, keyboard: Boolean = true, show: Boolean = true): Unit = {
    val dialog = jQuery(this.modal.render)
    val options = js.Object().asInstanceOf[ModalOptions]
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

/**
  * Modals are streamlined, but flexible, dialog prompts with the minimum required functionality and smart defaults.
  * @see [[https://getbootstrap.com/javascript/#modals]]
  */
object Modal {
  val dismiss: Modifier = {
    `data-dismiss` := "modal"
  }

  def closeButton(title: String = "Close"): ConcreteHtmlTag[dom.html.Button] = {
    Bootstrap.button(this.dismiss, title)
  }

  def button(md: Modifier*): ConcreteHtmlTag[dom.html.Button] = {
    Button(ButtonStyle.primary)(md)
  }

  def apply(title: Modifier = "Modal dialog", body: Modifier = "", buttons: Modifier = Modal.closeButton()): ModalBuilder = {
    ModalBuilder(title, body, buttons)
  }
}

case class ModalBuilder(title: Modifier, body: Modifier, buttons: Modifier) extends Modal {
  def withTitle(md: Modifier*): ModalBuilder = copy(title = md)
  def withBody(md: Modifier*): ModalBuilder = copy(body = md)
  def withButtons(md: Modifier*): ModalBuilder = copy(buttons = md)
}

