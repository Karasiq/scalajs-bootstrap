package com.karasiq.bootstrap.panel

import com.karasiq.bootstrap.Bootstrap
import com.karasiq.bootstrap.BootstrapImplicits._

import scalatags.JsDom.all._

case class PanelBuilder(panelId: String = Bootstrap.newId, style: PanelStyle = PanelStyle.default, header: Option[Modifier] = None, footer: Option[Modifier] = None) {
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

  def build(content: Modifier*): Tag = {
    div((Seq("panel") ++ style.styleClass).map(_.addClass), id := panelId)(
      for (h <- header) yield div(`class` := "panel-heading", id := s"$panelId-panel-header", h),
      div(`class` := "panel-body collapse in", id := s"$panelId-panel-body", content),
      for (f <- footer) yield div(`class` := "panel-footer", id := s"$panelId-panel-footer", f)
    )
  }
}
