package com.karasiq.bootstrap.alert

import com.karasiq.bootstrap.context.BootstrapBundle

trait Alerts { self: BootstrapBundle ⇒
  import BootstrapAttrs._

  import scalaTags.all._

  final class Alert(style: AlertStyle) extends BootstrapHtmlComponent {
    override def renderTag(md: Modifier*): Tag = {
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

    def link: Tag = {
      a("alert-link".addClass)
    }
  }

  final class AlertStyle private[alert](name: String) extends ModifierFactory {
    val createModifier: Modifier = s"alert-$name".addClass
  }

  object AlertStyle {
    lazy val success = new AlertStyle("success")
    lazy val info = new AlertStyle("info")
    lazy val warning = new AlertStyle("warning")
    lazy val danger = new AlertStyle("danger")
  }
}
