package com.karasiq.bootstrap_text.icons

import com.karasiq.bootstrap_text.BootstrapComponent

import scala.language.implicitConversions

trait IconModifier extends BootstrapComponent

object IconModifier {
  case object NoIcon extends IconModifier {
    import scalatags.Text.all._
    override def render(md: Modifier*): Modifier = ()
  }

  implicit def unitToIconModifier(u: Unit): IconModifier = NoIcon
}
