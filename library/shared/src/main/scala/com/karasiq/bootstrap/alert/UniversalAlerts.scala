package com.karasiq.bootstrap.alert

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.utils.Utils

trait UniversalAlerts { self: RenderingContext with Alerts with Utils ⇒
  import scalaTags.all._

  import BootstrapAttrs._

  type Alert = UniversalAlert
  object Alert extends AlertFactory {
    def create(style: AlertStyle): UniversalAlert = {
      new UniversalAlert(style)
    }

    lazy val link: Tag = {
      a("alert-link".addClass)
    }
  }

  class UniversalAlert(val style: AlertStyle) extends AbstractAlert {
    private[this] val classes = Seq("alert", "alert-dismissible", "fade", "in")

    def closeButton: Tag = {
      button(
        `type` := "button",
        "close".addClass,
        `data-dismiss` := "alert",
        aria.label     := "Close",
        span(aria.hidden := true, raw("&times;"))
      )
    }

    override def renderTag(md: ModifierT*): TagT = {
      div(classes.map(_.addClass), style, role := "alert")(
        closeButton,
        md
      )
    }
  }
}
