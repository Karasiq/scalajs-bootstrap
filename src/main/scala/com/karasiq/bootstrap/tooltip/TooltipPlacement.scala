package com.karasiq.bootstrap.tooltip

final class TooltipPlacement private[tooltip](value: String) {
  override def toString: String = value
}

object TooltipPlacement {
  lazy val auto = new TooltipPlacement("auto")
  lazy val left = new TooltipPlacement("left")
  lazy val right = new TooltipPlacement("right")
  lazy val top = new TooltipPlacement("top")
  lazy val bottom = new TooltipPlacement("bottom")
}