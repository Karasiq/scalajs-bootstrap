package com.karasiq.bootstrap.jquery

import io.udash.wrappers.jquery._

trait JQueryContext {
  def jQuery: JQueryStatic = JQueryContext.jQuery
}

object JQueryContext {
  final type JQuery = io.udash.wrappers.jquery.JQuery
  val jQuery: JQueryStatic = io.udash.wrappers.jquery.jQ
}
