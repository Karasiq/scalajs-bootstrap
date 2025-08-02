package com.karasiq.bootstrap.panel

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.utils.Utils

trait PanelStyles { self: RenderingContext with Utils ⇒
  import scalaTags.all._

  sealed trait PanelStyle extends ModifierFactory

  object DefaultPanelStyle extends PanelStyle {
    val createModifier = Bootstrap.noModifier
  }

  final class PanelStyleValue private[panel] (style: String) extends PanelStyle {
    val className      = s"panel-$style"
    val createModifier = className.addClass
  }

  // noinspection TypeAnnotation
  object PanelStyle {
    val default      = DefaultPanelStyle
    lazy val primary = new PanelStyleValue("primary")
    lazy val success = new PanelStyleValue("success")
    lazy val info    = new PanelStyleValue("info")
    lazy val warning = new PanelStyleValue("warning")
    lazy val danger  = new PanelStyleValue("danger")
  }
}
