package com.karasiq.bootstrap.icons

import com.karasiq.bootstrap.BootstrapComponent

trait IconModifier extends BootstrapComponent

object IconModifier {
  case object NoIcon extends IconModifier {
    import scalatags.JsDom.all._
    override def render(md: Modifier*): Modifier = ()
  }

  implicit def unitToIconModifier(u: Unit): IconModifier = NoIcon
}
