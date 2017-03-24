package com.karasiq.bootstrap_text.table

import com.karasiq.bootstrap_text.BootstrapImplicits._
import com.karasiq.bootstrap_text.ModifierFactory

import scalatags.Text.all._

final class TableStyle private[table] (style: String) extends ModifierFactory {
  val createModifier: Modifier = s"table-$style".addClass
}

object TableStyle {
  lazy val striped = new TableStyle("striped")
  lazy val hover = new TableStyle("hover")
  lazy val bordered = new TableStyle("bordered")
  lazy val condensed = new TableStyle("condensed")
}
