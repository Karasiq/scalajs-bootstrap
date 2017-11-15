package com.karasiq.bootstrap4.context

import scala.language.postfixOps

import com.karasiq.bootstrap4.utils.TextClassModifiers

trait TextRenderingContext extends RenderingContext with TextReactiveBinds with TextCallbacks with TextClassModifiers {
  type Element = scalatags.text.Builder
  type Output = String
  type FragT = String

  val scalaTags = scalatags.Text
}
