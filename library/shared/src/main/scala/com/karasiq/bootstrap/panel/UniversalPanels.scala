package com.karasiq.bootstrap.panel

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.icons.Icons
import com.karasiq.bootstrap.utils.Utils

trait UniversalPanels {
  self: RenderingContext with BootstrapComponents with Utils with Icons with Panels with PanelStyles ⇒
  import scalaTags.all._

  import BootstrapAttrs._

  type Panel = PanelBuilder
  object Panel extends PanelFactory {
    def collapse(panelId: String, modifiers: Modifier*): Tag = {
      span(cursor.pointer, `data-toggle` := "collapse", `data-target` := s"#$panelId-panel-body", modifiers)
    }

    def title(icon: IconModifier, title: Modifier, modifiers: Modifier*): Tag = {
      h3(`class` := "panel-title")(
        icon,
        Bootstrap.nbsp,
        title,
        modifiers
      )
    }

    def button(icon: IconModifier, modifiers: Modifier*): Tag = {
      a(href := "javascript:void(0);", icon, modifiers)
    }

    def buttons(buttons: Modifier*): Tag = {
      div(`class` := "pull-right panel-head-buttons", buttons)
    }

    /** Shortcut to PanelBuilder()
      */
    def apply(
        panelId: String = Bootstrap.newId,
        style: PanelStyle = PanelStyle.default,
        header: Option[Modifier] = None,
        footer: Option[Modifier] = None
    ): PanelBuilder = {
      PanelBuilder(panelId, style, header, footer)
    }
  }

  case class PanelBuilder(
      panelId: String,
      style: PanelStyle = PanelStyle.default,
      header: Option[Modifier] = None,
      footer: Option[Modifier] = None
  ) extends AbstractPanel
      with BootstrapHtmlComponent {

    def withId(newId: String): PanelBuilder = {
      copy(panelId = newId)
    }

    def withStyle(style: PanelStyle): PanelBuilder = {
      copy(style = style)
    }

    def withHeader(modifiers: Modifier*): PanelBuilder = {
      copy(header = Some(modifiers))
    }

    def withFooter(modifiers: Modifier*): PanelBuilder = {
      copy(footer = Some(modifiers))
    }

    def renderTag(content: ModifierT*): TagT = {
      div("panel".addClass, style, id := panelId)(
        for (h ← header) yield div(`class` := "panel-heading", id := s"$panelId-panel-header", h),
        div(`class` := "panel-body collapse in", id := s"$panelId-panel-body", content),
        for (f ← footer) yield div(`class` := "panel-footer", id := s"$panelId-panel-footer", f)
      )
    }
  }
}
