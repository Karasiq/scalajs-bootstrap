package com.karasiq.bootstrap.panel

import com.karasiq.bootstrap.Bootstrap
import org.scalajs.dom

import scalatags.JsDom.all._

object Panel {
  def collapse(panelId: String, modifiers: Modifier*): Tag = {
    val `data-toggle` = "data-toggle".attr
    val `data-target` = "data-target".attr
    span(cursor.pointer, `data-toggle` := "collapse", `data-target` := s"#$panelId-panel-body", modifiers)
  }

  def title(icon: String, title: Modifier, modifiers: Modifier*): Tag = {
    h3(`class` := "panel-title")(
      Bootstrap.icon(icon),
      raw("&nbsp;"),
      title,
      modifiers
    )
  }

  def button(icon: String, modifiers: Modifier*): ConcreteHtmlTag[dom.html.Anchor] = {
    a(href := "javascript:void(0);", Bootstrap.icon(icon), modifiers)
  }

  def buttons(buttons: Tag*): Tag = {
    div(`class` := "pull-right panel-head-buttons", buttons)
  }
}
