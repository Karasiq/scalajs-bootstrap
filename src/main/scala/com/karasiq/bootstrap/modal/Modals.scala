package com.karasiq.bootstrap.modal

import com.karasiq.bootstrap.buttons.Buttons
import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.utils.{ClassModifiers, Utils}

import scala.language.postfixOps

trait Modals { self: RenderingContext with Utils with BootstrapComponents with ClassModifiers with Buttons ⇒
  import BootstrapAttrs._

  import scalaTags.all._

  abstract class Modal extends BootstrapHtmlComponent {
    def title: Modifier
    def body: Modifier
    def buttons: Modifier
    def style: Modifier
    def dialogStyle: Modifier
    def contentStyle: Modifier
    def modalId: String

    def modalHeader: Tag = {
      div(`class` := "modal-header")(
        button(`type` := "button", `class` := "close", `data-dismiss` := "modal", aria.label := "Close")(
          span(aria.hidden := true, raw("&times;"))
        ),
        h4(`class` := "modal-title", this.title)
      )
    }

    def modalBody: Tag = {
      div(`class` := "modal-body", this.body)
    }

    def modalFooter: Tag = {
      div(`class` := "modal-footer", this.buttons)
    }

    def modal: Tag = {
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

    def toggle: Modifier = {
      Seq(`data-toggle` := "modal", `data-target` := s"#$modalId")
    }

    def renderTag(md: Modifier*): Tag = {
      modal(md:_*)
    }
  }

  trait ModalFactory {
    val dismiss: Modifier = {
      `data-dismiss` := "modal"
    }

    def closeButton(title: String = "Close"): Tag = {
      Bootstrap.button(this.dismiss, title)
    }

    def button(md: Modifier*): Tag = {
      Button(ButtonStyle.primary)(md)
    }

    def apply(title: Modifier = "Modal dialog", body: Modifier = "", buttons: Modifier = closeButton(),
              style: Modifier = (), dialogStyle: Modifier = ModalDialogSize.default, contentStyle: Modifier = (),
              modalId: String = Bootstrap.newId): Modal
  }

  /**
    * Modals are streamlined, but flexible, dialog prompts with the minimum required functionality and smart defaults.
    * @see [[https://getbootstrap.com/javascript/#modals]]
    */
  object Modal extends ModalFactory {
    def apply(title: Modifier = "Modal dialog", body: Modifier = "", buttons: Modifier = closeButton(),
              style: Modifier = (), dialogStyle: Modifier = ModalDialogSize.default, contentStyle: Modifier = (),
              modalId: String = Bootstrap.newId): ModalBuilder = {
      ModalBuilder(title, body, buttons, style, dialogStyle, contentStyle, modalId)
    }
  }

  case class ModalBuilder(title: Modifier, body: Modifier, buttons: Modifier, style: Modifier,
                          dialogStyle: Modifier, contentStyle: Modifier, modalId: String) extends Modal {
    def withTitle(md: Modifier*): ModalBuilder = copy(title = md)
    def withBody(md: Modifier*): ModalBuilder = copy(body = md)
    def withButtons(md: Modifier*): ModalBuilder = copy(buttons = md)
    def withStyle(md: Modifier*): ModalBuilder = copy(style = md)
    def withDialogStyle(md: Modifier*): ModalBuilder = copy(dialogStyle = md)
    def withContentStyle(md: Modifier*): ModalBuilder = copy(contentStyle = md)
    def withId(id: String): ModalBuilder = copy(modalId = id)
  }

  sealed trait ModalDialogSize extends ModifierFactory

  object DefaultModalDialogSize extends ModalDialogSize {
    val createModifier: Modifier = ()
  }

  final class CustomModalDialogSize private[modal](size: String) extends ModalDialogSize {
    val className: String = size
    val createModifier: Modifier = className.addClass
  }

  object ModalDialogSize {
    val default = DefaultModalDialogSize
    val small = new CustomModalDialogSize("modal-sm")
    val large = new CustomModalDialogSize("modal-lg")
  }
}

