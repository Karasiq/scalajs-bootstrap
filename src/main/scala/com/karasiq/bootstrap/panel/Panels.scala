package com.karasiq.bootstrap.panel

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.icons.Icons
import com.karasiq.bootstrap.utils.{ClassModifiers, Utils}

import scala.language.postfixOps

trait Panels { self: RenderingContext with Utils with BootstrapComponents with ClassModifiers with Icons ⇒
  import BootstrapAttrs._

  import scalaTags.all._

  case class PanelBuilder(panelId: String, style: PanelStyle = PanelStyle.default,
                          header: Option[Modifier] = None, footer: Option[Modifier] = None) extends BootstrapHtmlComponent {
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

    def renderTag(content: Modifier*): Tag = {
      div("panel".addClass, style, id := panelId)(
        for (h ← header) yield div(`class` := "panel-heading", id := s"$panelId-panel-header", h),
        div(`class` := "panel-body collapse in", id := s"$panelId-panel-body", content),
        for (f ← footer) yield div(`class` := "panel-footer", id := s"$panelId-panel-footer", f)
      )
    }
  }

  object Panel {
    def collapse(panelId: String, modifiers: Modifier*): Tag = {
      span(cursor.pointer, `data-toggle` := "collapse", `data-target` := s"#$panelId-panel-body", modifiers)
    }

    def title(icon: IconModifier, title: Modifier, modifiers: Modifier*): Tag = {
      h3(`class` := "panel-title")(
        icon,
        raw("&nbsp;"),
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

    /**
      * Shortcut to PanelBuilder()
      */
    def apply(panelId: String = Bootstrap.newId, style: PanelStyle = PanelStyle.default,
              header: Option[Modifier] = None, footer: Option[Modifier] = None): PanelBuilder = {
      PanelBuilder(panelId, style, header, footer)
    }
  }

  sealed trait PanelStyle extends ModifierFactory

  object DefaultPanelStyle extends PanelStyle {
    val createModifier: Modifier = ()
  }

  final class PanelStyleValue private[panel](style: String) extends PanelStyle {
    val className = s"panel-$style"
    val createModifier = className.addClass
  }

  object PanelStyle {
    def default = DefaultPanelStyle
    lazy val primary = new PanelStyleValue("primary")
    lazy val success = new PanelStyleValue("success")
    lazy val info = new PanelStyleValue("info")
    lazy val warning = new PanelStyleValue("warning")
    lazy val danger = new PanelStyleValue("danger")
  }
}
