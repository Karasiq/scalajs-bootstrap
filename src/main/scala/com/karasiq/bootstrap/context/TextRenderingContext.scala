package com.karasiq.bootstrap.context

import scala.language.postfixOps

trait TextRenderingContext extends RenderingContext {
  type Element = scalatags.text.Builder
  type Output = String
  type FragT = String
  val scalaTags = scalatags.Text
}
