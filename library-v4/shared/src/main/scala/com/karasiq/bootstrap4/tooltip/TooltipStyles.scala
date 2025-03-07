package com.karasiq.bootstrap4.tooltip

import com.karasiq.bootstrap.context.RenderingContext

trait TooltipStyles { self: RenderingContext ⇒
  import scalaTags.all._

  final class TooltipPlacement private[tooltip] (val placement: String) {
    override def toString: String = placement
  }

  // noinspection TypeAnnotation
  object TooltipPlacement {
    private[this] def placement(str: String): TooltipPlacement = new TooltipPlacement(str)
    val auto                                                   = placement("auto")
    val left                                                   = placement("left")
    val right                                                  = placement("right")
    val top                                                    = placement("top")
    val bottom                                                 = placement("bottom")
  }
}
