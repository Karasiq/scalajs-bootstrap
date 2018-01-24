package com.karasiq.bootstrap4.icons

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap4.utils.Utils

trait Icons { self: RenderingContext with Utils ⇒
  import scalaTags.all._

  val Icon: IconFactory

  trait IconModifier extends BootstrapComponent

  case object NoIcon extends IconModifier {
    override def render(md: ModifierT*): ModifierT = {
      md
    }
  }

  trait IconFactory {
    def apply(iconName: String): IconModifier
  }
}
