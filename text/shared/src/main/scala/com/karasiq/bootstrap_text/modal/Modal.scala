package com.karasiq.bootstrap_text.modal

import com.karasiq.bootstrap_text.BootstrapAttrs._
import com.karasiq.bootstrap_text.buttons.{Button, ButtonStyle}
import com.karasiq.bootstrap_text.{Bootstrap, BootstrapHtmlComponent}

import scalatags.Text.all._

sealed trait Modal extends BootstrapHtmlComponent {
  def title: Modifier
  def body: Modifier
  def buttons: Modifier
  def style: Modifier
  def dialogStyle: Modifier
  def contentStyle: Modifier

  private[this] def modalHeader = {
    div(`class` := "modal-header")(
      button(`type` := "button", `class` := "close", Modal.dismiss, aria.label := "Close")(
        span(aria.hidden := true, raw("&times;"))
      ),
      h4(`class` := "modal-title", this.title)
    )
  }

  private[this] def modalBody = {
    div(`class` := "modal-body", this.body)
  }

  private[this] def modalFooter = {
    div(`class` := "modal-footer", this.buttons)
  }

  private[this] def modal = {
    div(`class` := "modal fade", tabindex := -1, role := "dialog", style)(
      div(`class` := "modal-dialog", dialogStyle)(
        div(`class` := "modal-content", contentStyle)(
          modalHeader,
          modalBody,
          modalFooter
        )
      )
    )
  }

  def renderTag(md: Modifier*): RenderedTag = {
    this.modal
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

  def closeButton(title: String = "Close"): Tag = {
    Bootstrap.button(this.dismiss, title)
  }

  def button(md: Modifier*): Tag = {
    Button(ButtonStyle.primary).renderTag(md:_*)
  }

  def apply(title: Modifier = "Modal dialog", body: Modifier = "", buttons: Modifier = Modal.closeButton(), style: Modifier = (), dialogStyle: Modifier = ModalDialogSize.default, contentStyle: Modifier = ()): ModalBuilder = {
    ModalBuilder(title, body, buttons, style, dialogStyle, contentStyle)
  }
}

case class ModalBuilder(title: Modifier, body: Modifier, buttons: Modifier, style: Modifier, dialogStyle: Modifier, contentStyle: Modifier) extends Modal {
  def withTitle(md: Modifier*): ModalBuilder = copy(title = md)
  def withBody(md: Modifier*): ModalBuilder = copy(body = md)
  def withButtons(md: Modifier*): ModalBuilder = copy(buttons = md)
  def withStyle(md: Modifier*): ModalBuilder = copy(style = md)
  def withDialogStyle(md: Modifier*): ModalBuilder = copy(dialogStyle = md)
  def withContentStyle(md: Modifier*): ModalBuilder = copy(contentStyle = md)
}

