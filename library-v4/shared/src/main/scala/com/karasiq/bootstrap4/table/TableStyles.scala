package com.karasiq.bootstrap4.table

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap4.utils.Utils

trait TableStyles { self: RenderingContext with Utils ⇒
  import scalaTags.all._

  final class TableStyle private[table] (val styleName: String) extends StyleClassModifier {
    val className      = s"table-$styleName"
    val createModifier = className.addClass
  }

  object TableStyle {
    lazy val dark       = new TableStyle("dark")
    lazy val striped    = new TableStyle("striped")
    lazy val hover      = new TableStyle("hover")
    lazy val bordered   = new TableStyle("bordered")
    lazy val small      = new TableStyle("sm")
    lazy val borderless = new TableStyle("borderless")
  }
}
