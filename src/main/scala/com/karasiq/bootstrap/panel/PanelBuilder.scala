package com.karasiq.bootstrap.panel

import com.karasiq.bootstrap.BootstrapHtmlComponent
import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom

import scalatags.JsDom.all._

case class PanelBuilder(panelId: String, style: PanelStyle = PanelStyle.default, header: Option[Modifier] = None, footer: Option[Modifier] = None) extends BootstrapHtmlComponent[dom.html.Div] {
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

  def renderTag(content: Modifier*): RenderedTag = {
    div("panel".addClass, style, id := panelId)(
      for (h ← header) yield div(`class` := "panel-heading", id := s"$panelId-panel-header", h),
      div(`class` := "panel-body collapse in", id := s"$panelId-panel-body", content),
      for (f ← footer) yield div(`class` := "panel-footer", id := s"$panelId-panel-footer", f)
    )
  }
}
