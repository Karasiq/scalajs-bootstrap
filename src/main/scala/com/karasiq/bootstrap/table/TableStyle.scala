package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ModifierFactory

import scalatags.JsDom.all._

final class TableStyle private[table] (style: String) extends ModifierFactory {
  val createModifier: Modifier = s"table-$style".addClass
}

object TableStyle {
  lazy val striped = new TableStyle("striped")
  lazy val hover = new TableStyle("hover")
  lazy val bordered = new TableStyle("bordered")
  lazy val condensed = new TableStyle("condensed")
}
