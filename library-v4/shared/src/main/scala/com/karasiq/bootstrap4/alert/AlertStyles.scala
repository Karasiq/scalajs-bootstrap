package com.karasiq.bootstrap4.alert

import com.karasiq.bootstrap4.context.RenderingContext

trait AlertStyles { self: RenderingContext ⇒
  import scalaTags.all._

  final class AlertStyle private[alert](val styleName: String) extends ModifierFactory {
    val createModifier = s"alert-$styleName".addClass
  }

  object AlertStyle {
    lazy val success = new AlertStyle("success")
    lazy val info = new AlertStyle("info")
    lazy val warning = new AlertStyle("warning")
    lazy val danger = new AlertStyle("danger")
  }
}
