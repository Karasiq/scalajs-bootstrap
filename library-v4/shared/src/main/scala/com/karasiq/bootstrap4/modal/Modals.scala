package com.karasiq.bootstrap4.modal

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.{ClassModifiers, RenderingContext}
import com.karasiq.bootstrap4.buttons.Buttons
import com.karasiq.bootstrap4.utils.Utils

trait Modals extends ModalStyles {
  self: RenderingContext with Utils with BootstrapComponents with ClassModifiers with Buttons ⇒
  import scalaTags.all._

  type Modal <: AbstractModal with BootstrapHtmlComponent
  val Modal: ModalFactory

  trait AbstractModal {
    def modalId: String
    def title: Modifier
    def body: Modifier
    def buttons: Modifier
    def style: Modifier
    def dialogStyle: Modifier
    def contentStyle: Modifier
    def toggle: Modifier
    def dismiss: Modifier
  }

  /** Modals are streamlined, but flexible, dialog prompts with the minimum required functionality and smart defaults.
    * @see
    *   [[https://getbootstrap.com/javascript/#modals]]
    */
  trait ModalFactory {
    def closeButton(title: String = "Close"): Tag
    def button(md: Modifier*): Tag

    def apply(
        title: Modifier = "Modal dialog",
        body: Modifier = "",
        buttons: Modifier = closeButton(),
        style: Modifier = Bootstrap.noModifier,
        dialogStyle: Modifier = ModalDialogSize.default,
        contentStyle: Modifier = Bootstrap.noModifier,
        modalId: String = Bootstrap.newId
    ): Modal
  }
}
