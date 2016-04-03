package com.karasiq.bootstrap.card

import com.karasiq.bootstrap.Bootstrap
import com.karasiq.bootstrap.BootstrapAttrs._
import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.grid.GridSystem
import com.karasiq.bootstrap.icons.IconModifier
import org.scalajs.dom

import scalatags.JsDom.all._

object Card {
  def collapse(cardId: String, modifiers: Modifier*): Tag = {
    span(cursor.pointer, `data-toggle` := "collapse", `data-target` := s"#$cardId-card-body", modifiers)
  }

  def title(md: Modifier*): Tag = {
    h4("card-title".addClass, md)
  }

  def text(md: Modifier*): Tag = {
    p("card-text".addClass, md)
  }

  def button(icon: IconModifier, modifiers: Modifier*): ConcreteHtmlTag[dom.html.Anchor] = {
    a(href := "javascript:void(0);", icon, modifiers)
  }

  def buttons(buttons: Modifier*): Tag = {
    div("card-head-buttons".addClass, GridSystem.pull.xs.right, buttons)
  }

  /**
    * Shortcut to PanelBuilder()
    */
  def apply(panelId: String = Bootstrap.newId, style: CardStyle = CardStyle.default, inverse: Boolean = false, header: Option[Modifier] = None, footer: Option[Modifier] = None): CardBuilder = {
    CardBuilder(panelId, style, inverse, header, footer)
  }
}
