package com.karasiq.bootstrap_text.alert

import com.karasiq.bootstrap_text.BootstrapAttrs._
import com.karasiq.bootstrap_text.BootstrapHtmlComponent
import com.karasiq.bootstrap_text.BootstrapImplicits._

import scalatags.Text.all._

final class Alert(style: AlertStyle) extends BootstrapHtmlComponent {
  override def renderTag(md: Modifier*): RenderedTag = {
    div(Seq("alert", "alert-dismissible", "fade", "in").map(_.addClass), style, role := "alert")(
      button(`type` := "button", "close".addClass, `data-dismiss` := "alert", aria.label := "Close")(
        span(aria.hidden := true, raw("&times;"))
      ),
      md
    )
  }
}

/**
  * Provide contextual feedback messages for typical user actions with the handful of available and flexible alert messages.
  * @see [[http://getbootstrap.com/components/#alerts]]
  */
object Alert {
  def apply(style: AlertStyle, md: Modifier*): Tag = {
    new Alert(style).renderTag(md:_*)
  }

  def link: Tag = a("alert-link".addClass)
}
