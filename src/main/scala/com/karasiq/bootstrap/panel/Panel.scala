package com.karasiq.bootstrap.panel

import com.karasiq.bootstrap.Bootstrap
import com.karasiq.bootstrap.BootstrapAttrs._
import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.icons.IconModifier
import org.scalajs.dom

import scalatags.JsDom.all._

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

  def button(icon: IconModifier, modifiers: Modifier*): ConcreteHtmlTag[dom.html.Anchor] = {
    a(href := "javascript:void(0);", icon, modifiers)
  }

  def buttons(buttons: Modifier*): Tag = {
    div(`class` := "pull-right panel-head-buttons", buttons)
  }

  /**
    * Shortcut to PanelBuilder()
    */
  def apply(panelId: String = Bootstrap.newId, style: PanelStyle = PanelStyle.default, header: Option[Modifier] = None, footer: Option[Modifier] = None): PanelBuilder = {
    PanelBuilder(panelId, style, header, footer)
  }
}
