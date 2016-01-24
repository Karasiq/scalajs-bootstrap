package com.karasiq.bootstrap.alert

import com.karasiq.bootstrap.BootstrapAttrs._
import com.karasiq.bootstrap.BootstrapHtmlComponent
import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom

import scalatags.JsDom.all
import scalatags.JsDom.all._

final class Alert(style: AlertStyle) extends BootstrapHtmlComponent[dom.html.Div] {
  override def renderTag(md: all.Modifier*): RenderedTag = {
    div("alert".addClass, "alert-dismissible".addClass, style, role := "alert")(
      button(`type` := "button", "close".addClass, `data-dismiss` := "alert", aria.label := "Close")(
        span(aria.hidden := true, raw("&times;"))
      ),
      md
    )
  }
}

object Alert {
  def apply(style: AlertStyle, md: Modifier*): ConcreteHtmlTag[dom.html.Div] = {
    new Alert(style).renderTag(md:_*)
  }

  def link: ConcreteHtmlTag[dom.html.Anchor] = a("alert-link".addClass)
}
