package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.context.RenderingContext

trait ButtonGroupStyles { self: RenderingContext ⇒
  import scalaTags.all._

  sealed trait ButtonGroupSize extends ModifierFactory

  object DefaultButtonGroupSize extends ButtonGroupSize {
    val createModifier: Modifier = ()
  }

  final class ButtonGroupSizeValue private[buttons](val size: String) extends ButtonGroupSize {
    val className = s"btn-group-$size"
    val createModifier = className.addClass
  }

  object ButtonGroupSize {
    def default: ButtonGroupSize = DefaultButtonGroupSize
    lazy val large: ButtonGroupSize = new ButtonGroupSizeValue("lg")
    lazy val small: ButtonGroupSize = new ButtonGroupSizeValue("sm")
    lazy val extraSmall: ButtonGroupSize = new ButtonGroupSizeValue("xs")
  }
}
