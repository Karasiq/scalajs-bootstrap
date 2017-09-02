package com.karasiq.bootstrap.icons

import com.karasiq.bootstrap.context.RenderingContext

trait Icons { self: RenderingContext ⇒
  import scalaTags.all._

  val Icon: IconFactory

  trait IconModifier extends BootstrapComponent

  case object NoIcon extends IconModifier {
    private[this] val modifier: ModifierT = ()
    override def render(md: ModifierT*): ModifierT = modifier
  }

  trait IconFactory {
    def apply(iconName: String): IconModifier
  }
}
