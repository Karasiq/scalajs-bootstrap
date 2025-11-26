package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.context.RenderingContext

trait TableStyles { self: RenderingContext ⇒
  import scalaTags.all._

  final class TableStyle private[table] (val styleName: String) extends ModifierFactory {
    val className      = s"table-$styleName"
    val createModifier = className.addClass
  }

  object TableStyle {
    lazy val striped   = new TableStyle("striped")
    lazy val hover     = new TableStyle("hover")
    lazy val bordered  = new TableStyle("bordered")
    lazy val condensed = new TableStyle("condensed")
  }
}
