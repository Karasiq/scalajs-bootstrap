package com.karasiq.bootstrap.panel

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.{ClassModifiers, RenderingContext}
import com.karasiq.bootstrap.icons.Icons
import com.karasiq.bootstrap.utils.Utils

trait Panels extends PanelStyles {
  self: RenderingContext with Utils with BootstrapComponents with ClassModifiers with Icons ⇒
  import scalaTags.all._

  type Panel <: AbstractPanel with BootstrapHtmlComponent
  val Panel: PanelFactory

  trait AbstractPanel {
    def panelId: String
    def style: PanelStyle
    def header: Option[Modifier]
    def footer: Option[Modifier]
  }

  trait PanelFactory {
    def collapse(panelId: String, modifiers: Modifier*): Tag
    def title(icon: IconModifier, title: Modifier, modifiers: Modifier*): Tag
    def button(icon: IconModifier, modifiers: Modifier*): Tag
    def buttons(buttons: Modifier*): Tag

    def apply(
        panelId: String = Bootstrap.newId,
        style: PanelStyle = PanelStyle.default,
        header: Option[Modifier] = None,
        footer: Option[Modifier] = None
    ): Panel
  }
}
