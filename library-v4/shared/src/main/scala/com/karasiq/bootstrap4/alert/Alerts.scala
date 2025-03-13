package com.karasiq.bootstrap4.alert

import com.karasiq.bootstrap.context.RenderingContext

trait Alerts extends AlertStyles { self: RenderingContext ⇒
  import scalaTags.all._

  type Alert <: AbstractAlert

  trait AbstractAlert extends BootstrapHtmlComponent {
    def style: AlertStyle
  }

  /** Provide contextual feedback messages for typical user actions with the handful of available and flexible alert
    * messages.
    * @see
    *   [[http://getbootstrap.com/components/#alerts]]
    */
  trait AlertFactory {
    def create(style: AlertStyle): Alert
    def link: Tag

    def apply(style: AlertStyle, content: Modifier*): Tag = {
      create(style).renderTag(content: _*)
    }
  }
}
