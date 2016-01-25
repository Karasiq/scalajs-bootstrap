package com.karasiq.bootstrap.tooltip

sealed trait TooltipPlacement {
  def asString: String
}

object TooltipPlacement {
  private def create(str: String): TooltipPlacement = new TooltipPlacement {
    override def asString: String = str
  }

  def auto = create("auto")
  def left = create("left")
  def right = create("right")
  def top = create("top")
  def bottom = create("bottom")
}