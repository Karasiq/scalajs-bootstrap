package com.karasiq.bootstrap4.modal

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.{ClassModifiers, RenderingContext}
import com.karasiq.bootstrap4.buttons.Buttons
import com.karasiq.bootstrap4.utils.Utils

trait UniversalModals {
  self: RenderingContext
    with Utils
    with BootstrapComponents
    with ClassModifiers
    with Buttons
    with Modals
    with ModalStyles ⇒
  import scalaTags.all._

  import BootstrapAttrs._

  type Modal = ModalBuilder

  /** Modals are streamlined, but flexible, dialog prompts with the minimum required functionality and smart defaults.
    * @see
    *   [[https://getbootstrap.com/javascript/#modals]]
    */
  object Modal extends ModalFactory {
    val dismiss: Modifier = {
      `data-dismiss` := "modal"
    }

    def closeButton(title: String = "Close"): Tag = {
      Button(ButtonOutline.danger)(dismiss, title)
    }

    def button(md: Modifier*): Tag = {
      Button(ButtonOutline.primary)(md)
    }

    def apply(
        title: Modifier = "Modal dialog",
        body: Modifier = "",
        buttons: Modifier = closeButton(),
        style: Modifier = Bootstrap.noModifier,
        dialogStyle: Modifier = ModalDialogSize.default,
        contentStyle: Modifier = Bootstrap.noModifier,
        modalId: String = Bootstrap.newId
    ): ModalBuilder = {
      ModalBuilder(title, body, buttons, style, dialogStyle, contentStyle, modalId)
    }
  }

  trait UniversalModal extends AbstractModal with BootstrapHtmlComponent {
    def modalHeader: Tag = {
      div(`class` := "modal-header")(
        h5(`class` := "modal-title", this.title),
        button(`type` := "button", `class` := "close", `data-dismiss` := "modal", aria.label := "Close")(
          span(aria.hidden := true, raw("&times;"))
        )
      )
    }

    def modalBody: Tag = {
      div(`class` := "modal-body", this.body)
    }

    def modalFooter: Tag = {
      div(`class` := "modal-footer", this.buttons)
    }

    def modal: Tag = {
      div(`class` := "modal fade", tabindex := -1, role := "dialog", id := modalId, style)(
        div(`class` := "modal-dialog", role := "document", dialogStyle)(
          div(`class` := "modal-content", contentStyle)(
            modalHeader,
            modalBody,
            modalFooter
          )
        )
      )
    }

    def toggle: Modifier = {
      Seq(`data-toggle` := "modal", `data-target` := s"#$modalId")
    }

    def dismiss: Modifier = {
      Modal.dismiss
    }

    def renderTag(md: ModifierT*): TagT = {
      modal(md: _*)
    }
  }

  case class ModalBuilder(
      title: Modifier,
      body: Modifier,
      buttons: Modifier,
      style: Modifier,
      dialogStyle: Modifier,
      contentStyle: Modifier,
      modalId: String
  ) extends UniversalModal {

    def withTitle(md: Modifier*): ModalBuilder        = copy(title = md)
    def withBody(md: Modifier*): ModalBuilder         = copy(body = md)
    def withButtons(md: Modifier*): ModalBuilder      = copy(buttons = md)
    def withStyle(md: Modifier*): ModalBuilder        = copy(style = md)
    def withDialogStyle(md: Modifier*): ModalBuilder  = copy(dialogStyle = md)
    def withContentStyle(md: Modifier*): ModalBuilder = copy(contentStyle = md)
    def withId(id: String): ModalBuilder              = copy(modalId = id)
  }
}
