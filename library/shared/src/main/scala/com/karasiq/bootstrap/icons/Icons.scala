package com.karasiq.bootstrap.icons

import scala.language.{implicitConversions, postfixOps}

import com.karasiq.bootstrap.context.RenderingContext

trait Icons { self: RenderingContext ⇒
  trait IconModifier extends BootstrapComponent

  case object NoIcon extends IconModifier {
    private[this] val modifier: ModifierT = ()
    override def render(md: ModifierT*): ModifierT = modifier
  }
}
